package com.example.intentodeshakespeare;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

//Extendemos de un listFragments que crea una lista de los elementos que le pasemos
public class FragmentoPrincipal extends ListFragment {



    //PREPARAMOS UNA INTERFAZ PARA PASAR INFO
    public interface OnPrincipalSelectedListener {
        public void onItemSelected(String nombrePais, int imagen);
    }

    private OnPrincipalSelectedListener miOnPrincipalSelectedListener = null;

    //pasamos una instancia de la interfaz y la asignamos a la declarada en esta clase
    public void ListenerFragmentPrincipal(OnPrincipalSelectedListener listener) {
        miOnPrincipalSelectedListener = listener;
    }

    //CREAMOS LA VISTA Y DAMOS SOPORTE AL METODO DE LA INTERFAZ
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListAdapter listaPaisesAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, BaseDatos.DATOS_FRAGMENTO_PRINCIPAL); //BaseDatos

        setListAdapter(listaPaisesAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //Listener  para saber que iten pulsamos de la lista
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View
                    view, int position, long id) {
                if (miOnPrincipalSelectedListener != null) {
                    miOnPrincipalSelectedListener.onItemSelected(BaseDatos.DATOS_FRAGMENTO_SECUNDARIO[position], position); //BaseDatos
                }
            }
        });
    }
}
