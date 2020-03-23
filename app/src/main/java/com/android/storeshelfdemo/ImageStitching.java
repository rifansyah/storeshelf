package com.android.storeshelfdemo;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.ddogleg.fitting.modelset.ModelMatcher;
import org.ddogleg.struct.FastQueue;

import java.util.ArrayList;
import java.util.List;

import boofcv.abst.feature.associate.AssociateDescription;
import boofcv.abst.feature.associate.ScoreAssociation;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.alg.distort.ImageDistort;
import boofcv.alg.distort.PixelTransformHomography_F32;
import boofcv.alg.distort.impl.DistortSupport;
import boofcv.alg.interpolate.InterpolatePixelS;
import boofcv.android.ConvertBitmap;
import boofcv.factory.feature.associate.FactoryAssociation;
import boofcv.factory.feature.detdesc.FactoryDetectDescribe;
import boofcv.factory.geo.ConfigRansac;
import boofcv.factory.geo.FactoryMultiViewRobust;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.border.BorderType;
import boofcv.struct.feature.AssociatedIndex;
import boofcv.struct.feature.BrightFeature;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.geo.AssociatedPair;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.Planar;
import georegression.struct.homography.Homography2D_F64;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point2D_I32;
import georegression.transform.homography.HomographyPointOps_F64;

public class ImageStitching {
    public static<T extends ImageGray<T>, FD extends TupleDesc> Homography2D_F64
    computeTransform( T imageA , T imageB ,
                      DetectDescribePoint<T,FD> detDesc ,
                      AssociateDescription<FD> associate ,
                      ModelMatcher<Homography2D_F64, AssociatedPair> modelMatcher )
    {
        // get the length of the description
        List<Point2D_F64> pointsA = new ArrayList<>();
        FastQueue<FD> descA = UtilFeature.createQueue(detDesc,100);
        List<Point2D_F64> pointsB = new ArrayList<>();
        FastQueue<FD> descB = UtilFeature.createQueue(detDesc,100);

        // extract feature locations and descriptions from each image
        describeImage(imageA, detDesc, pointsA, descA);
        describeImage(imageB, detDesc, pointsB, descB);

        // Associate features between the two images
        associate.setSource(descA);
        associate.setDestination(descB);
        associate.associate();

        // create a list of AssociatedPairs that tell the model matcher how a feature moved
        FastQueue<AssociatedIndex> matches = associate.getMatches();
        List<AssociatedPair> pairs = new ArrayList<>();

        for( int i = 0; i < matches.size(); i++ ) {
            AssociatedIndex match = matches.get(i);

            Point2D_F64 a = pointsA.get(match.src);
            Point2D_F64 b = pointsB.get(match.dst);

            pairs.add( new AssociatedPair(a,b,false));
        }

        // find the best fit model to describe the change between these images
        if( !modelMatcher.process(pairs) )
            throw new RuntimeException("Model Matcher failed!");

        // return the found image transform
        return modelMatcher.getModelParameters().copy();
    }

    /**
     * Detects features inside the two images and computes descriptions at those points.
     */
    private static <T extends ImageGray<T>, FD extends TupleDesc>
    void describeImage(T image,
                       DetectDescribePoint<T,FD> detDesc,
                       List<Point2D_F64> points,
                       FastQueue<FD> listDescs) {
        detDesc.detect(image);

        listDescs.reset();
        for( int i = 0; i < detDesc.getNumberOfFeatures(); i++ ) {
            points.add( detDesc.getLocation(i).copy() );
            listDescs.grow().setTo(detDesc.getDescription(i));
        }
    }

