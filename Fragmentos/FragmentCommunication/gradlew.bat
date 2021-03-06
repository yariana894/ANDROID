package com.example.ejercicio2a;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Spinner opciones;
    Object value;
    TextView textViewFahr;
    TextView textViewCels;
    TextView textViewKel;
    TextView textViewRank;
    TextView textViewReaumur;

    Double kel;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Spinner) findViewById(R.id.spinner)).setOnItemSelectedListener(this);
        editText = findViewById(R.id.editTextTextPersonName);

        textViewFahr = (TextView) findViewById(R.id.textViewFahren);
        textViewCels = (TextView) findViewById(R.id.textViewCelsius);
        textViewKel = (TextView) findViewById(R.id.textViewKelvin);
        textViewRank = (TextView) findViewById(R.id.textViewRankine);
        textViewReaumur = (TextView) findViewById(R.id.textViewReaumur);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String valor = editText.getText().toString();

        if (parent.getSelectedItem().equals("Celsius")) {
            textViewCels.setText(valor);

            double valorDouble = Double.parseDouble(valor);

            double valorCK = valorDouble + 273.15;
            String res = String.valueOf(valorCK);
            textViewKel.setText(res);


            double valorCF = (valorDouble * 9 / 5) + 32;
            String res2 = String.valueOf(valorCF);
            textViewFahr.setText(res2);

            double valorCRe = (valorDouble + 273.15) * 9 / 5;
            String res3 = String.valueOf(valorCRe);
            textViewReaumur.setText(res3);

            double valorCRa = (valorDouble + 273.15) * 9 / 5;
            String res4 = String.valueOf(valorCRa);
            textViewRank.setText(res4);
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     