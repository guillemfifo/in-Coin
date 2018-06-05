package com.example.moneda2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class AdaptadorDeServeis extends ArrayAdapter {

    public AdaptadorDeServeis(Context context, List objects){
        super(context,0,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Obteniendo una instancia del inflater
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View v = convertView;

        //Comprobando si el View no existe
        if (null == convertView){
            //Si no existe, entonces inflarlo
            v = inflater.inflate(R.layout.item_serveis, parent, false);
        }

        //Obteniendo instancia de los elementos
        TextView servei = (TextView) v.findViewById(R.id.textView_servei);
        TextView horari = (TextView) v.findViewById(R.id.textView_horari);

        //Obteniendo instancia de la Tarea en la posicion actual
        Servei item = (Servei) getItem(position);

        servei.setText(item.getServei());
        horari.setText(item.getHorari());

        //Devolver al ListView la fila creada
        return v;

    }
}
