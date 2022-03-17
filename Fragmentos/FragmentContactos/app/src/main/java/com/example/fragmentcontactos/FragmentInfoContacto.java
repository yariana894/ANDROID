package com.example.fragmentcontactos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

//Clase que heredara de Fragment para implementar el metodo onCreateView().
public class FragmentInfoContacto extends Fragment {

    /*Metodo donde se crea el Fragment y se asigna el layout a este. En este Fragment se definen
    los elementos necesarios para mostrar la informacion del contacto seleccionado.*/
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_info_contactos, container, false);
    }

    //Metodo que mostrara los datos del contacto seleccionado.
    public void mostrarDetalle(String texto) {
        TextView txtInformacion =
                (TextView) getView().findViewById(R.id.informacionTV);

        txtInformacion.setText(texto);
    }
}
