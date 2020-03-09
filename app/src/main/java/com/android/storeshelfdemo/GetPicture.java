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
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

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

        Bitmap imgBitmapHor1 = stitchImagesHorizontal(Arrays.asList(img1, img2, img3));
        Bitmap imgBitmapHor2 = stitchImagesHorizontal(Arrays.asList(img4, img5, img6));

        Mat imgHorizontal1 = new Mat();
        Mat imgHorizontal2 = new Mat();
        Utils.bitmapToMat(imgBitmapHor1, imgHorizontal1);
        Utils.bitmapToMat(imgBitmapHor2, imgHorizontal2);

        Bitmap imgBitmap = stitchImagesVertical(Arrays.asList(imgHorizontal1, imgHorizontal2));

//        ImageView imageView = findViewById(R.id.imageView);
//        imageView.setImageBitmap(imgBitmap);
        saveBitmap(imgBitmap);

        finish();

//        GetPicture.this.closeProcessingDialog();
    }
}
