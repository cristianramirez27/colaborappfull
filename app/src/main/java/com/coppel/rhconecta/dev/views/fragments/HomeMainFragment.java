package com.coppel.rhconecta.dev.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.HomeBannerPagerAdapter;
import com.coppel.rhconecta.dev.views.adapters.HomeMenuRecyclerViewAdapter;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.modelview.BannerItem;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.HomeMenuItemTouchHelperCallback;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;
import com.coppel.rhconecta.dev.visionarios.MainActivity;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.comunicados.views.ComunicadosDetalleActivity;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;
import com.coppel.rhconecta.dev.visionarios.inicio.interfaces.Inicio;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.ItemCarousel;
import com.coppel.rhconecta.dev.visionarios.inicio.presenters.InicioPresenter;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.views.VideosDetalleActivity;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTICE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_VISIONARIES;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMainFragment extends Fragment implements View.OnClickListener, HomeMenuRecyclerViewAdapter.OnItemClick, HomeBannerPagerAdapter.OnBannerClick, Inicio.View{

    public static final String TAG = HomeMainFragment.class.getSimpleName();
    private HomeActivity parent;
    private HomeBannerPagerAdapter homeBannerPagerAdapter;
    private HomeMenuRecyclerViewAdapter homeMenuRecyclerViewAdapter;
    private ProfileResponse.Response profileResponse;
    private InicioPresenter presenter;

    private Encuesta ultimaEncuesta;

    private ArrayList<ItemCarousel> itemsCarousel = new ArrayList<ItemCarousel>();
    private ArrayList<Video> videosLanding = new ArrayList<Video>();
    private ArrayList<Comunicado> comunicadosLanding = new ArrayList<Comunicado>();

    int[] notifications;
    @BindView(R.id.vpBanner)
    ViewPager vpBanner;
    @BindView(R.id.tabIndicator)
    ViewPagerIndicator tabIndicator;
    @BindView(R.id.rcvMenu)
    RecyclerView rcvMenu;
    @BindView(R.id.imgvArrowLeft)
    ImageView imgvArrowLeft;
    @BindView(R.id.imgvArrowRight)
    ImageView imgvArrowRight;
    @BindView(R.id.txvFavorites)
    TextView txvFavorites;
    @BindView(R.id.viewBackFavorites)
    View viewBackFavorites;

    //private DialogFragmentLoader dialogFragmentLoader;
    private ISurveyNotification ISurveyNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification)context;

    }

    @Override
    public void onResume(){
        super.onResume();
        itemsCarousel.clear();//Eliminamos los elementos del carrusel.
        presenter.getUltimaEncuesta();
        presenter.guardarLogin();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);
        ButterKnife.bind(this, view);
        parent = (HomeActivity) getActivity();
        profileResponse = parent.getProfileResponse();
        imgvArrowLeft.setOnClickListener(this);
        imgvArrowRight.setOnClickListener(this);
        presenter = new InicioPresenter(this);
        notifications = new int[]{0,0};
        itemsCarousel.clear();//Eliminamos los elementos del carrusel.
        initMenu();
        presenter.guardarLogin();
        ISurveyNotification.getSurveyIcon().setVisibility(View.VISIBLE);
        ISurveyNotification.getSurveyIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ultimaEncuesta != null) {

                    Intent intentEncuesta = new Intent(v.getContext(), EncuestaActivity.class);
                    intentEncuesta.putExtra("encuesta", ultimaEncuesta);
                    startActivity(intentEncuesta);
                } else {
                    Toast.makeText(getActivity(), "No hay encuestas nuevas", Toast.LENGTH_SHORT).show();
                }

            }
        });


        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                hideProgress();
            }
        }, 60000);*/

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        RealmHelper.updateMenuItems(profileResponse.getCorreo(), homeMenuRecyclerViewAdapter.getCustomMenu());
    }

    private void initBanner() {

        List<BannerItem> banners = new ArrayList<>();

        if (itemsCarousel != null && itemsCarousel.size() > 0) {

            for (ItemCarousel item : itemsCarousel) {
                String imagenUrl;
                if (item.getTypeItem() == 1) {
                    imagenUrl = comunicadosLanding.get(item.getIdxItem()).getImagen_aviso_landing();
                } else {
                    imagenUrl = videosLanding.get(item.getIdxItem()).getImagen_video_preview();
                }
                banners.add(new BannerItem("", "", imagenUrl, parent.getResources().getDrawable(R.drawable.img_visionarios)));
            }
            homeBannerPagerAdapter = new HomeBannerPagerAdapter(getContext(), banners);
            homeBannerPagerAdapter.setOnBannerClick(this);
            vpBanner.setAdapter(homeBannerPagerAdapter);

            tabIndicator.setupWithViewPager(vpBanner);
            tabIndicator.addOnPageChangeListener(mOnPageChangeListener);

        }

        hideProgress();
    }

    @NonNull
    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public
        void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

        }

        @Override
        public
        void onPageSelected(final int position) {

        }

        @Override
        public
        void onPageScrollStateChanged(final int state) {

        }
    };

    private void initMenu() {
        rcvMenu.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvMenu.setLayoutManager(gridLayoutManager);
        homeMenuRecyclerViewAdapter = new HomeMenuRecyclerViewAdapter(getContext(), MenuUtilities.getHomeMenuItems(parent, profileResponse.getCorreo(), false,notifications), gridLayoutManager.getSpanCount());
        homeMenuRecyclerViewAdapter.setOnItemClick(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new HomeMenuItemTouchHelperCallback(homeMenuRecyclerViewAdapter));
        rcvMenu.setAdapter(homeMenuRecyclerViewAdapter);
        itemTouchHelper.attachToRecyclerView(rcvMenu);
        viewBackFavorites.post(new Runnable() {
            @Override
            public void run() {
                int backgroundFavoritesHeight = (homeMenuRecyclerViewAdapter.getItemSize() + txvFavorites.getHeight() + AppUtilities.dpToPx(getResources(), 8));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, backgroundFavoritesHeight);
                viewBackFavorites.setLayoutParams(layoutParams);
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvArrowLeft:
                if (vpBanner.getCurrentItem() != 0) {
                    vpBanner.setCurrentItem(vpBanner.getCurrentItem() - 1);
                }
                break;
            case R.id.imgvArrowRight:
                if (vpBanner.getCurrentItem() != (homeBannerPagerAdapter.getCount() - 1)) {
                    vpBanner.setCurrentItem(vpBanner.getCurrentItem() + 1);
                }
                break;
        }
    }

    @Override
    public void onItemClick(String tag) {
        parent.navigationMenu(tag);
    }

    @Override
    public void onBannerClick(int position) {

        if(itemsCarousel.size()>0){
            try{
                ItemCarousel item = itemsCarousel.get(position);

                if (item.getTypeItem() == 1) {
                    initComunicadoDetalle(comunicadosLanding.get(item.getIdxItem()));
                } else {
                    initVideoDetalle(videosLanding.get(item.getIdxItem()));
                }
            }catch (Exception e ){
                Log.d(TAG,"Banner loading");
            }

        }

    }

    @Override
    public Context getContext() {
        return parent.getBaseContext();
    }

    @Override
    public void showUltimaEncuesta(Encuesta encuesta) {
        if (encuesta != null) {
            ISurveyNotification.getSurveyIcon().setCountMessages(1);
            ultimaEncuesta = encuesta;
        } else {
            ultimaEncuesta = null;
            ISurveyNotification.getSurveyIcon().setCountMessages(0);
        }
    }

    @Override
    public void showBangesComunicados(int nuevos) {
        Log.d(TAG,"Notifications comunicados:"+nuevos);
        notifications[0]=nuevos;
    }

    @Override
    public void showBangesVideos(int nuevos) {
        Log.d(TAG,"Notifications videos visionarios:"+nuevos);
        notifications[1]=nuevos;
        uptadeNotificationsAdapter();
    }

    private void uptadeNotificationsAdapter(){
        homeMenuRecyclerViewAdapter.setNotification(AppConstants.OPTION_NOTICE,notifications[0]);
        homeMenuRecyclerViewAdapter.setNotification(AppConstants.OPTION_VISIONARIES,notifications[1]);
    }


    @Override
    public void ShowTextoDiccionario(String text, int textView) {
    }

    @Override
    public void showError() {
    }

    @Override
    public void guardarTextoDiccionario(String key, String text) {
    }

    @Override
    public void showVideosLanding(ArrayList<Video> videos) {
         Log.d(TAG,"LANDING VIDEOS ");
        videosLanding = videos;
        if(itemsCarousel!=null &&itemsCarousel.size()>0){
            for(int i=0;i<itemsCarousel.size();i++){
                if(itemsCarousel.get(i).getTypeItem()==2){
                    itemsCarousel.remove(i);
                }
            }
        }
        if (videosLanding != null) {
            for (int i = 0; i < videosLanding.size(); i++) {
                itemsCarousel.add(new ItemCarousel(itemsCarousel.size(), 2, i));
            }
        }
        initBanner();
    }

    @Override
    public void showComunicadosLanding(ArrayList<Comunicado> comunicados) {
        Log.d(TAG,"LANDING COMUNICADOS ");

        comunicadosLanding = comunicados;
        itemsCarousel.clear();//Eliminamos los elementos del carrusel.

        if (comunicadosLanding != null) {
            for (int i = 0; i < comunicadosLanding.size(); i++) {
                itemsCarousel.add(new ItemCarousel(itemsCarousel.size(), 1, i));
            }
        }
        presenter.getVideos();
    }

    void initVideoDetalle(Video video) {
        Intent intent = new Intent(getActivity(), VideosDetalleActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }

    void initComunicadoDetalle(Comunicado comunicado) {
        Intent intent = new Intent(getActivity(), ComunicadosDetalleActivity.class);
        intent.putExtra("comunicado", comunicado);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
       // dialogFragmentLoader = new DialogFragmentLoader();
        //dialogFragmentLoader.show(getActivity().getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {

       /* if(isAdded()){
            if(dialogFragmentLoader != null)
                dialogFragmentLoader.close();
        }*/

    }
}
