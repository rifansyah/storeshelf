package com.android.storeshelfdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.features2d.AKAZE;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

public class GetPicture extends AppCompatActivity {

    static {
        OpenCVLoader.initDebug();
    }

    private int PICK_IMAGE_REQUEST_1 = 1;
    private int PICK_IMAGE_REQUEST_2 = 2;
    private int PICK_IMAGE_REQUEST_3 = 3;
    private int PICK_IMAGE_REQUEST_4 = 4;
    private int PICK_IMAGE_REQUEST_5 = 5;
    private int PICK_IMAGE_REQUEST_6 = 6;

    String currentImage1Path, currentImage2Path, currentImage3Path;

    private ImageButton captureBtn1, captureBtn2, captureBtn3, captureBtn4, captureBtn5, captureBtn6;
    private Button saveBtn; // used to interact with capture and save Button in UI
    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);
        getSupportActionBar().setTitle("Mulai Ambil Gambar");

        OpenCVLoader.initDebug();

        captureBtn1 = findViewById(R.id.getPicture1);
        captureBtn2 = findViewById(R.id.getPicture2);
        captureBtn3 = findViewById(R.id.getPicture3);
        captureBtn4 = findViewById(R.id.getPicture4);
        captureBtn5 = findViewById(R.id.getPicture5);
        captureBtn6 = findViewById(R.id.getPicture6);

        saveBtn = findViewById(R.id.saveImage);
//        saveBtn.setOnClickListener(saveOnClickListener);
        String picture1Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png";
        String picture2Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png";
        String picture3Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png";
        String picture4Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_4.png";
        String picture5Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png";
        String picture6Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_6.png";

//        File myFile1 = new File(picture1Path); if(myFile1.exists()) myFile1.delete();
//        File myFile2 = new File(picture2Path); if(myFile2.exists()) myFile2.delete();
//        File myFile3 = new File(picture3Path); if(myFile3.exists()) myFile3.delete();
//        File myFile4 = new File(picture4Path); if(myFile4.exists()) myFile4.delete();
//        File myFile5 = new File(picture5Path); if(myFile5.exists()) myFile5.delete();
//        File myFile6 = new File(picture6Path); if(myFile6.exists()) myFile6.delete();
    }

    @Override
    public void onResume(){
        super.onResume();
        String picture1Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png";
        String picture2Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png";
        String picture3Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png";
        String picture4Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_4.png";
        String picture5Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png";
        String picture6Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_6.png";

        File myFile1 = new File(picture1Path);
        if(myFile1.exists()){
            Bitmap img1 = downScaleImage(picture1Path);
            captureBtn1.setImageBitmap(img1);
        }
        File myFile2 = new File(picture2Path);
        if(myFile2.exists()){
            Bitmap img2 = downScaleImage(picture2Path);
            captureBtn2.setImageBitmap(img2);
        }
        File myFile3 = new File(picture3Path);
        if(myFile3.exists()){
            Bitmap img3 = downScaleImage(picture3Path);

            captureBtn3.setImageBitmap(img3);
        }
        File myFile4 = new File(picture4Path);
        if(myFile4.exists()){
            Bitmap img4 = downScaleImage(picture4Path);

            captureBtn4.setImageBitmap(img4);
        }
        File myFile5 = new File(picture5Path);
        if(myFile5.exists()){
            Bitmap img5 = downScaleImage(picture5Path);

            captureBtn5.setImageBitmap(img5);
        }
        File myFile6 = new File(picture6Path);
        if(myFile6.exists()){
            Bitmap img6 = downScaleImage(picture6Path);

            captureBtn6.setImageBitmap(img6);
        }

    }

//    View.OnClickListener saveOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Thread thread = new Thread(imageProcessingRunnable);
//            thread.start();
//        }
//    };

    public void chooseImage1(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 1;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);
    }

    public void chooseImage2(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 2;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);

    }

    public void chooseImage3(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 3;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);

    }

    public void chooseImage4(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 4;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);

    }

    public void chooseImage5(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 5;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);
    }

    public void chooseImage6(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 6;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn1.setImageBitmap(bitmap1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn2.setImageBitmap(bitmap2);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn3.setImageBitmap(bitmap3);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn4.setImageBitmap(bitmap4);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn5.setImageBitmap(bitmap5);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_6 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn6.setImageBitmap(bitmap6);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showProcessingDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mCam.stopPreview();
                ringProgressDialog = ProgressDialog.show(GetPicture.this, "",	"Panorama", true);
                ringProgressDialog.setCancelable(false);
            }
        });
    }
    private void closeProcessingDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mCam.startPreview();
                ringProgressDialog.dismiss();
            }
        });
    }

