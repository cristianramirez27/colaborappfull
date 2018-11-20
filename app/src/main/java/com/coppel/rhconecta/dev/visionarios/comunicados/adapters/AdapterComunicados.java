package com.coppel.rhconecta.dev.visionarios.comunicados.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.comunicados.views.ComunicadosActivity;

import java.util.ArrayList;

public class AdapterComunicados extends BaseAdapter {

    static class ViewHolder {
        TextView labelTitulo;
        ImageView iconoNuevo;
        TextView labelFecha;
        CardView cardFila;
    }

    private static final String TAG = "CustomAdapterComunicado";
    private static int convertViewCounter = 0;

    private ArrayList<Comunicado> data;
    private LayoutInflater inflater = null;

    private Context fromContext;

    public AdapterComunicados(Context c, ArrayList<Comunicado> d) {
        this.data = d;
        this.fromContext = c;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * 30 Octubre 2018
     * Modificacion: Se modificó la siguiente función para la corrección de la vista del listado de comunicados---
     *
     * **/
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comunicado c = (Comunicado) getItem(position);

        ViewHolder holder;

        if (convertView == null)
        {

            convertView = inflater.inflate(R.layout.fila_lista_comunicados, null);

            convertViewCounter++;

            holder = new ViewHolder();
            holder.cardFila =  (CardView) convertView.findViewById(R.id.cardFila);
            holder.iconoNuevo= (ImageView) holder.cardFila.findViewById(R.id.iconoNuevo);
            holder.labelTitulo = (TextView) holder.cardFila.findViewById(R.id.labelTitulo);
            holder.labelFecha = (TextView) holder.cardFila.findViewById(R.id.labelFecha);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.cardFila.setTag(c);
        String fechaFormateada;

        try{
            String[] strFecha= c.getDate().split("T");
            fechaFormateada=strFecha[0];
        }catch (Exception e){
            fechaFormateada=c.getDate();
        }


        try {
            String[] fechas = fechaFormateada.split("-");
            fechaFormateada=fechas[2]+"-"+fechas[1]+"-"+fechas[0];
        }
        catch(Exception e) {
        }

        holder.labelTitulo.setText(c.getTitulo());
        holder.labelFecha.setText(fechaFormateada);

        if(c.isVisto()){ //marca como nuevo el comunicado
            holder.iconoNuevo.setImageResource(R.drawable.ic_dot_grey_400_36dp);
        }else{
            holder.iconoNuevo.setImageResource(R.drawable.ic_punto_rojo);
        }
        return convertView;
    }

    private View.OnClickListener onClickMetodo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Comunicado c = (Comunicado) v.getTag();

            if (v.getContext() instanceof ComunicadosActivity) {
                // ((ComunicadosActivity)v.getContext()).TODO(d);
            }

        }
    };
}
