package com.example.contactosmasdet;


import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

//Clase que heredara de Fragment para implementar los metodos onCreateView() y onActivityCreated().
public class FragmentLista extends Fragment {

    /*Se crea un Array de Objetos Contacto con los campos nombre, apellidos, telefono y direccion
    inicializados*/
    public Contacto[] contacto = new Contacto []
            {
                    new Contacto("Contacto 1","Apellido 1","111111111","C/Calle 1"),
                    new Contacto("Contacto 2","Apellido 2","222222222","C/Calle 2"),
                    new Contacto("Contacto 3","Apellido 3","333333333","C/Calle 3"),
                    new Contacto("Contacto 4","Apellido 4","444444444","C/Calle 4"),
                    new Contacto("Contacto 5","Apellido 5","555555555","C/Calle 5"),
                    new Contacto("Contacto 6","Apellido 6","666666666","C/Calle 6"),
                    new Contacto("Contacto 7","Apellido 7","777777777","C/Calle 7"),
                    new Contacto("Contacto 8","Apellido 8","888888888","C/Calle 8"),
                    new Contacto("Contacto 9","Apellido 9","999999999","C/Calle 9"),
                    new Contacto("Contacto 0","Apellido 0","000000000","C/Calle 0"),
            };
    private ListView listaContactosLV;
    private ContactoListener contactoListener;

    /*Metodo donde se crea el Fragment y se asigna el layout a este. En este Fragment se define
    un control de seleccion ListView para mostrar la lista de contactos.*/
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_lista_contactos, container, false);
    }

    /*Metodo que se ejecutara cuando la Activity del Fragment este completamente
     creada.*/
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //Asignamos al componente ListView el recurso definido a nivel de Layout.
        listaContactosLV = (ListView)getView().findViewById(R.id.listContactos);

        //Cargamos el adaptador en el componente ListView.
        listaContactosLV.setAdapter(new AdapterListaContactos(this));

        //Evento encargado de controlar que elemento de la lista ha sido seleccionado.
        listaContactosLV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if (contactoListener!=null) {
                    contactoListener.onContactoSeleccionado(
                            (Contacto)listaContactosLV.getAdapter().getItem(pos));
                    Contacto contacto = (Contacto)list.getItemAtPosition(pos);
                    Toast.makeText(getActivity(), "Contacto Seleccionado: " + contacto.getNombre()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Se define una interfaz con un metodo que se ejecutara desde el evento onItemClick()
    public interface ContactoListener
    {
        void onContactoSeleccionado(Contacto c);
    }

    public void setContactoListener(ContactoListener contactoListener)
    {
        this.contactoListener = contactoListener;
    }

    //CLASE INTERNA PARA MANEJAR EL ARRAY DE OBJETOS CONTACTO
	/*Se crea el adaptador que extiende de la Clase ArrayAdapter, para construir la vista de cada item
	del componente ListView, con los recursos ya definidos en el layout.*/
    class AdapterListaContactos extends ArrayAdapter<Contacto> {

        Activity context;

        AdapterListaContactos(Fragment context) {
            super(context.getActivity(), R.layout.list_item_contactos, contacto);
            this.context = context.getActivity();
        }

        /*Metodo que se encargara de construir un nuevo objeto View con el layout creado
        a partir de esta nueva Activity, mostrando en cada una de las lineas del ListView
        esta nueva vista.*/
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.list_item_contactos, null);

            TextView tvNombre = (TextView)item.findViewById(R.id.tvNombre);
            tvNombre.setText(contacto[position].getNombre());

            TextView tvTelefono = (TextView)item.findViewById(R.id.tvTelefono);
            tvTelefono.setText(contacto[position].getTelefono());

            return(item);
        }
    }
}
