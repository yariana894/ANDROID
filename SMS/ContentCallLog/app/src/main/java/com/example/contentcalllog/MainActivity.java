package com.example.contentcalllog;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //agrega nuevo valor
        ContentValues valores = new ContentValues();
        valores.put(CallLog.Calls.DATE, new Date().getTime());
        valores.put(CallLog.Calls.NUMBER, "3546229918");
        valores.put(CallLog.Calls.DURATION, "45");
        valores.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
        Uri nuevoElemento = getContentResolver().insert(
                CallLog.Calls.CONTENT_URI, valores);
        //con los permisos agrega esta llamada

        //borramos la inserción anterior
        getContentResolver().delete(CallLog.Calls.CONTENT_URI,
                "number='3546229918'", null);

        //modificaciones
        ContentValues valores2 = new ContentValues();
        valores2.put(CallLog.Calls.NUMBER, "4444444444");
        getContentResolver().update(CallLog.Calls.CONTENT_URI, valores2,
                "number='1155556666'", null);


        String[] TIPO_LLAMADA = {"", "entrante", "saliente",
                "perdida", "mensaje de voz", "cancelada",
                "lista bloqueados"};

        TextView salida = (TextView) findViewById(R.id.salida);
        salida.setMovementMethod(new ScrollingMovementMethod());

        Uri llamadas = Uri.parse("content://call_log/calls");

        String[] proyeccion = new String[]{CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE};
        String[] arrgSelec = new String[]{"1"};
        Cursor c = getContentResolver().query(
                llamadas,
                proyeccion,
                "type = ?",
                arrgSelec,
                "date DESC");
        while (c.moveToNext()) {
            salida.append("\n"
                    + DateFormat.format("dd/MM/yy k:mm (",
                    c.getLong(c.getColumnIndex(CallLog.Calls.DATE)))
                    + c.getString(c.getColumnIndex(CallLog.Calls.DURATION)) + ") "
                    + c.getString(c.getColumnIndex(CallLog.Calls.NUMBER)) + ", "
                    + TIPO_LLAMADA[Integer.parseInt(c.getString(
                    c.getColumnIndex(CallLog.Calls.TYPE)))]
            );


        }
        c.close();
    }
}