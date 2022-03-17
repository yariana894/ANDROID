package com.example.asynctask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editTextUsuario, editTextClave;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.buttonValidar);
        editTextClave = findViewById(R.id.editTextClave);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        progressBar = findViewById(R.id.progressBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enviamos el usuario como parámetro para el método
                new Task1().execute(editTextUsuario.getText().toString());
            }
        });


    }

    class Task1 extends AsyncTask<String, Void, String> {

        /*el progressbar se pone visible y el botón se bloquea*/
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            button.setEnabled(false);
        }

        //durante 5 segundos está cargando
        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            button.setEnabled(true);
            Intent intent = new Intent(MainActivity.this, ResultadoActivity.class);
            intent.putExtra("usuario", editTextUsuario.getText().toString());
            startActivity(intent);
        }
    }
}


