package com.example.asynctask;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    private TextView textViewResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        textViewResultado = findViewById(R.id.textView);

        String nombreUsuario = getIntent().getStringExtra("usuario");
        textViewResultado.setText("Bienvenido: " + nombreUsuario);
    }
}