//    private Runnable imageProcessingRunnable = new Runnable() {
//        @Override
//        public void run() {
//            GetPicture.this.showProcessingDialog();
//
//            try {
//                // Create a long array to store all image address
//                int elems = listImage.size();
//                long[] tempobjadr = new long[elems];
//                for (int i = 0; i < elems; i++) {
//                    tempobjadr[i] = listImage.get(i).getNativeObjAddr();
//                }
//                // Create a Mat to store the final panorama image
//                Mat result = new Mat();
//
//                // Call the Open CV C++ Code to perform stitching process
////            NativePanorama.processPanorama(tempobjadr, result.getNativeObjAddr());
//
//                Log.d("Vision", "Height " + result.rows() + " Width: " + result.cols());
//                // Save the image to internal storage
//                File sdcard = Environment.getExternalStorageDirectory();
//                final String fileName = sdcard.getAbsolutePath() + "/opencv_" + System.currentTimeMillis() + ".png";
//                Imgcodecs.imwrite(fileName, result);
//
//                GetPicture.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(GetPicture.this.getApplicationContext(), "File saved at: " + fileName, Toast.LENGTH_LONG).show();
//                    }
//                });
//
//                listImage.clear();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            GetPicture.this.closeProcessingDialog();
//        }
//    };

    private void saveBitmap(Bitmap bmp){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/StoreShelfDemoResult_" + timeStamp + ".bmp";
        String filename2 = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/StoreShelfDemoResult.bmp";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            out = new FileOutputStream(filename2);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImage1File() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img1_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentImage1Path = image.getAbsolutePath();
        return image;
    }

    private File createImage2File() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img2_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentImage2Path = image.getAbsolutePath();
        return image;
    }

    private File createImage3File() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img3_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentImage3Path = image.getAbsolutePath();
        return image;
    }

