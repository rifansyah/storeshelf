package com.android.storeshelfdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.storeshelfdemo.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenCVLoader.initDebug();

        String resultFilename = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/StoreShelfDemoResult.bmp";

        ImageView imView = findViewById(R.id.imageView);

        File myFile = new File(resultFilename);
        if(myFile.exists()){
            Bitmap imgResult = BitmapFactory.decodeFile(myFile.getAbsolutePath());
            imView.setImageBitmap(imgResult);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        String resultFilename = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/StoreShelfDemoResult.bmp";

        ImageView imView = findViewById(R.id.imageView);

        File myFile = new File(resultFilename);
        if(myFile.exists()){
            Bitmap imgResult = BitmapFactory.decodeFile(myFile.getAbsolutePath());
            imView.setImageBitmap(imgResult);
        }
    }

    public void getPicture(View view) {
        Intent intent = new Intent(this, GetPicture.class);
        startActivity(intent);
    }
}