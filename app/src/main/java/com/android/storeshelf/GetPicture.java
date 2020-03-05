package com.android.storeshelf;

import android.app.ProgressDialog;
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

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_stitching;
import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

//import org.opencv.core.Mat;

public class GetPicture extends AppCompatActivity {

//    static{
//        System.loadLibrary("opencv_java");
//        System.loadLibrary("MyLib");
//    }

//    static {
//        System.loadLibrary("OpenCV412");
//    }

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
    private SurfaceView mSurfaceView, mSurfaceViewOnTop; // used to display the camera frame in UI
    private Camera mCam;
    private boolean isPreview; // Is the camera frame displaying?
    private boolean safeToTakePicture = true; // Is it safe to capture a picture?
    Mat imageMat;

    private List<Mat> listImage = new ArrayList<>();

    ProgressDialog ringProgressDialog;

    static boolean try_use_gpu = false;
    static opencv_core.MatVector imgs = new opencv_core.MatVector();
    static String result_name = "result.jpg";

//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case LoaderCallbackInterface.SUCCESS:
//                {
//                    Log.i("OpenCV", "OpenCV loaded successfully");
//                    imageMat=new Mat();
//                } break;
//                default:
//                {
//                    super.onManagerConnected(status);
//                } break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);

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
            Bitmap img1 = BitmapFactory.decodeFile(myFile1.getAbsolutePath());
            captureBtn1.setImageBitmap(img1);
            captureBtn1.setRotation(90);
        }
        File myFile2 = new File(picture2Path);
        if(myFile2.exists()){
            Bitmap img2 = BitmapFactory.decodeFile(myFile2.getAbsolutePath());
            captureBtn2.setImageBitmap(img2);
            captureBtn2.setRotation(90);
        }
        File myFile3 = new File(picture3Path);
        if(myFile3.exists()){
            Bitmap img3 = BitmapFactory.decodeFile(myFile3.getAbsolutePath());
            captureBtn3.setImageBitmap(img3);
            captureBtn3.setRotation(90);
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

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        File photoFile = null;
//        try{
//            photoFile = createImage1File();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        if(photoFile != null){
//            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileProvider",photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(Intent.createChooser(intent, "Select Image 1"), PICK_IMAGE_REQUEST_1);
//        }
    }

    public void chooseImage2(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 2;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        File photoFile = null;
//        try{
//            photoFile = createImage2File();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        if(photoFile != null){
//            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileProvider",photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(Intent.createChooser(intent, "Select Image 2"), PICK_IMAGE_REQUEST_2);
//        }
    }

    public void chooseImage3(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 3;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);


//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        File photoFile = null;
//        try{
//            photoFile = createImage3File();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        if(photoFile != null){
//            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileProvider",photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(Intent.createChooser(intent, "Select Image 3"), PICK_IMAGE_REQUEST_3);
//        }
    }

    public void chooseImage4(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 4;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);


//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        File photoFile = null;
//        try{
//            photoFile = createImage3File();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        if(photoFile != null){
//            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileProvider",photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(Intent.createChooser(intent, "Select Image 3"), PICK_IMAGE_REQUEST_3);
//        }
    }

    public void chooseImage5(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 5;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);