//    public void stitchHorizontal(View view){
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inScaled = false; // Leaving it to true enlarges the decoded image size.
//        Bitmap im1 = BitmapFactory.decodeResource(getResources(), R.drawable.part1, options);
//        Bitmap im2 = BitmapFactory.decodeResource(getResources(), R.drawable.part2, options);
//        Bitmap im3 = BitmapFactory.decodeResource(getResources(), R.drawable.part3, options);
//
//        Mat img1 = new Mat();
//        Mat img2 = new Mat();
//        Mat img3 = new Mat();
//        Utils.bitmapToMat(im1, img1);
//        Utils.bitmapToMat(im2, img2);
//        Utils.bitmapToMat(im3, img3);
//
//        Bitmap imgBitmap = stitchImagesHorizontal(Arrays.asList(img1, img2, img3));
//        ImageView imageView = findViewById(R.id.opencvImg);
//        imageView.setImageBitmap(imgBitmap);
//        saveBitmap(imgBitmap, "stitch_horizontal");
//    }
//    public void stitchVectical(View view){
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inScaled = false; // Leaving it to true enlarges the decoded image size.
//        Bitmap im1 = BitmapFactory.decodeResource(getResources(), R.drawable.part1, options);
//        Bitmap im2 = BitmapFactory.decodeResource(getResources(), R.drawable.part2, options);
//        Bitmap im3 = BitmapFactory.decodeResource(getResources(), R.drawable.part3, options);
//
//        Mat img1 = new Mat();
//        Mat img2 = new Mat();
//        Mat img3 = new Mat();
//        Utils.bitmapToMat(im1, img1);
//        Utils.bitmapToMat(im2, img2);
//        Utils.bitmapToMat(im3, img3);
//
//        Bitmap imgBitmap = stitchImagesVectical(Arrays.asList(img1, img2, img3));
//        ImageView imageView = findViewById(R.id.opencvImg);
//        imageView.setImageBitmap(imgBitmap);
//        saveBitmap(imgBitmap, "stitch_vectical");
//    }

    Bitmap stitchImagesVertical(List<Mat> src) {
        Mat dst = new Mat();
        Core.vconcat(src, dst); //Core.hconcat(src, dst);
        Bitmap imgBitmap = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst, imgBitmap);

        return imgBitmap;
    }
    Bitmap stitchImagesHorizontal(List<Mat> src) {
        Mat dst = new Mat();
        Core.hconcat(src, dst); //Core.vconcat(src, dst);
        Bitmap imgBitmap = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst, imgBitmap);

        return imgBitmap;
    }

    Bitmap downScaleImage(String path){
        File myFile1 = new File(path);
        Bitmap im = BitmapFactory.decodeFile(myFile1.getAbsolutePath());

        final int maxSize = 960;
        int outWidth;
        int outHeight;
        int inWidth = im.getWidth();
        int inHeight = im.getHeight();
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }

        Bitmap imCompress = Bitmap.createScaledBitmap(im, outWidth, outHeight, false);
        return imCompress;
    }

    public void stitchImage(View view) {

        GetPicture.this.showProcessingDialog();

        String picture1Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png";
        String picture2Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png";
        String picture3Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png";
        String picture4Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_4.png";
        String picture5Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png";
        String picture6Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_6.png";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false; // Leaving it to true enlarges the decoded image size.
        Bitmap im1Compress = downScaleImage(picture1Path);
        Bitmap im2Compress = downScaleImage(picture2Path);
        Bitmap im3Compress = downScaleImage(picture3Path);

        Bitmap im4Compress = downScaleImage(picture4Path);
        Bitmap im5Compress = downScaleImage(picture5Path);
        Bitmap im6Compress = downScaleImage(picture6Path);

        Mat img1 = new Mat();
        Mat img2 = new Mat();
        Mat img3 = new Mat();
        Utils.bitmapToMat(im1Compress, img1);
        Utils.bitmapToMat(im2Compress, img2);
        Utils.bitmapToMat(im3Compress, img3);

        Mat img4 = new Mat();
        Mat img5 = new Mat();
        Mat img6 = new Mat();
        Utils.bitmapToMat(im4Compress, img4);
        Utils.bitmapToMat(im5Compress, img5);
        Utils.bitmapToMat(im6Compress, img6);

//        Bitmap imgBitmapHor1 = stitchImagesHorizontal(Arrays.asList(img1, img2, img3));
//        Bitmap imgBitmapHor2 = stitchImagesHorizontal(Arrays.asList(img4, img5, img6));
        Mat tempTop = stitchTwoImages(img1, img2);
        Mat resTop = stitchTwoImages(tempTop, img3);

        Mat tempBottom = stitchTwoImages(img4, img5);
        Mat resBottom = stitchTwoImages(tempBottom, img6);


        Mat imgHorizontal1 = new Mat();
        Mat imgHorizontal2 = new Mat();
//        Utils.bitmapToMat(imgBitmapHor1, imgHorizontal1);
//        Utils.bitmapToMat(imgBitmapHor2, imgHorizontal2);

        Bitmap imgBitmap = stitchImagesVertical(Arrays.asList(resTop, resBottom));

        saveBitmap(imgBitmap);

        finish();
    }

    public static Mat stitchTwoImages(Mat img1, Mat img2) {
        Mat img1Gray = new Mat();
        Mat img2Gray = new Mat();

        Imgproc.cvtColor(img1, img1Gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(img2, img2Gray, Imgproc.COLOR_BGR2GRAY);

        Mat dc1 = new Mat();
        MatOfKeyPoint kp1 = new MatOfKeyPoint();
        Mat dc2 = new Mat();
        MatOfKeyPoint kp2 = new MatOfKeyPoint();

        AKAZE akaze = AKAZE.create();
        akaze.detectAndCompute(img1Gray, new Mat(), kp1, dc1);
        akaze.detectAndCompute(img2Gray, new Mat(), kp2, dc2);

        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        List<MatOfDMatch> knnMatches = new ArrayList<MatOfDMatch>();
        matcher.knnMatch(dc1, dc2, knnMatches, 2);

        float ratioThreshold = 0.6f;
        List<DMatch> goodMatches = new ArrayList<DMatch>();
        for (int i = 0; i < knnMatches.size(); i++) {
            DMatch[] matches = knnMatches.get(i).toArray();
            float dist1 = matches[0].distance;
            float dist2 = matches[1].distance;
            if (dist1 < ratioThreshold * dist2) {
                goodMatches.add(matches[0]);
            }
        }

        System.out.println(String.valueOf(goodMatches.size()));

        if (goodMatches.size() > 50) {
            return wrapToImages(img1, img2, goodMatches, kp1, kp2);
        } else {
            return mergeTwoImagesManually(img1, img2);
        }
    }

    public static Mat wrapToImages(Mat img1, Mat img2, List<DMatch> goodMatches, MatOfKeyPoint kp1, MatOfKeyPoint kp2) {
        LinkedList<Point> imgPoints1List = new LinkedList<Point>();
        LinkedList<Point> imgPoints2List = new LinkedList<Point>();
        List<KeyPoint> keypoints1List = kp1.toList();
        List<KeyPoint> keypoints2List = kp2.toList();

        for(int i = 0; i < goodMatches.size(); i++) {
            imgPoints1List.addLast(keypoints1List.get(goodMatches.get(i).queryIdx).pt);
            imgPoints2List.addLast(keypoints2List.get(goodMatches.get(i).trainIdx).pt);
        }

        MatOfPoint2f srcPts = new MatOfPoint2f();
        srcPts.fromList(imgPoints1List);
        MatOfPoint2f dstPts = new MatOfPoint2f();
        dstPts.fromList(imgPoints2List);

        Mat H = Calib3d.findHomography(srcPts, dstPts, Calib3d.RANSAC, 5);

        int imageWidth = img1.cols();
        int imageHeight = img1.rows();

        Mat Offset = new Mat(3, 3, H.type());
        Offset.put(0,0, new double[]{1});
        Offset.put(0,1, new double[]{0});
        Offset.put(0,2, new double[]{imageWidth});
        Offset.put(1,0, new double[]{0});
        Offset.put(1,1, new double[]{1});
        Offset.put(1,2, new double[]{imageHeight});
        Offset.put(2,0, new double[]{0});
        Offset.put(2,1, new double[]{0});
        Offset.put(2,2, new double[]{1});

        Core.gemm(Offset, H, 1, new Mat(), 0, H);

        Mat srcCorners = new Mat(4,1, CvType.CV_32FC2);
        Mat dstCorners = new Mat(4,1, CvType.CV_32FC2);

        srcCorners.put(0,0, new double[]{0,0});
        srcCorners.put(0,0, new double[]{imageWidth,0});
        srcCorners.put(0,0,new double[]{imageWidth,imageHeight});
        srcCorners.put(0,0,new double[]{0,imageHeight});

        Core.perspectiveTransform(srcCorners, dstCorners, H);

        Size s = new Size(imageWidth * 3,imageHeight * 3);
        Mat imgMatches = new Mat(new Size(img1.cols() + img2.cols(), img1.rows()), CvType.CV_32FC2);

        Imgproc.warpPerspective(img1, imgMatches, H, s);

        int mxPos = (int)(imgMatches.size().width/2 - img2.size().width/2);
        int myPos = (int)(imgMatches.size().height/2 - img2.size().height/2);
        Mat m = new Mat(imgMatches,new Rect(mxPos, myPos, img2.cols(), img2.rows()));

        img2.copyTo(m);

        Mat resGray = new Mat();
        Mat resGrayThreshold = new Mat();
        Mat hierarchy = new Mat();
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Imgproc.cvtColor(imgMatches, resGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(resGray, resGrayThreshold, 5, 255,Imgproc.THRESH_BINARY);
        Imgproc.findContours(resGrayThreshold, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint cnt = contours.get(0);
        Rect boundingRect = Imgproc.boundingRect(cnt);
        System.out.println(boundingRect.toString());
        Mat regionOfInterest = imgMatches.submat(boundingRect);

        MatOfDMatch gm = new MatOfDMatch();
        gm.fromList(goodMatches);
        Mat outMat = new Mat();
        Features2d.drawMatches(img1, kp1, img2, kp2, gm, outMat);
        return regionOfInterest;
    }

    public static Mat mergeTwoImagesManually(Mat img1, Mat img2) {
        Mat m = new Mat(img2,new Rect(img2.cols() / 3, 0, img2.cols() - (img2.cols() / 3), img2.rows()));
        Mat concat = new Mat();
        Core.hconcat(Arrays.asList(img1, m), concat);
        return concat;
    }
}
