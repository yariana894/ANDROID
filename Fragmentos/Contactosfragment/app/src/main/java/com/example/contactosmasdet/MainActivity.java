package com.example.contactosmasdet;

import com.example.contactosmasdet.FragmentLista.ContactoListener;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

/*La Clase MainActivity debe heredar de FragmentActivity, ya que se trata de la clase base para
poder usar Fragments en una Activity*/
public class MainActivity extends FragmentActivity implements ContactoListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se declara y enlaza la clase FragmentLista con el componente definido a nivel de layout
        FragmentLista frgListado =(FragmentLista)getSupportFragmentManager().findFragmentById(R.id.fragmentListaContactos);

        //Se realiza la llamada al metodo setContactoListener() indicandolo como parametro el contexto
        frgListado.setContactoListener(this);
    }

    //Metodo que se ejecutara cuando se seleccione un contacto de la lista.
    @Override
    public void onContactoSeleccionado(Contacto c) {
        boolean detalle = (getSupportFragmentManager().findFragmentById(R.id.fragmentInformacion) != null);

        if(detalle) {
            ((FragmentInfoContacto)getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentInformacion))
                    .mostrarDetalle("Nombre: " + c.getNombre() + "\nApellidos: " + c.getApellidos()
                            + "\nTelefono: " + c.getTelefono() + "\nDireccion: " + c.getDireccion());
        }
    }
}
