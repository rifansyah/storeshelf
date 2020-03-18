package com.android.storeshelfdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.opencv.android.OpenCVLoader;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            Toast.makeText(this, "ga bisa", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "bisa", Toast.LENGTH_LONG).show();
        }

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