package com.android.storeshelf;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;


public class CameraActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

        ImageView imgPreviewLeft = findViewById(R.id.imagePreviewLeft);
        ImageView imgPreviewRight = findViewById(R.id.imagePreviewRight);
        ImageView imgPreviewTop = findViewById(R.id.imagePreviewTop);
        ImageView imgPreviewBottom = findViewById(R.id.imagePreviewBottom);

        textureView = findViewById(R.id.view_finder);

        if(allPermissionsGranted()){
            startCamera(); //start camera if permission has been granted by user
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        int pictureId = getIntent().getIntExtra("pictureNumber",0);


        if(pictureId == 1){
            File imgFileRight = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png");
            Bitmap imgRight = BitmapFactory.decodeFile(imgFileRight.getAbsolutePath());

//            imgPreviewRight.imageMatrix = Matrix.ScaleToFit.START;

            imgPreviewRight.setImageBitmap(imgRight);

            imgPreviewRight.setRotation(90);
        }else if(pictureId == 2){
            File imgFileLeft = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png");
            Bitmap imgLeft = BitmapFactory.decodeFile(imgFileLeft.getAbsolutePath());
            File imgFileRight = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png");
            Bitmap imgRight = BitmapFactory.decodeFile(imgFileRight.getAbsolutePath());

//            imgPreviewLeft.imageMatrix = Matrix.ScaleToFit.END;
//            imgPreviewRight.imageMatrix = Matrix.ScaleToFit.START;

            imgPreviewLeft.setImageBitmap(imgLeft);
            imgPreviewLeft.setRotation(90);
            imgPreviewRight.setImageBitmap(imgRight);
            imgPreviewRight.setRotation(90);
        }else if(pictureId == 3){
            File imgFileLeft = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png");
            Bitmap imgLeft = BitmapFactory.decodeFile(imgFileLeft.getAbsolutePath());

//            imgPreviewLeft.imageMatrix = Matrix.ScaleToFit.END;

            imgPreviewLeft.setImageBitmap(imgLeft);
            imgPreviewLeft.setRotation(90);
        }else if(pictureId == 4){
            File imgFileTop = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png");
            Bitmap imgTop = BitmapFactory.decodeFile(imgFileTop.getAbsolutePath());
            File imgFileRight = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png");
            Bitmap imgRight = BitmapFactory.decodeFile(imgFileRight.getAbsolutePath());

            imgPreviewTop.setImageBitmap(imgTop);
            imgPreviewTop.setRotation(90);
            imgPreviewRight.setImageBitmap(imgRight);
        }
    }

    private void startCamera() {

        CameraX.unbindAll();

        Rational aspectRatio = new Rational (textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(), textureView.getHeight()); //size of the screen

        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    //to update the surface texture we  have to destroy it first then re-add it
                    @Override
                    public void onUpdated(Preview.PreviewOutput output){
                        ViewGroup parent = (ViewGroup) textureView.getParent();
                        parent.removeView(textureView);
                        parent.addView(textureView, 0);

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                        updateTransform();
                    }
                });


        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);

        findViewById(R.id.imgCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pictureId = getIntent().getIntExtra("pictureNumber",0);
                File file;
                if(pictureId == 1) {
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_1.png");
                }else if(pictureId == 2){
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_2.png");
                }else if(pictureId == 3){
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_3.png");
                }else if(pictureId == 4){
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_4.png");
                }else if(pictureId == 5){
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_5.png");
                }else if(pictureId == 6){
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "CapturedImage" + "_6.png");
                }else{
                    file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + "_0.png");
                }

//                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".png");
                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "Pic captured at " + file.getAbsolutePath();
                        Toast.makeText(getBaseContext(), msg,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "Pic capture failed : " + message;
                        Toast.makeText(getBaseContext(), msg,Toast.LENGTH_LONG).show();
                        if(cause != null){
                            cause.printStackTrace();
                        }
                    }
                });
            }
        });

        //bind to lifecycle:
        CameraX.bindToLifecycle((LifecycleOwner)this, preview, imgCap);
    }

    private void updateTransform(){
        Matrix mx = new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();

        float cX = w / 2f;
        float cY = h / 2f;

        int rotationDgr;
        int rotation = (int)textureView.getRotation();

        switch(rotation){
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }

        mx.postRotate((float)rotationDgr, cX, cY);
        textureView.setTransform(mx);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera();
            } else{
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public void back(View view) {
        finish();
    }
}
