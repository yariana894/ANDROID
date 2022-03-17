package com.example.intentodeshakespeare;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentoSecundario extends Fragment {

    public static String KEY_DATOS_UNO = "KEY_DATOS_UNO";
    public static String KEY_IMAGEN = "KEY_IMAGE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_secundario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_DATOS_UNO)) {
            mostrarContenidoSeleccionado(bundle.getString(KEY_DATOS_UNO), Integer.parseInt(bundle.getString(KEY_IMAGEN)));
            Log.i("Chris",bundle.getString(KEY_DATOS_UNO));
        }
    }

    public void mostrarContenidoSeleccionado(String countryName, int image) {  //Posible imageview

        ((TextView)getView().findViewById(R.id.textViewNombrePais)).setText(countryName);
        Log.i("Chris", String.valueOf(image) + " a a");
        ((ImageView) getView().findViewById(R.id.textViewImagen)).setImageResource(BaseDatos.IMAGENES[Integer.valueOf(image)]);
    }


}
