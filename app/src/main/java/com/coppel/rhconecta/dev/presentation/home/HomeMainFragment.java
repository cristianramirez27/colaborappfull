package com.coppel.rhconecta.dev.presentation.home;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.NotificationEvent;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.di.DaggerDiComponent;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.home.adapter.BannerViewPagerAdapter;
import com.coppel.rhconecta.dev.presentation.home.fragment.BannerFragment;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.HomeBannerPagerAdapter;
import com.coppel.rhconecta.dev.views.adapters.HomeMenuRecyclerViewAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.modelview.BannerItem;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.HomeMenuItemTouchHelperCallback;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.comunicados.views.ComunicadosDetalleActivity;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.inicio.interfaces.Inicio;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.ItemCarousel;
import com.coppel.rhconecta.dev.visionarios.inicio.presenters.InicioPresenter;
import com.coppel.rhconecta.dev.visionarios.utils.App;
import com.coppel.rhconecta.dev.visionarios.utils.Config;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.views.VideosDetalleActivity;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMainFragment
        extends Fragment
        implements View.OnClickListener,
        HomeMenuRecyclerViewAdapter.OnItemClick,
        HomeBannerPagerAdapter.OnBannerClick,
        Inicio.View,
        DialogFragmentWarning.OnOptionClick
{

    public static final String TAG = HomeMainFragment.class.getSimpleName();
    private HomeActivity parent;
    private HomeBannerPagerAdapter homeBannerPagerAdapter;
    private HomeMenuRecyclerViewAdapter homeMenuRecyclerViewAdapter;
    private ProfileResponse.Response profileResponse;
    private InicioPresenter presenter;
    DialogFragmentLoader dialogFragmentLoader;
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


    private long mLastClickTime = 0;
    private boolean reloadDashboard;

    //private DialogFragmentLoader dialogFragmentLoader;
    private ISurveyNotification ISurveyNotification;

    private DialogFragmentWarning dialogFragmentWarning;

    /* START Clean architecture attributes */
    @Inject
    public HomeViewModel homeViewModel;
    /* END Clean architecture attributes */

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//COMMENT
        //dialogFragmentLoader = new DialogFragmentLoader();
        // dialogFragmentLoader.show(getActivity().getSupportFragmentManager(), DialogFragmentLoader.TAG);

        ((HomeActivity)getActivity()).showProgress();

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

                if(ISurveyNotification.validateSurveyAccess()){
                    if (ultimaEncuesta != null) {
                        Intent intentEncuesta = new Intent(v.getContext(), EncuestaActivity.class);
                        intentEncuesta.putExtra("encuesta", ultimaEncuesta);
                        startActivity(intentEncuesta);
                    } else {
                        Toast.makeText(getActivity(), "No hay encuestas nuevas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                hideProgress();
            }
        }, 1200);

        return view;
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerDiComponent.create().inject(this);
        observeViewModel();
        homeViewModel.loadBanners();
    }

    /* START Clean architecture functions */

    /**
     *
     *
     */
    private void observeViewModel() {
        homeViewModel.getLoadBannersStatus().observe(this, getLoadBannersObserver());
    }

    /**
     *
     * @return
     */
    private Observer<ProcessStatus> getLoadBannersObserver() {
        View view = getView();
        assert view != null;
        View loader = view.findViewById(R.id.pbLoader);
        return processStatus -> {
            loader.setVisibility(View.GONE);
            if(processStatus != null) {
                switch (processStatus) {
                    case LOADING:
                        loader.setVisibility(View.VISIBLE);
                        break;
                    case FAILURE:
                        // TODO: Validate failure instance
                        String message = homeViewModel.getFailure().toString();
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        break;
                    case COMPLETED:
                        setBanners(homeViewModel.getBanners());
                        break;
                }
            }
        };
    }

    /**
     *
     * @param banners
     */
    private void setBanners(List<Banner> banners){
        assert getView() != null;
        ViewPager viewPager = getView().findViewById(R.id.vpBanner);
        ArrayList<Fragment> fragments = new ArrayList<>(banners.size());
        for(Banner banner: banners)
            fragments.add(BannerFragment.createInstance(banner, onBannerClickListener));
        BannerViewPagerAdapter adapter  = new BannerViewPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabIndicator.setupWithViewPager(viewPager);
    }

    /* */
    private BannerFragment.OnBannerClickListener onBannerClickListener = banner -> {
        Toast.makeText(parent, banner.toString(), Toast.LENGTH_SHORT).show();
    };

    /* END Clean architecture functions */


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification)context;

    }

    @Override
    public void onResume(){
        super.onResume();
        itemsCarousel.clear();//Eliminamos los elementos del carrusel.

        if(!isOnline()){
            showConecctionError();
            return;
        }

        presenter.getUltimaEncuesta();
        presenter.guardarLogin();

        if(reloadDashboard){
            reloadDashboard = false;
            updateDashboard();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        RealmHelper.updateMenuItems(profileResponse.getCorreo(), homeMenuRecyclerViewAdapter.getCustomMenu());

        ((HomeActivity)getActivity()).hideProgress();

        reloadDashboard = true;
        //HIDE
        /*if (dialogFragmentLoader != null) {
            dialogFragmentLoader.dismissAllowingStateLoss();
        }*/
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

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

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

        ((HomeActivity)getActivity()).hideProgress();
       //HIDE
        // dialogFragmentLoader.close();
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

        ((HomeActivity)getActivity()).hideProgress();
       // if(isAdded()){
           // if(dialogFragmentLoader != null)
           //     dialogFragmentLoader.close();
      //  }

    }

    //VISIONARIOS ESTA FUNCIONA YA NO SE USARÁ
    public  void getURL_VISIONARIOS (){
        final String url = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.VISIONARIOS_URL);
        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dbref = firebaseDatabase.getReference(MyFirebaseReferences.DATABASE_REFERENCE_DICCIONARIO);
            dbref.child("config").child("URL_VISIONARIOS").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String str = dataSnapshot.getValue(String.class);
                        CallbackGetURL_VISIONARIOS(str);

                    } else { //si no existe nodo
                        CallbackGetURL_VISIONARIOS(url);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("CONFIG", "error");
                    CallbackGetURL_VISIONARIOS(url);
                }
            });

        } catch (Exception e) {
            CallbackGetURL_VISIONARIOS(url);
            Log.d("CONFIG", e.getMessage());
        }
    }

    public void CallbackGetURL_VISIONARIOS(String url){
        InternalDatabase idb = new InternalDatabase(App.context);
        TableConfig tableConfig = new TableConfig(idb, false);
        Config config = new Config(1,url);
        tableConfig.insertIfNotExist(config);
        tableConfig.closeDB();
        presenter.guardarLogin();
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onLeftOptionClick() {

    }

    @Override
    public void onRightOptionClick() {
        hideProgress();
        dialogFragmentWarning.close();

    }

    public void showConecctionError() {
        /**Se agrega modificación para mostrar Login cuando las credenciales almacenadas
         * no correspoden a las del usuario previamente autenticado.*/

        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.network_error), getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NotificationEvent event) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateDashboard();
            }
        },400);

    }

    private void updateDashboard(){
        homeMenuRecyclerViewAdapter.setCustomMenuUpdate(MenuUtilities.getHomeMenuItems(parent, profileResponse.getCorreo(), false,notifications));
        homeMenuRecyclerViewAdapter.notifyDataSetChanged();
        //initMenu();
        //homeMenuRecyclerViewAdapter.notifyDataSetChanged();
    }

}