    /**
     * Given two input images create and display an image where the two have been overlayed on top of each other.
     */
    public static <T extends ImageGray<T>>
    Bitmap stitch(Bitmap imageA , Bitmap imageB , Class<T> imageType )
    {
        T inputA = ConvertBitmap.bitmapToGray(imageA, null, imageType, null);
        T inputB = ConvertBitmap.bitmapToGray(imageB, null, imageType, null);

        // Detect using the standard SURF feature descriptor and describer
        DetectDescribePoint detDesc = FactoryDetectDescribe.surfStable(
                new ConfigFastHessian(1, 2, 200, 1, 9, 4, 4), null,null, imageType);
        ScoreAssociation<BrightFeature> scorer = FactoryAssociation.scoreEuclidean(BrightFeature.class,true);
        AssociateDescription<BrightFeature> associate = FactoryAssociation.greedy(scorer,2,true);

        // fit the images using a homography.  This works well for rotations and distant objects.
        ModelMatcher<Homography2D_F64,AssociatedPair> modelMatcher =
                FactoryMultiViewRobust.homographyRansac(null,new ConfigRansac(60,3));

        Homography2D_F64 H = computeTransform(inputA, inputB, detDesc, associate, modelMatcher);

        return renderStitching(imageA, imageB,H);
    }

    /**
     * Renders and displays the stitched together images
     */
    public static Bitmap renderStitching( Bitmap imageA, Bitmap imageB ,
                                        Homography2D_F64 fromAtoB )
    {
        // specify size of output image
        double scale = 0.5;

        // Convert into a BoofCV color format
        Planar<GrayF32> colorA =
                ConvertBitmap.bitmapToPlanar(imageA, null, GrayF32.class, null);
        Planar<GrayF32> colorB =
                ConvertBitmap.bitmapToPlanar(imageB, null, GrayF32.class, null);

        // Where the output images are rendered into
        Planar<GrayF32> work = colorA.createSameShape();

        // Adjust the transform so that the whole image can appear inside of it
        Homography2D_F64 fromAToWork = new Homography2D_F64(scale,0,colorA.width/4,0,scale,colorA.height/4,0,0,1);
        Homography2D_F64 fromWorkToA = fromAToWork.invert(null);

        // Used to render the results onto an image
        PixelTransformHomography_F32 model = new PixelTransformHomography_F32();
        InterpolatePixelS<GrayF32> interp = FactoryInterpolation.bilinearPixelS(GrayF32.class, BorderType.ZERO);
        ImageDistort<Planar<GrayF32>,Planar<GrayF32>> distort =
                DistortSupport.createDistortPL(GrayF32.class, model, interp, false);
        distort.setRenderAll(false);

        // Render first image
        model.set(fromWorkToA);
        distort.apply(colorA,work);

        // Render second image
        Homography2D_F64 fromWorkToB = fromWorkToA.concat(fromAtoB,null);
        model.set(fromWorkToB);
        distort.apply(colorB,work);

        // Convert the rendered image into a BufferedImage
        Bitmap output = Bitmap.createBitmap(work.width, work.height, Bitmap.Config.ARGB_8888);
        ConvertBitmap.planarToBitmap(work, output, null);

        // draw lines around the distorted image to make it easier to see
        Homography2D_F64 fromBtoWork = fromWorkToB.invert(null);
        Point2D_I32 corners[] = new Point2D_I32[4];
        corners[0] = renderPoint(0,0,fromBtoWork);
        corners[1] = renderPoint(colorB.width,0,fromBtoWork);
        corners[2] = renderPoint(colorB.width,colorB.height,fromBtoWork);
        corners[3] = renderPoint(0,colorB.height,fromBtoWork);

//        g2.setColor(Color.YELLOW);
//        g2.setStroke(new BasicStroke(4));
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.drawLine(corners[0].x,corners[0].y,corners[1].x,corners[1].y);
//        g2.drawLine(corners[1].x,corners[1].y,corners[2].x,corners[2].y);
//        g2.drawLine(corners[2].x,corners[2].y,corners[3].x,corners[3].y);
//        g2.drawLine(corners[3].x,corners[3].y,corners[0].x,corners[0].y);

        return output;
    }

    private static Point2D_I32 renderPoint( int x0 , int y0 , Homography2D_F64 fromBtoWork )
    {
        Point2D_F64 result = new Point2D_F64();
        HomographyPointOps_F64.transform(fromBtoWork, new Point2D_F64(x0, y0), result);
        return new Point2D_I32((int)result.x,(int)result.y);
    }
}
