package com.example.video;


import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    //ruta hasta el archivo
    String srcPath="/sdcard/video.webm";
    private VideoView mVideoView;
    final int PHOTO_RESULT=1;
    private Uri mLastPhotoURI=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void local(View view) {
        setContentView(R.layout.activity_second);
        mVideoView=(VideoView) findViewById(R.id.videoView);

        mVideoView.setVideoPath(srcPath);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.start();


    }
    public void streaming(View view){
        setContentView(R.layout.activity_second);
        mVideoView=(VideoView) findViewById(R.id.videoView);


        //RUTA STREAMING
        mVideoView.setVideoURI(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/transcoded/a/ab/Ejemplo_Potencia.webm/Ejemplo_Potencia.webm.360p.vp9.webm"));

        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.start();
    }

    public void volver(View view) {
        setContentView(R.layout.activity_main);
    }
}
