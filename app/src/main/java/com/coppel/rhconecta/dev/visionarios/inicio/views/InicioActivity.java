package com.coppel.rhconecta.dev.visionarios.inicio.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.comunicados.views.ComunicadosActivity;
import com.coppel.rhconecta.dev.visionarios.comunicados.views.ComunicadosDetalleActivity;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;
import com.coppel.rhconecta.dev.visionarios.inicio.interfaces.Inicio;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.ItemCarousel;
import com.coppel.rhconecta.dev.visionarios.inicio.presenters.InicioPresenter;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.views.VideosActivity;
import com.coppel.rhconecta.dev.visionarios.videos.views.VideosDetalleActivity;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InicioActivity extends AppCompatActivity implements Inicio.View {

    private static String TAG = "InicioActivity";
    private Toolbar toolbar;
    private InicioPresenter presenter;
    private Encuesta ultimaEncuesta;
    private SurveyInboxView surveyInboxView;
    private ImageView badgeComunicados;
    private TextView countBadgeComunicados;
    private ImageView badgeVisionarios;
    private TextView countBadgeVisionarios;

    private CarouselView carouselView;
    private TextView textVisionarios;
    private TextView textComunicados;
    private TextView textCartasLaborales;
    private TextView textEstadoDeCuenta;
    private TextView textBeneficios;
    private TextView textFondoAhorro;

    private ArrayList<ItemCarousel> itemsCarousel = new ArrayList<ItemCarousel>();
    private ArrayList<Video> videosLanding = new ArrayList<Video>();
    private ArrayList<Comunicado> comunicadosLanding = new ArrayList<Comunicado>();

    private int[] imgCarousels = {R.drawable.ic_image_grey_300_48dp};

    private Map<String, String> diccionario = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        presenter = new InicioPresenter(this);
        surveyInboxView = (SurveyInboxView) findViewById(R.id.surveyInbox);
        badgeComunicados = (ImageView) findViewById(R.id.badgeComunicados);
        countBadgeComunicados = (TextView) findViewById(R.id.countBadgeComunicados);
        badgeVisionarios = (ImageView) findViewById(R.id.badgeVisionarios);
        countBadgeVisionarios = (TextView) findViewById(R.id.countBadgeVisionarios);
        textVisionarios = (TextView) findViewById(R.id.textVisionarios);
        textComunicados = (TextView) findViewById(R.id.textComunicados);
        textCartasLaborales = (TextView) findViewById(R.id.textCartasLaborales);
        textEstadoDeCuenta = (TextView) findViewById(R.id.textEstadoDeCuenta);
        textBeneficios = (TextView) findViewById(R.id.textBeneficios);
        textFondoAhorro = (TextView) findViewById(R.id.textFondoAhorro);
        carouselView = (CarouselView) findViewById(R.id.carouselView);


        initializeToolBar();
        presenter.getTextoLabel("aItemsMenu/0", "visionarios", R.id.textVisionarios);
        presenter.getTextoLabel("aItemsMenu/1", "comunicados", R.id.textComunicados);
        presenter.getTextoLabel("aItemsMenu/2", "cartas laborales", R.id.textCartasLaborales);
        presenter.getTextoLabel("aItemsMenu/3", "estado de cuenta", R.id.textEstadoDeCuenta);
        presenter.getTextoLabel("aItemsMenu/4", "beneficios", R.id.textBeneficios);
        presenter.getTextoLabel("aItemsMenu/5", "fondo de ahorro", R.id.textFondoAhorro);
        presenter.getTextoLabel("cMisFavoritos", "mis favoritos", R.id.textFavoritos);
        presenter.getTextoLabel("cTitulo", "inicio", R.id.toolbar);
        presenter.getTextoLabelError("errorInternet", "Sin conexión a internet");
        presenter.guardarLogin();

    }


    /**
     * 6 Noviembre 2018
     * Modificacion: Se agregó la siguiente función para el actualizado de los datos al reanudar la vista.---
     *
     * **/
    @Override
    public void onResume(){
        super.onResume();
        presenter.guardarLogin();
    }

    /**
     * 6 Noviembre 2018
     * Modificacion: Se modificó la siguiente sentencia para el cambio de imagen que e debe de mostrar en landing de comunicados.---
     *
     * **/
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            ItemCarousel item = itemsCarousel.get(position);
            String imagenUrl;

            imageView.setImageResource(imgCarousels[0]);

            if(item.getTypeItem()==1){
                imagenUrl=comunicadosLanding.get(item.getIdxItem()).getImagen_aviso_landing();
            }else{
                imagenUrl=videosLanding.get(item.getIdxItem()).getImagen_video_preview();
            }

            Log.d(TAG,"IMAGENURL:"+imagenUrl);

            if(imagenUrl != null && !imagenUrl.isEmpty()){
                try{
                    Picasso.with(getApplicationContext()).load(imagenUrl).placeholder(imgCarousels[0]).error(imgCarousels[0]).fit().centerCrop().into(imageView);
                }catch (Exception e){
                    Log.d(TAG,e.getMessage());
                    imageView.setImageResource(imgCarousels[0]);

                }
            }else{
                imageView.setImageResource(imgCarousels[0]);
            }


        }
    };

    public void initCarousel() {

        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(itemsCarousel.size());
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {

                ItemCarousel item = itemsCarousel.get(position);


                if (item.getTypeItem() == 1) {
                    initComunicadoDetalle(comunicadosLanding.get(item.getIdxItem()));
                } else {
                    initVideoDetalle(videosLanding.get(item.getIdxItem()));
                }

            }
        });
    }


    @Override
    public void showError() {
        Toast.makeText(getBaseContext(), diccionario.get("errorInternet").toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() { //DESHABILITA EL BOTON DE REGRESAR

        if (true) {
            //TODO
        } else {
            super.onBackPressed();
        }

    }

    void initializeToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intentBack = new Intent(v.getContext(),EncuestaActivity.class);
                //startActivity(intentBack);
            }
        });

        surveyInboxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ultimaEncuesta != null) {
                    Intent intentEncuesta = new Intent(v.getContext(), EncuestaActivity.class);
                    intentEncuesta.putExtra("encuesta", ultimaEncuesta);
                    startActivity(intentEncuesta);
                } else {
                    Toast.makeText(getBaseContext(), "No hay encuestas nuevas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void showUltimaEncuesta(Encuesta encuesta) {
        if (encuesta != null) {
            surveyInboxView.setCountMessages(1);
            ultimaEncuesta = encuesta;
        } else {
            surveyInboxView.setCountMessages(0);
        }
    }

    @Override
    public void showBangesComunicados(int nuevos) {
        if (nuevos > 0) {
            badgeComunicados.setVisibility(View.VISIBLE);
            countBadgeComunicados.setVisibility(View.VISIBLE);
            String strNuevos = "";
            if (nuevos > 99) {
                strNuevos = "99";
            } else if (nuevos > 9) {
                strNuevos = Integer.toString(nuevos);
            } else {
                strNuevos = nuevos + " ";
            }
            countBadgeComunicados.setText(strNuevos);
        } else {
            badgeComunicados.setVisibility(View.GONE);
            countBadgeComunicados.setVisibility(View.GONE);
            countBadgeComunicados.setText("");
        }
    }

    @Override
    public void showBangesVideos(int nuevos) {
        if (nuevos > 0) {
            badgeVisionarios.setVisibility(View.VISIBLE);
            countBadgeVisionarios.setVisibility(View.VISIBLE);
            String strNuevos = "";
            if (nuevos > 99) {
                strNuevos = "99";
            } else if (nuevos > 9) {
                strNuevos = Integer.toString(nuevos);
            } else {
                strNuevos = nuevos + " ";
            }
            countBadgeVisionarios.setText(strNuevos);
        } else {
            badgeVisionarios.setVisibility(View.GONE);
            countBadgeVisionarios.setVisibility(View.GONE);
            countBadgeVisionarios.setText("");
        }
    }

    @Override
    public void ShowTextoDiccionario(String text, int textView) {
        if (textView == R.id.toolbar) {
            toolbar.setTitle(text);
        } else {
            TextView textLabel = (TextView) findViewById(textView);
            if (textLabel != null) {
                textLabel.setText(text);
            }
        }
    }

    public void startVisionarios(View v) {
        Intent intentBack = new Intent(v.getContext(), VideosActivity.class);
        startActivity(intentBack);
    }

    public void startComunicados(View v) {
        Intent intentBack = new Intent(v.getContext(), ComunicadosActivity.class);
        startActivity(intentBack);
    }

    @Override
    public void guardarTextoDiccionario(String key, String text) {
        if (diccionario.containsKey(key)) {
            Log.d("DICCIONARIO", key + " reemplazado");
            diccionario.remove(key);
            diccionario.put(key, text);
        } else {
            Log.d("DICCIONARIO", key + " guardado");
            diccionario.put(key, text);
        }
    }

    @Override
    public void showVideosLanding(ArrayList<Video> videos) {
        videosLanding = videos;

        if (videosLanding != null) {
            for (int i = 0; i < videosLanding.size(); i++) {
                itemsCarousel.add(new ItemCarousel(itemsCarousel.size(), 2, i));
            }
        }


        initCarousel();
    }

    @Override
    public void showComunicadosLanding(ArrayList<Comunicado> comunicados) {
        comunicadosLanding = comunicados;

        if (comunicadosLanding != null) {
            for (int i = 0; i < comunicadosLanding.size(); i++) {
                itemsCarousel.add(new ItemCarousel(itemsCarousel.size(), 1, i));
            }
        }


        presenter.getVideos();

    }

    void initVideoDetalle(Video video) {
        Intent intent = new Intent(this, VideosDetalleActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }

    void initComunicadoDetalle(Comunicado comunicado) {
        Intent intent = new Intent(this, ComunicadosDetalleActivity.class);
        intent.putExtra("comunicado", comunicado);
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
