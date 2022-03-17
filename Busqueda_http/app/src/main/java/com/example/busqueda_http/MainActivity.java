package com.example.busqueda_http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText entrada;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrada = (EditText) findViewById(R.id.EditText01);

        salida = (TextView) findViewById(R.id.TextView01);

        //preparamos el hilo de búsquda
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy
                .Builder().permitNetwork().build());
    }

    public void Buscar(View view) {

        try {
            String palabras = entrada.getText().toString();
            String resultado = resultadoGoogle(palabras);
            salida.append(palabras + " -- " + resultado + "\n");
        } catch (Exception e) {
            salida.append("Error al conectar!!!\n");
            Toast.makeText(this, "Error al conectar!!!\n", Toast.LENGTH_LONG).show();
        }
    }

    String resultadoGoogle(String palabras) throws Exception {

        String pagina = "", devuelve = "";
        //YA NO NOS DEJA BUSCAR EN ESTA API
        URL url = new URL("https://www.google.es/search?hl=en&q=\""
                + URLEncoder.encode(palabras, "UTF-8") + "\"");

        HttpURLConnection conexion = (HttpURLConnection)
                url.openConnection();

        //ESTAMOS INDICANDO EL BROWSER QUE VAMOS A UTILIZAR
        conexion.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0;"
                + "Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
                + "Chrome/88.0.4324.104 Safari/537.36");

        if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conexion.getInputStream()));

            String linea = reader.readLine();
            while (linea != null) {
                pagina += linea;
                linea = reader.readLine();
            }
            reader.close();

            int ini = pagina.indexOf("About");
            if (ini != -1) {
                int fin = pagina.indexOf(" ", ini + 16);
                devuelve = pagina.substring(ini + 6, fin);
            } else {
                devuelve = "no encontrado";
            }
        } else {
            salida.append("Error: " + conexion.getResponseMessage() + "\n");
        }
        conexion.disconnect();
        return devuelve;
    }

}