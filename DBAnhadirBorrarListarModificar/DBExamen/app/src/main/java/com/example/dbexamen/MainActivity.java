package com.example.dbexamen;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    EditText txtTitulo, txtDirector, txtPais, txtActor, txtAnho;
    ListView listaDiscos;
    SQLiteDatabase db;
    String[] trozos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDirector = (EditText) findViewById(R.id.txtDirector);
        txtPais = (EditText) findViewById(R.id.txtPais);
        txtActor = (EditText) findViewById(R.id.txtActor);
        txtAnho = (EditText) findViewById(R.id.txtAño);
        listaDiscos = (ListView) findViewById(R.id.listaDiscos);
        db = openOrCreateDatabase("misPelis", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS misPelis(Titulo VARCHAR,Director VARCHAR,Pais VARCHAR,Actor VARCHAR,Anho INTEGER);");

        listaDiscos.setOnItemClickListener(this);
    }

    public void Listar(View v) {
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM misPelis", null);
        if (c.getCount() == 0)
            lista.add("No hay registros");
        else {
            while (c.moveToNext()) {
                lista.add(c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2) + "-" + c.getString(3) + "-" + c.getString(4));
            }
        }
        adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_fila, lista);
        //comprobar
        listaDiscos.setAdapter(adaptador);
        c.close();
    }

    //añadir
    public void Anadir(View v) {
        String aux = String.valueOf(txtAnho.getText());
        db.execSQL("INSERT INTO misPelis VALUES" +
                " ('" + txtTitulo.getText().toString() + "','" + txtDirector.getText().toString() + "','" + txtPais.getText() + "','" + txtActor.getText() + "'," + Integer.parseInt(aux) + ")");
        Toast.makeText(this, "Se añadió la peli " + txtTitulo.getText().toString(),
                Toast.LENGTH_LONG).show();

    }

    public void Borrar(View v) {
        try {
            db.execSQL("DELETE FROM misPelis WHERE Titulo = '" + txtTitulo.getText().toString() + "' AND Director='" + txtDirector.getText().toString() + "'AND Pais= '" + txtPais.getText().toString() + "' AND Actor= '" + txtActor.getText().toString() + "'AND Anho= " + Integer.parseInt(txtAnho.getText().toString()) + "");
            Toast.makeText(this, "Se borró el disco " +
                    txtDirector.getText().toString(), Toast.LENGTH_LONG).show();
        } catch (SQLException s) {
            Toast.makeText(this, "Error al borrar!", Toast.LENGTH_LONG).show();
        }

    }


    //METOdo de callback
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        String cad;

        //obtenemos la info del item clickado
        cad = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), "Title => " + cad, Toast.LENGTH_SHORT).show();
        trozos = cad.split("-");
        //g=trozos[0];
        //d=trozos[1];
        txtTitulo.setText(trozos[0]);
        txtDirector.setText(trozos[1]);
        txtPais.setText(trozos[2]);
        txtActor.setText(trozos[3]);
        txtAnho.setText(trozos[4]);
    }

    //modificar
    public void Modificar(View v) {

        db.execSQL("UPDATE misPelis SET Titulo ='" + txtTitulo.getText().toString() + "', Director = '" + txtDirector.getText().toString() + "', Pais='" + txtPais.getText().toString() + "', Actor='" + txtActor.getText().toString() + "', Anho=" + Integer.parseInt(txtAnho.getText().toString()) + "    " +
                "WHERE Titulo = '" + trozos[0] + "' and Director = '" + trozos[1] + "'and  Pais='" + trozos[2] + "' and Actor='" + trozos[3] + "'and Anho= " + Integer.parseInt(trozos[4]) + "                  ");
        Toast.makeText(this, "Se modifico el disco " + txtDirector.getText().toString(), Toast.LENGTH_LONG).show();

    }
}