//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        File photoFile = null;
//        try{
//            photoFile = createImage3File();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        if(photoFile != null){
//            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileProvider",photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(Intent.createChooser(intent, "Select Image 3"), PICK_IMAGE_REQUEST_3);
//        }
    }

    public void chooseImage6(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        int pictureNumber = 6;
        intent.putExtra("pictureNumber",pictureNumber);
        startActivity(intent);


//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        File photoFile = null;
//        try{
//            photoFile = createImage3File();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        if(photoFile != null){
//            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileProvider",photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(Intent.createChooser(intent, "Select Image 3"), PICK_IMAGE_REQUEST_3);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn1.setImageBitmap(bitmap1);
//
//                Mat mat = new Mat();
//                Utils.bitmapToMat(bitmap1, mat);
//                listImage.add(mat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn2.setImageBitmap(bitmap2);
//
//                Mat mat = new Mat();
//                Utils.bitmapToMat(bitmap2, mat);
//                listImage.add(mat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn3.setImageBitmap(bitmap3);
//
//                Mat mat = new Mat();
//                Utils.bitmapToMat(bitmap3, mat);
//                listImage.add(mat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                captureBtn4.setImageBitmap(bitmap4);
//
//                Mat mat = new Mat();
//                Utils.bitmapToMat(bitmap4, mat);
//                listImage.add(mat);
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
        String filename = "/sdcard/testPano.bmp";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
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
////        saveBitmap(imgBitmap, "stitch_horizontal");
//    }

//    Bitmap stitchImagesHorizontal(List<Mat> src) {
//        Mat dst = new Mat();
//        Core.hconcat(src, dst); //Core.vconcat(src, dst);
//        Bitmap imgBitmap = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(dst, imgBitmap);
//
//        return imgBitmap;
//    }

    public void stitchImage(View view) {

        String picture1Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png";
        String picture2Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png";
        String picture3Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png";
        String picture4Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_4.png";
        String picture5Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png";
        String picture6Path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_6.png";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false; // Leaving it to true enlarges the decoded image size.
        File myFile1 = new File(picture1Path);
        Bitmap im1 = BitmapFactory.decodeFile(myFile1.getAbsolutePath());

        final int maxSize = 960;
        int outWidth;
        int outHeight;
        int inWidth = im1.getWidth();
        int inHeight = im1.getHeight();
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }

        Bitmap im1Compress = Bitmap.createScaledBitmap(im1, outWidth, outHeight, false);

        File myFile2 = new File(picture2Path);
        Bitmap im2 = BitmapFactory.decodeFile(myFile2.getAbsolutePath());
        Bitmap im2Compress = Bitmap.createScaledBitmap(im2, outWidth, outHeight, false);

        File myFile3 = new File(picture3Path);
        Bitmap im3 = BitmapFactory.decodeFile(myFile3.getAbsolutePath());
        Bitmap im3Compress = Bitmap.createScaledBitmap(im3, outWidth, outHeight, false);

        Mat img1 = imread(picture1Path);
        Mat img2 = imread(picture2Path);
        Mat img3 = imread(picture3Path);
//        Utils.bitmapToMat(im1Compress, img1);
//        Utils.bitmapToMat(im2Compress, img2);
//        Utils.bitmapToMat(im3Compress, img3);

//            int retval = parseCmdArgs(args);
//            if (retval != 0) {
//                System.exit(-1);
//            }

        opencv_core.Mat pano = new opencv_core.Mat();
//        Stitcher stitcher = Stitcher.createDefault(try_use_gpu);
        opencv_stitching.Stitcher stitcher = opencv_stitching.Stitcher.create();
        imgs.resize(imgs.size() + 1);
        imgs.put(imgs.size() -1, img1);
        imgs.resize(imgs.size() + 1);
        imgs.put(imgs.size()-1, img2);
        imgs.resize(imgs.size() + 1);
        imgs.put(imgs.size()-1, img3);

        int status = stitcher.stitch(imgs, pano);

        if (status != opencv_stitching.Stitcher.OK) {
            System.out.println("Can't stitch images, error code = " + status);
            System.exit(-1);
        }

        imwrite(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + result_name, pano);

        System.out.println("Images stitched together to make " + getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + result_name);

        String resultImage = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + result_name;
        File myFileResult = new File(resultImage);
        Bitmap imResult = BitmapFactory.decodeFile(myFileResult.getAbsolutePath());
//        Bitmap imgBitmap = stitchImagesHorizontal(Arrays.asList(img1, img2, img3));
        ImageView imageView = findViewById(R.id.opencvImg);
        imageView.setImageBitmap(imResult);
    }
}
