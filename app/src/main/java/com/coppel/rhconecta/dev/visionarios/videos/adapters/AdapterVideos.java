package com.coppel.rhconecta.dev.visionarios.videos.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.views.VideosActivity;

import java.util.ArrayList;

public class AdapterVideos extends BaseAdapter {

    ViewHolder holder;
    private class ViewHolder {
        TextView labelTitulo;
        TextView labelEncabezado;
        ImageView imgVideoPreview;
        TextView labelFecha;
        TextView labelVisitas;
    }

    private static final String TAG = "CustomAdapterVideos";
    private static int convertViewCounter = 0;

    protected ArrayList<Video> data;
    private LayoutInflater inflater = null;
    protected static Bitmap imagenes[];

    protected Context fromContext;

    public AdapterVideos(Context c, ArrayList<Video> d, Bitmap[] imagenes) {
        this.data = d;
        this.fromContext = c;
        this.inflater = LayoutInflater.from(c);
        this.imagenes = imagenes;
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
     * Modificacion: Se modificó la siguiente función para la corrección de la vista del listado---
     *
     * **/
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Video v = (Video) getItem(position);

        if (convertView == null)
        {

            convertView = inflater.inflate(R.layout.fila_lista_videos, null);

            convertViewCounter++;

            holder = new ViewHolder();
            holder.imgVideoPreview= (ImageView) convertView.findViewById(R.id.imgVideoPreview);
            holder.labelTitulo = (TextView) convertView.findViewById(R.id.labelTitulo);
            holder.labelEncabezado = (TextView) convertView.findViewById(R.id.labelEncabezado);
            holder.labelFecha = (TextView) convertView.findViewById(R.id.labelFecha);
            holder.labelVisitas = (TextView) convertView.findViewById(R.id.labelVisitas);
            holder.imgVideoPreview= (ImageView) convertView.findViewById(R.id.imgVideoPreview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String date=v.getFecha();
        String fechaFormateada;
        try {
            String[] fechas = date.split("-");
            fechaFormateada=fechas[2]+"-"+fechas[1]+"-"+fechas[0];
        }
        catch(Exception e) {
            fechaFormateada=date;
        }

        holder.labelTitulo.setText(v.getTitulo());
        holder.labelEncabezado.setText("\n"+v.getDescripcion());
        holder.labelFecha.setText(fechaFormateada);
        holder.labelVisitas.setText(v.getVistas()+" Vistas");
        holder.imgVideoPreview.setImageBitmap(imagenes[position]);

        return convertView;
    }


    private View.OnClickListener onClickMetodo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Video v = (Video) view.getTag();

            if (view.getContext() instanceof VideosActivity) {
                // ((VideosActivity)v.getContext()).TODO(d);
            }

        }
    };
}
