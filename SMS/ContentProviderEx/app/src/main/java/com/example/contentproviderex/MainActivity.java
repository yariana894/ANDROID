package com.example.contentproviderex;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemLongClickListener {
    private final String tag = "SMS:";
    EditText nombre;
    EditText sms;
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.txtNombre);
        sms = (EditText) findViewById(R.id.txtSMS);

        //IMPLEMENTA EL LISTENER EN EL ON CREATE
        l = (ListView) findViewById(R.id.lstContactos);
        l.setOnItemLongClickListener(this);
    }


//metode buscar

    public void buscar(View view) {

        String proyeccion[] = {ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.PHOTO_ID};

        String filtro = ContactsContract.Contacts.DISPLAY_NAME + " like ?";
        String args_filtro[] = {nombre.getText().toString() + "%"};
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, proyeccion, filtro, args_filtro, null);

        List<String> lista_contactos = new ArrayList<String>();

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                //obtener id de contacto
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                //obtener nombre de contacto
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //si tiene teléfono, lo agregamos a la lista
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    lista_contactos.add(name);
                }
            }
        } //mandamos el adapatador a la lista de contactos

        ListView l = (ListView) findViewById(R.id.lstContactos);
        //COMO IMPLEMENTAMOS EL LISTENER ---> en el onCreate
        l.setAdapter(new ArrayAdapter<String>(this, R.layout.fila, lista_contactos));
        cur.close(); //cerrar el cursor
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView t = (TextView) view;
        String nombreContacto = t.getText().toString();
        String proyeccion[] = {ContactsContract.Contacts._ID};
        String filtro = ContactsContract.Contacts.DISPLAY_NAME + " = ?";
        String args_filtro[] = {nombreContacto};
        List<String> lista_contactos = new ArrayList<String>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, proyeccion, filtro, args_filtro, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String identificador = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                EnviarSMS(identificador);
            }
        }
        cur.close();
        return true;
    }

    private void EnviarSMS(String identificador) {
        ContentResolver cr = getContentResolver();
        SmsManager smsManager =
                SmsManager.getDefault();
        String mensaje = ((EditText) findViewById(R.id.txtSMS)).getText().toString();
        Cursor cursorTelefono = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]
                        {identificador}, null);

        while (cursorTelefono.moveToNext()) {
            String telefono = cursorTelefono.getString(
                    cursorTelefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
            try {
                smsManager.sendTextMessage(telefono, null, mensaje, null, null);
                Toast.makeText(this, "SMS ENVIADO", Toast.LENGTH_LONG).show();
                Log.d(tag, "SMS enviado.");
            } catch (Exception e) {
                Toast.makeText(this, "SMS NOT SENT", Toast.LENGTH_LONG).show();
                Log.d(tag, "No se pudo enviar el SMS.");
                e.printStackTrace();
            }
        }
        cursorTelefono.close();
    }
}