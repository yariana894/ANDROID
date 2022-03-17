package com.example.intentodeshakespeare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    //Control orientacion
    public boolean dualPanel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentoPrincipal fragmentoPrincipal = null;

        //Instanciamos el fragmento principal para saber saber si la pantalla esta horizontal o verticla
        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        //ventana en vertical
        if(frameLayout != null){
            dualPanel = false;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentoPrincipal = (FragmentoPrincipal) getSupportFragmentManager().findFragmentByTag("MASTER");
            if (fragmentoPrincipal == null) {
                fragmentoPrincipal = new FragmentoPrincipal();
                fragmentTransaction.add(R.id.frameLayout, fragmentoPrincipal, "MASTER");
            }
            FragmentoSecundario fragmentoSecundario = (FragmentoSecundario)getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetail);
            if (fragmentoSecundario != null) {
                fragmentTransaction.remove(fragmentoSecundario);
            }
            fragmentTransaction.commit();

        //Ventana en horizontal
        }else{
            dualPanel = true;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentoPrincipal = (FragmentoPrincipal) getSupportFragmentManager()
                    .findFragmentById(R.id.frameLayoutMaster);
            if (fragmentoPrincipal == null) {
                fragmentoPrincipal = new FragmentoPrincipal();
                fragmentTransaction.add(R.id.frameLayoutMaster, fragmentoPrincipal);
            }
            FragmentoSecundario fragmentoSecundario = (FragmentoSecundario) getSupportFragmentManager()
                    .findFragmentById(R.id.frameLayoutDetail);
            if (fragmentoSecundario == null) {
                fragmentoSecundario = new FragmentoSecundario();
                fragmentTransaction.add(R.id.frameLayoutDetail, fragmentoSecundario);
            }
            fragmentTransaction.commit();
        }

        //Listener del fragmento principal
        fragmentoPrincipal.ListenerFragmentPrincipal(new FragmentoPrincipal.OnPrincipalSelectedListener() {
            @Override
            public void onItemSelected(String nombrePais, int imagen) {
                Log.i("Chris", String.valueOf(imagen));
                enviarSaludoPais(nombrePais, imagen);

            }
        });
    }

    private void enviarSaludoPais(String saludo, int imagen) {
        Log.i("Chris", String.valueOf(imagen));
        FragmentoSecundario fragmentoSecundario;
        if (dualPanel) {
            //Two pane layout
            fragmentoSecundario = (FragmentoSecundario) getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetail);
            fragmentoSecundario.mostrarContenidoSeleccionado(saludo, imagen); //Revisar

        } else {
            // Single pane layout
            fragmentoSecundario = new FragmentoSecundario();
            //pasamos la info
            Bundle bundle = new Bundle();
            bundle.putString(FragmentoSecundario.KEY_DATOS_UNO, saludo);
            bundle.putString(FragmentoSecundario.KEY_IMAGEN, String.valueOf(imagen));
            fragmentoSecundario.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragmentoSecundario);
            //empleo de la pila como almacen de fragmentos creados
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}