package com.example.basedatossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TABLE_NAME = "AlmacenNovelas";
    EditText txtID, txtTitulo, txtAutor, buscarid;
    TextView viewTitle, autName;
    ListView listaMuestra;
    ArrayAdapter<String> adaptador;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtID = (EditText)findViewById(R.id.txtID);
        txtTitulo=(EditText)findViewById(R.id.txtTitulo);
        txtAutor =(EditText)findViewById(R.id.txtAutor);
        buscarid = (EditText) findViewById(R.id.buscarid);
        viewTitle = (TextView) findViewById(R.id.viewtitle);
        autName = (TextView) findViewById(R.id.autname);

        listaMuestra =(ListView)findViewById(R.id.lista);
        db=openOrCreateDatabase("Biblioteca", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY, Titulo VARCHAR, Autor VARCHAR);");

        listaMuestra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String texto[] = adaptador.getItem(position).toString().split("\\|");
                String datos[] = {texto[0].trim()};
                Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", datos);
                if (c.getCount() != 0) {
                    c.moveToFirst();
                    txtID.setText(c.getString(0));
                    txtTitulo.setText(c.getString(1));
                    txtAutor.setText(c.getString(2));
                }
            }
        });
        Listar();
    }

    public void Añadir(View v){
        String[] datos = {txtID.getText().toString()};
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", datos);
        if (c.getCount() == 0) {
            String[] datos2 = {txtID.getText().toString(), txtTitulo.getText().toString(), txtAutor.getText().toString()};
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?)", datos2);
            Toast.makeText(this,"Se añadió el título "+ txtTitulo.getText().toString(),Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "El ID ya existe.", Toast.LENGTH_SHORT).show();
        }
        Listar();
    }

    public void Modificar(View v) {
        String[] datos = {txtID.getText().toString()};
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", datos);
        if (c.getCount() != 0){
            String[] datos2 = {txtTitulo.getText().toString(), txtAutor.getText().toString(), txtID.getText().toString()};
            db.execSQL("UPDATE " + TABLE_NAME + " SET Titulo = ?, Autor = ? WHERE ID = ?", datos2);
            Toast.makeText(this, "Fila actualizada.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fila inexistente.", Toast.LENGTH_SHORT).show();
        }
        Listar();
    }

    public void Borrar(View v){
        try {
            String[] datos = {txtID.getText().toString()};
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", datos);
            if (c.getCount() == 0) {
                Toast.makeText(this, "Fila inexistente.", Toast.LENGTH_SHORT).show();
            } else {
                db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID = ?", datos);
                Toast.makeText(this, "Se borró el libro " + txtTitulo.getText().toString(), Toast.LENGTH_LONG).show();
            }
        }
        catch(SQLException s){
            Toast.makeText(this, "Error al borrar!", Toast.LENGTH_LONG).show();
        }
         Listar();
    }


    public void Listar(){
        List<String> lista = new ArrayList<String>();
        Cursor c=db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(c.getCount()==0)
            lista.add("No hay registros");
        else{
            while(c.moveToNext()) {
                String fila = "";
                for (int i = 0; i < c.getColumnCount(); i++) {
                    if (i != c.getColumnCount()-1) {
                        fila += c.getString(i) + " | ";
                    } else {
                        fila += c.getString(i);
                    }
                }
                lista.add(fila);
            }

        }
        adaptador=new ArrayAdapter<String>(getApplicationContext(),R.layout.lista_fila,lista);
        listaMuestra.setAdapter(adaptador);
    }



//buscar por ID [Funciona]

 /*   public void buscar(View v){
        String[] id = {buscarid.getText().toString()};
       // List<String> lista = new ArrayList<String>();
        Cursor c=db.rawQuery("SELECT * FROM AlmacenNovelas WHERE ID = ?", id);
        if(c.getCount()==0)
            Toast.makeText(this, "No hay libros con esa ID", Toast.LENGTH_LONG).show();
        else{
                 c.moveToFirst();
                viewTitle.setText(c.getString(1));
                autName.setText(c.getString(2));
               // lista.add(c.getString(0)+" | "+c.getString(1)+" | "+c.getString(2));
        }

    }*/


    public void buscar(View v){
        String[] id = {buscarid.getText().toString()};
        // List<String> lista = new ArrayList<String>();
        Cursor c=db.rawQuery("SELECT * FROM AlmacenNovelas WHERE ID = ?", id);
        if(c.getCount()==0)
            Toast.makeText(this, "No hay libros con ese nombre", Toast.LENGTH_LONG).show();
        else{
            c.moveToFirst();
            viewTitle.setText(c.getString(1));
            autName.setText(c.getString(2));
            // lista.add(c.getString(0)+" | "+c.getString(1)+" | "+c.getString(2));
        }




    }





}