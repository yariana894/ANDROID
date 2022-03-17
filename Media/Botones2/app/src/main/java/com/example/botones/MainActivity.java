package com.example.botones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    String srcPath="/sdcard/video.webm";
    private VideoView mVideoView;
    final int PHOTO_RESULT=1;
    private Uri mLastPhotoURI=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Cosas para fotos
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        }
    }
    private Uri createFileURI() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(System.currentTimeMillis());
        String fileName = "PHOTO_" + timeStamp + ".jpg";
        return Uri.fromFile(new File(Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),fileName));
    }

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mLastPhotoURI = createFileURI();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mLastPhotoURI);
            startActivityForResult(takePictureIntent, PHOTO_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(mLastPhotoURI.getPath()));
        }
    }
    public void video(View view) {
        setContentView(R.layout.activity_second);
        mVideoView=(VideoView) findViewById(R.id.videoView);

        mVideoView.setVideoPath(srcPath);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.start();


    }

    public void volver(View view) {
        setContentView(R.layout.activity_main);
    }
    public void empezarVideo(View view){
        startService(new Intent(
                MainActivity.this,ServicioMusica.class));
    }
    public void pararVideo(View view){
        stopService(new Intent(
                MainActivity.this,ServicioMusica.class));
    }
    }
