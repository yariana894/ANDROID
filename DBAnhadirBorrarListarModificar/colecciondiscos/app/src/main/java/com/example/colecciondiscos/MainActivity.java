package com.example.colecciondiscos;


import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    EditText txtGrupo, txtDisco;
    ListView listaDiscos;
    SQLiteDatabase db;
    String[] trozos= new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGrupo = (EditText) findViewById(R.id.txtGrupo);
        txtDisco = (EditText) findViewById(R.id.txtDisco);
        listaDiscos = (ListView) findViewById(R.id.listaDiscos);
        db = openOrCreateDatabase("MisDiscos", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS misDiscos(Grupo VARCHAR,Disco VARCHAR);");

        Listar();
        listaDiscos.setOnItemClickListener(this);

    }

    public void Listar() {
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM MisDiscos", null);
        if (c.getCount() == 0)
            lista.add("No hay registros");
        else {
            while (c.moveToNext()) {
                lista.add(c.getString(0) + "-" + c.getString(1));
            }
        }
        adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_fila, lista);
        //comprobar
        listaDiscos.setAdapter(adaptador);
        c.close();
    }

    //añadir
    public void Anadir(View v) {
        db.execSQL("INSERT INTO MisDiscos VALUES" +
                " ('" + txtGrupo.getText().toString() + "','" + txtDisco.getText().toString() + "')");
        Toast.makeText(this, "Se añadió el disco " + txtDisco.getText().toString(),
                Toast.LENGTH_LONG).show();
        Listar();
    }
    public void Borrar(View v) {
        try {
            db.execSQL("DELETE FROM MisDiscos WHERE Grupo = '" +
                    txtGrupo.getText().toString() + "' AND Disco='" +
                    txtDisco.getText().toString() + "'");
            Toast.makeText(this, "Se borró el disco " +
                    txtDisco.getText().toString(), Toast.LENGTH_LONG).show();
        } catch (SQLException s) {
            Toast.makeText(this, "Error al borrar!", Toast.LENGTH_LONG).show();
        }
        Listar();
    }


    //METOdo de callback

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String[] trozos;
        String cad;

        //obtenemos la info del item clickado
        cad=parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),"Title => "+cad, Toast.LENGTH_SHORT).show();
        trozos=cad.split("-");
        //g=trozos[0];
        //d=trozos[1];
        txtGrupo.setText(trozos[0]);
        txtDisco.setText(trozos[1]);
    }
    public void Modificar(View v) {


        db.execSQL("UPDATE misDiscos SET Grupo ='"+txtGrupo.getText().toString()+"', Disco = '"+ txtDisco.getText().toString()+"' " +
                "WHERE Grupo = '"+trozos[0]+"' and Disco = '"+trozos[1]+"'");
        Toast.makeText(this,"Se modifico el disco "+txtDisco.getText().toString(),Toast.LENGTH_LONG).show();
        Listar();
    }
}