package com.example.mediarawalternativo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ImageButton forwardbtn, backwardbtn, pausebtn, playbtn;
    private MediaPlayer mPlayer;
    private TextView songName, startTime, songTime;
    private SeekBar songPrgs;
    private static int oTime = 0, sTime = 0, eTime = 0, fTime = 5000, bTime = 5000;

    //Propósito de un handler
    private Handler hdlr = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //botons
        backwardbtn = (ImageButton) findViewById(R.id.btnBackward);
        forwardbtn = (ImageButton) findViewById(R.id.btnForward);
        playbtn = (ImageButton) findViewById(R.id.btnPlay);
        pausebtn = (ImageButton) findViewById(R.id.btnPause);
        songName = (TextView) findViewById(R.id.txtSname);
        startTime = (TextView) findViewById(R.id.txtStartTime);
        songTime = (TextView) findViewById(R.id.txtSongTime);
        songName.setText("Adrenalina");
        mPlayer = MediaPlayer.create(this, R.raw.adrenalina);

        //barra de progreso
        songPrgs = (SeekBar) findViewById(R.id.sBar);
        songPrgs.setClickable(false);

        pausebtn.setEnabled(false);

        //listener del play
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Playing Audio", Toast.LENGTH_SHORT).show();
                mPlayer.start();

                eTime = mPlayer.getDuration();
                sTime = mPlayer.getCurrentPosition();

                if (oTime == 0) {
                    songPrgs.setMax(eTime);
                    oTime = 1;
                }

                songTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(eTime),
                        TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(eTime))));
                startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));

                //actualiza la seek bar
                songPrgs.setProgress(sTime);
                //holder psando ref a update song time
                hdlr.postDelayed(UpdateSongTime, 100);

                pausebtn.setEnabled(true);
                playbtn.setEnabled(false);
            }
        });


        //listener de pause
        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();

                pausebtn.setEnabled(false);
                playbtn.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Pausing Audio", Toast.LENGTH_SHORT).show();
            }
        });


        //listener del forward
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((sTime + fTime) <= eTime) {
                    sTime = sTime + fTime;
                    //posicionamos el reproductor en los nuevos msegundos
                    mPlayer.seekTo(sTime);

                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }

                if (!playbtn.isEnabled()) {
                    playbtn.setEnabled(true);
                }

            }
        });


        //listener de rewind
        backwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime - bTime) > 0) {
                    sTime = sTime - bTime;
                    mPlayer.seekTo(sTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
                if (!playbtn.isEnabled()) {
                    playbtn.setEnabled(true);
                }
            }
        });
    }

    //gestión de los updates de la canción
    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            sTime = mPlayer.getCurrentPosition();
            startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));
            songPrgs.setProgress(sTime);
            hdlr.postDelayed(this, 100);
        }
    };
}