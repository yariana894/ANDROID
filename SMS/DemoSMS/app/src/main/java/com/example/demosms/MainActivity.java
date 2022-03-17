package com.example.demosms;

import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ReceptorSMS.onRecibeSMS {

    public final String tag = "DemoSMS";
    ReceptorSMS receptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creamos y registramos el receptor de sms's de manera dinámica
        receptor = new ReceptorSMS();
        registerReceiver(receptor, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        receptor.setOnRecibeSMSListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receptor);
        receptor = null;
    }

    public void EnviarSMS(View v) {
        EditText txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        EditText txt = (EditText) findViewById(R.id.editTextMensaje);
        EnviaSMS(txtTelefono.getText().toString(), txt.getText().toString());
    }

    public void EnviaSMS(String telefono, String mensaje) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefono, null, mensaje, null, null);
            Toast.makeText(getApplicationContext(), "SMS enviado.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS no enviado, por favor, inténtalo otra vez.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRecibeSMS(String origen, String mensaje) {
        TextView t = (TextView) findViewById(R.id.txtRecibirSMS);
        t.setText("Mensaje de " + origen + ":" + mensaje);
    }
}