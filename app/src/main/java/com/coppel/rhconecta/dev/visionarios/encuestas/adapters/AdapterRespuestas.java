package com.coppel.rhconecta.dev.visionarios.encuestas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Respuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;

import java.util.ArrayList;

public class AdapterRespuestas extends BaseAdapter {

    static class ViewHolder {
        RadioButton radioRespuesta;
    }

    private static final String TAG = "CustomAdapterRespuesta";
    private static int convertViewCounter = 0;

    private ArrayList<Respuesta> data;
    private LayoutInflater inflater = null;

    private Context fromContext;

    public AdapterRespuestas(Context c, ArrayList<Respuesta> d) {
        this.data = d;
        this.fromContext = c;
        this.inflater = LayoutInflater.from(c);
    }

    public void setCheckedRespuesta(int idx) {
        resetRespuestas();
        this.data.get(idx).setSeleccionado(true);
        notifyDataSetChanged();
    }

    public void resetRespuestas() {
        for (int i = 0; i < this.data.size(); i++) {
            this.data.get(i).setSeleccionado(false);
        }
        notifyDataSetChanged();
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.fila_lista_respuestas, null);
            convertViewCounter++;
            holder = new ViewHolder();
            holder.radioRespuesta = (RadioButton) convertView.findViewById(R.id.radioRespuesta);
            holder.radioRespuesta.setOnClickListener(onClickMetodo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Respuesta r = (Respuesta) getItem(position);
        holder.radioRespuesta.setTag(position);

        // Agrega los valores a los elementos de la view
        holder.radioRespuesta.setText(r.getContenido());
        if (!r.isSeleccionado()) { //marca como nuevo el comunicado
            holder.radioRespuesta.setChecked(false);
        }

        return convertView;
    }

    private View.OnClickListener onClickMetodo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final int idx = (int) v.getTag();
            resetRespuestas();
            Respuesta r = (Respuesta) getItem(idx);
            r.setSeleccionado(true);

            if (v.getContext() instanceof EncuestaActivity) {
                ((EncuestaActivity) v.getContext()).setCheckRespuesta(idx);
            }
            notifyDataSetChanged();


        }
    };
}
