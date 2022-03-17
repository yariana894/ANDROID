package com.example.imagendescargaaudiofotovideoetcunaactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/*!!!!!!!!!!!!!!!!!!!!!!!SOLO HASTA API 28 !!!!!Camara API 1!!!!!!!!!*/
//Activar permisos de camara y storage
public class MainActivity extends AppCompatActivity {
    //Cargar Imagenes (Descarga)
    public EditText editTextURL;
    CargaImagenes cargaImagenes;
    //Audio
    MediaPlayer mediaPlayer;
    //Video
    private VideoView mVideoView;
    //ruta hasta el recurso
    String srcPath = "/sdcard/video.webm";
    //Imagen
    ImageView imageView;
    static final int CAPTURA_IMAGEN_THUMBNAIL = 1;
    static final int CAPTURA_IMAGEN_TAMAÑO_REAL = 2;
    String fotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.adrenalina);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        //RUTA LOCAL
        mVideoView.setVideoPath(srcPath);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        //mVideoView.start();
        imageView = (ImageView) findViewById(R.id.imageView);
        //Carga imagenes
        editTextURL = (EditText) findViewById(R.id.editTextURL);
        cargaImagenes = new CargaImagenes();

    }

    public void playStopVideo(View view){
        mVideoView.start();
            Toast toast = Toast.makeText(this, "Reproduciendo vídeo", Toast.LENGTH_SHORT);
            toast.show();


    }


    public void playStopMusica(View view){
        TextView t = (TextView) findViewById(R.id.tvAudio);
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            t.setText("Pausado");
            Toast toast = Toast.makeText(this, "Pausado", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            mediaPlayer.start();
            t.setText("Sonando");
            Toast toast = Toast.makeText(this, "Sonando", Toast.LENGTH_SHORT);
            toast.show();
        }
    }




    public void hacerFotoThumbnail(View view) {
        Intent hacerFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hacerFotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(hacerFotoIntent, CAPTURA_IMAGEN_THUMBNAIL);
        }
    }

    public void Descargar(View v){
        cargaImagenes.execute(editTextURL.getText().toString());
    }
    private class CargaImagenes extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap imagen = descargarImagen(url);
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            imageView.setImageBitmap(result);
            pDialog.dismiss();
        }

        private Bitmap descargarImagen (String imageHttpAddress){
            java.net.URL imageUrl = null;
            Bitmap imagen = null;
            try{
                imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            }catch(IOException ex){
                ex.printStackTrace();
            }

            return imagen;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURA_IMAGEN_THUMBNAIL && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == CAPTURA_IMAGEN_TAMAÑO_REAL && resultCode == RESULT_OK) {
            Toast toast = Toast.makeText(this, "La imagen se ha guardado en: " + fotoPath, Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}