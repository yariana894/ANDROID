package com.example.enviaremailasincrono;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Editable;
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

    ListView l;
    private final String tag = "SMS:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l = (ListView) findViewById(R.id.lstContactos);
        l.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        TextView t = (TextView) view;
        String nombreContacto = t.getText().toString();

        String proyeccion[] = {ContactsContract.Contacts._ID};
        String filtro = ContactsContract.Contacts.DISPLAY_NAME + " = ?";
        String args_filtro[] = {nombreContacto};

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                proyeccion, filtro, args_filtro, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                @SuppressLint("Range") String identificador = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                EnviarSMS(identificador);
            }
        }
        cur.close();
        return true;
    }

    //envia un SMS a los teléfonos de un contacto
    private void EnviarSMS(String identificador) {
        HiloAsincrono hilo = new HiloAsincrono();
        hilo.execute(identificador);


    }

    class HiloAsincrono extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            if(Looper.myLooper()==null){   //Looper obligatorio
                Looper.prepare();
            }
            ContentResolver cr = getContentResolver();
           // SmsManager smsManager = SmsManager.getDefault();
            String mensaje = ((EditText) findViewById(R.id.txtSMS)).getText().toString();

            //

            Cursor cursorTelefono = cr.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                    new String[]{params[0]}, null);
            while (cursorTelefono.moveToNext()) {
                @SuppressLint("Range") String telefono = cursorTelefono.getString(cursorTelefono.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                Log.i("Chris", telefono);
                try {
                    //smsManager.sendTextMessage(telefono, null, mensaje, null, null);
                    Intent i = new Intent();
                    Intent chooser = null;
                    i.setAction(Intent.ACTION_SEND);
                    //i.setData(Uri.parse("mailto:"));
                    String para[] = {telefono};
                    i.putExtra(Intent.EXTRA_EMAIL, para);
                    i.putExtra(Intent.EXTRA_SUBJECT, "Saludos desde Android");
                    i.putExtra(Intent.EXTRA_TEXT, mensaje);
                    i.setType("message/rfc822");
                    chooser = i.createChooser(i, "Enviar Email");
                    startActivity(i);
                    Toast toast = Toast.makeText(getApplicationContext(),"Abriendo...",Toast.LENGTH_SHORT);
                    toast.show();
                } catch (Exception e) {
                    Log.d(tag, "No se pudo enviar el SMS.");
                    Toast toast = Toast.makeText(getApplicationContext(),"No se puedo enviar el SMS",Toast.LENGTH_SHORT);
                    toast.show();
                    e.printStackTrace();
                }

            }
            cursorTelefono.close();


            return null;

        }


    }


    @SuppressLint("Range")
    public void Buscar(View v) {
        EditText txtNombre = (EditText) findViewById(R.id.txtContacto);
        EditText mesaje = (EditText) findViewById(R.id.txtSMS);
        Editable limit = txtNombre.getText();
        if(limit.length() <= 1) {
            String proyeccion[] = {ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                    ContactsContract.Contacts.PHOTO_ID};
            String filtro = ContactsContract.Contacts.DISPLAY_NAME + " like ?";
            String args_filtro[] = {txtNombre.getText().toString() + "%"};

            List<String> lista_contactos = new ArrayList<String>();
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    proyeccion, filtro, args_filtro, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    @SuppressLint("Range") String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        lista_contactos.add(name);
                    }
                }
            }
            cur.close();

            ListView l = (ListView) findViewById(R.id.lstContactos);
            l.setAdapter(new ArrayAdapter<String>(this, R.layout.fila_lista, lista_contactos));
            txtNombre.setText("");
            mesaje.setText("");

        }else {
            Toast toast = Toast.makeText(getApplicationContext(),"Solo puedes introducir una letra",Toast.LENGTH_SHORT);
            toast.show();
            txtNombre.setText("");
        }

    }

}

