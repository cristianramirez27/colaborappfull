package com.coppel.rhconecta.dev.presentation.home;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.URL_RESERVATIONS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.URL_MAIN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN_USER;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.di.home.DaggerHomeComponent;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Badge;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.home.adapter.BannerViewPagerAdapter;
import com.coppel.rhconecta.dev.presentation.home.fragment.BannerFragment;
import com.coppel.rhconecta.dev.presentation.poll.PollActivity;
import com.coppel.rhconecta.dev.presentation.release_detail.ReleaseDetailActivity;
import com.coppel.rhconecta.dev.presentation.room.ReaderQrFragment;
import com.coppel.rhconecta.dev.presentation.room.dialog.DialogInputRoom;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;
import com.coppel.rhconecta.dev.presentation.visionary_detail.VisionaryDetailActivity;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.resources.db.models.MainSection;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.HomeMenuRecyclerViewAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.HomeMenuItemTouchHelperCallback;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/* */
public class HomeMainFragment
        extends Fragment
        implements HomeMenuRecyclerViewAdapter.OnItemClick, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = HomeMainFragment.class.getSimpleName();
    private HomeActivity parent;
    private HomeMenuRecyclerViewAdapter homeMenuRecyclerViewAdapter = null;
    private ProfileResponse.Response profileResponse;

    int[] notifications = {0,0,0};
    @BindView(R.id.vpBanner)
    ViewPager vpBanner;
    @BindView(R.id.tabIndicator)
    ViewPagerIndicator tabIndicator;
    @BindView(R.id.rcvMenu)
    RecyclerView rcvMenu;
    @BindView(R.id.txvFavorites)
    TextView txvFavorites;
    @BindView(R.id.room_container)
    ConstraintLayout room;
    @BindView(R.id.room_check)
    ConstraintLayout roomCheck;
    private long mLastClickTime = 0;
    private boolean reloadDashboard;
    private ISurveyNotification ISurveyNotification;
    private DialogFragmentWarning dialogFragmentWarning;
    @Inject
    public HomeViewModel homeViewModel;
    @Inject
    public RoomsViewModel roomsViewModel;
    private ViewPager viewPagerBanners;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateDashboard();
        }
    };

    private IntentFilter filter = new IntentFilter(AppConstants.INTENT_NOTIFICATION_ACTON);
    private float deltaX = 0f;
    private float oldX = 0f;
    private float positionX = 0f;
    private boolean isFirst = false;
    private static String TAG_DIALOG_ROOM = "TAG_DIALOG_ROOM";
    private DialogFragmentWarning dialog = null;
    private DateFormat date = new SimpleDateFormat("z",Locale.getDefault());
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
    private ReaderQrFragment dialogQrRoom = ReaderQrFragment.Companion.newInstance();
    private DialogInputRoom dialogInputRoom = new DialogInputRoom();
    private DialogFragmentWarning.OnOptionClick onOptionClick = new DialogFragmentWarning.OnOptionClick (){

        @Override
        public void onLeftOptionClick() {
            dialog.close();
            dialogQrRoom.show(requireActivity().getSupportFragmentManager(),ReaderQrFragment.class.getSimpleName());
        }

        @Override
        public void onRightOptionClick() {
            dialog.close();
            System.out.printf("dialog Inputs");
            dialogInputRoom.show(requireActivity().getSupportFragmentManager(),DialogInputRoom.class.getSimpleName());
        }
    };

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
//        ((HomeActivity) Objects.requireNonNull(getActivity())).showProgress();
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);
        ButterKnife.bind(this, view);
        parent = (HomeActivity) getActivity();
        profileResponse = parent.getProfileResponse();
        // Poll icon
        initMenu();
        ISurveyNotification.getSurveyIcon().setVisibility(View.VISIBLE);
        ISurveyNotification.getSurveyIcon().setCountMessages(0);
        ISurveyNotification.getSurveyIcon().setOnClickListener(this::onSurveyIconClickListener);
        return view;
    }

    /**
     *
     */
    private void onSurveyIconClickListener(View view) {
        List<MainSection> sections = MenuUtilities.getMainSection();
        if (MenuUtilities.isFilial(getContext()) && !MenuUtilities.findItem(sections, 19)) {
            return;
        }
        // Verifica que la opcion de encuestas este disponible
        if (AppUtilities.getStringFromSharedPreferences(requireContext(), BLOCK_ENCUESTAS).equals(YES)) {
            showBlockOptionWarningDialog(BLOCK_MESSAGE_ENCUESTAS);
            return;
        }
        // Verifica que los badges hayan sido cargados correctamente.
        if (homeViewModel.getBadges() == null) {
            String message = getString(R.string.not_available_service);
            SingleActionDialog dialog = new SingleActionDialog(
                    getActivity(),
                    getString(R.string.home_fragment_failure_title),
                    message,
                    getString(R.string.home_fragment_failure_action),
                    null
            );
            dialog.setCancelable(false);
            try {
                dialog.show();
            } catch (Exception ignore) { /* PASS */ }
            return;
        }
        // Coloca la informacion en la opcion de encuesta
        Badge badge = homeViewModel.getBadges().get(Badge.Type.POLL);
        if (badge == null) return;
        if (badge.getValue() > 0) {
            Intent intent = new Intent(getContext(), PollActivity.class);
            startActivity(intent);
        } else {
            SingleActionDialog dialog = new SingleActionDialog(
                    getActivity(),
                    getString(R.string.not_available_poll_title),
                    getString(R.string.not_available_poll_content),
                    getString(R.string.not_available_poll_action),
                    null
            );
            dialog.show();
        }
    }

    /**
     *
     */
    private void showBlockOptionWarningDialog(String key) {
        try {
            String message = AppUtilities
                    .getStringFromSharedPreferences(requireContext(), key);
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(this);
            dialogFragmentWarning.show(
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                    DialogFragmentWarning.TAG
            );
        } catch (Exception ignore) { /* IGNORE */ }
    }

    /**
     *
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerHomeComponent.create().inject(this);
        dialog = new DialogFragmentWarning();
        dialog.setTwoOptionsData(
                getString(R.string.title_dialog_rooms),
                getString(R.string.msg_dialog_rooms),
                getString(R.string.option_qr_dialog_rooms),
                getString(R.string.option_input_dialog_rooms)
        );
        dialog.setOnOptionClick(onOptionClick);
        initViews();
        observeViewModel();
        String tokenUser = AppUtilities.getStringFromSharedPreferences(requireContext(), SHARED_PREFERENCES_TOKEN_USER);
        String url = AppUtilities.getStringFromSharedPreferences(requireContext(), URL_RESERVATIONS);
        String main = AppUtilities.getStringFromSharedPreferences(requireContext(), URL_MAIN);
        Log.e("Main","url is -> "+main);
        roomsViewModel.init(url,tokenUser, date.format(calendar.getTime()));
    }

    /**
     *
     */
    @Override
    public void onStart() {
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, filter);
        super.onStart();
        execute();
        if (getActivity() instanceof HomeActivity)
            ((HomeActivity) getActivity()).checkoutAnalyticsTime();
    }

    /**
     *
     */
    private void initViews() {
        initBannersViews();
        room.setOnTouchListener((v, event) -> {
            int cardWidth = room.getWidth();
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float metrics = (float) displayMetrics.widthPixels;
            float start = (float) ((displayMetrics.widthPixels/2) - (cardWidth/2));

            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    isFirst = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newX = event.getRawX();
                    if(!isFirst){
                        if (positionX >= 0f)
                            positionX = start;
                        oldX = newX;
                        isFirst =  true;
                        return true;
                    }
                    deltaX = newX - oldX;
                    oldX = newX;

                    if (deltaX != 0.0f && (newX - cardWidth) < start) {
                        positionX += deltaX;
                        Log.d("Aprox","X "+positionX);
                        float min = Math.min(start, positionX);
                        Log.d("Values", start + " --- $" + newX + "-- [ " + oldX + "-- " + deltaX + " ]-- $" + metrics + "  ---- {" + (newX - cardWidth ) + "} ---> " + min);
                        room.animate().x(min).setDuration(0).start();
                    }
                    break;
            }
            return true;
        });
        roomCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.show(requireActivity().getSupportFragmentManager(),TAG_DIALOG_ROOM);
            }
        });
    }

    /**
     *
     */
    private void observeViewModel() {
        homeViewModel.getLoadBadgesProcessStatus().observe(this, this::getLoadBadgesObserver);
        homeViewModel.getLoadBannersProcessStatus().observe(this, this::getLoadBannersObserver);
    }

    /**
     *
     */
    private void execute() {
        homeViewModel.loadBadges();
        homeViewModel.loadBanners();
    }

    /**
     *
     */
    private void getLoadBannersObserver(ProcessStatus processStatus) {
        assert getView() != null;
        View loader = getView().findViewById(R.id.pbLoader);
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                if (isOnline()) {
                    Failure failure = homeViewModel.getFailure();
                    String message = getString(R.string.not_available_service);
                    SingleActionDialog dialog = new SingleActionDialog(
                            getActivity(),
                            getString(R.string.home_fragment_failure_title),
                            message,
                            getString(R.string.home_fragment_failure_action),
                            null
                    );
                    dialog.setCancelable(false);
                    try {
                        dialog.show();
                    } catch (Exception ignore) {
                    }
                }
                break;
            case COMPLETED:
                if (homeViewModel.getBanners() != null)
                    setBanners(homeViewModel.getBanners());
                break;
        }
    }

    /**
     *
     */
    private void getLoadBadgesObserver(ProcessStatus processStatus) {
        assert getView() != null;
        View loader = getView().findViewById(R.id.pbLoader);
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Failure failure = homeViewModel.getFailure();
                String message = getString(R.string.not_available_service);
                SingleActionDialog dialog = new SingleActionDialog(
                        getContext(),
                        getString(R.string.home_fragment_failure_title),
                        message,
                        getString(R.string.home_fragment_failure_action),
                        null
                );
                dialog.setCancelable(false);
                setBadges(new HashMap<>());
                try {
                    dialog.show();
                } catch (Exception ignore) {
                }
                break;
            case COMPLETED:
                if (homeViewModel.getBadges() != null) {
                    ((HomeActivity) requireActivity()).checkZendeskFeature();
                    setBadges(homeViewModel.getBadges());
                }
                break;
        }
    }

    /**
     *
     */
    private void initBannersViews() {
        assert getView() != null;
        viewPagerBanners = getView().findViewById(R.id.vpBanner);
    }

    /**
     *
     */
    private void setBanners(List<Banner> banners) {
        viewPagerBanners.setAdapter(
                new BannerViewPagerAdapter(
                        getChildFragmentManager(),
                        banners,
                        getOnBannerClickListener()
                )
        );
        tabIndicator.setupWithViewPager(viewPagerBanners);
    }

    /**
     *
     */
    private void setBadges(Map<Badge.Type, Badge> badges) {
        if (badges.isEmpty()) {
            HashMap<Badge.Type, Badge> map = new HashMap<>();
            map.put(Badge.Type.RELEASE, new Badge(0, Badge.Type.RELEASE));
            map.put(Badge.Type.VISIONARY, new Badge(0, Badge.Type.VISIONARY));
            map.put(Badge.Type.COLLABORATOR_AT_HOME, new Badge(0, Badge.Type.COLLABORATOR_AT_HOME));
            map.put(Badge.Type.POLL, new Badge(0, Badge.Type.POLL));
            badges = map;
        }
        notifications = new int[]{
                Objects.requireNonNull(badges.get(Badge.Type.RELEASE)).getValue(),
                Objects.requireNonNull(badges.get(Badge.Type.VISIONARY)).getValue(),
                Objects.requireNonNull(badges.get(Badge.Type.COLLABORATOR_AT_HOME)).getValue()
        };
        updateDashboard();
        int countMessages = Objects.requireNonNull(badges.get(Badge.Type.POLL)).getValue();
        ISurveyNotification.getSurveyIcon().setCountMessages(countMessages);
    }

    /**
     *
     */
    private BannerFragment.OnBannerClickListener getOnBannerClickListener() {
        return banner -> {
            if (!isOnline()) {
                showConecctionError();
                return;
            }
            Intent intent;
            AccessOption accessOption = AccessOption.BANNER;
            if (banner.isRelease()) {
                intent = new IntentBuilder(new Intent(getContext(), ReleaseDetailActivity.class))
                        .putIntExtra(ReleaseDetailActivity.RELEASE_ID, Integer.parseInt(banner.getId()))
                        .putSerializableExtra(ReleaseDetailActivity.ACCESS_OPTION, accessOption)
                        .build();
                startActivity(intent);
            }
            if (banner.isVisionary() || banner.isVisionaryAtHome()) {
                VisionaryType type = banner.isVisionary() ?
                        VisionaryType.VISIONARIES : VisionaryType.COLLABORATOR_AT_HOME;
                intent = new IntentBuilder(new Intent(getContext(), VisionaryDetailActivity.class))
                        .putStringExtra(VisionaryDetailActivity.VISIONARY_ID, banner.getId())
                        .putStringExtra(VisionaryDetailActivity.VISIONARY_IMAGE_PREVIEW, banner.getImage())
                        .putSerializableExtra(VisionaryDetailActivity.VISIONARY_TYPE, type)
                        .putSerializableExtra(VisionaryDetailActivity.ACCESS_OPTION, accessOption)
                        .build();
                startActivity(intent);
            }

            if (banner.isLink() && banner.getUrlLink() != null) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner.getUrlLink()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        // Chrome browser presumably not installed so allow user to choose instead
                        intent.setPackage(null);
                        startActivity(intent);
                    } catch (Exception ignore) { /* PASS */ }
                }
            }

        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ISurveyNotification = (ISurveyNotification) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isOnline()) {
            showConecctionError();
            return;
        }
        if (reloadDashboard) {
            reloadDashboard = false;
            updateDashboard();
        }
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver);
        super.onPause();
        try {
            RealmHelper.updateMenuItems(profileResponse.getCorreo(), homeMenuRecyclerViewAdapter.getCustomMenu());
//            ((HomeActivity) Objects.requireNonNull(getActivity())).hideProgress();
            reloadDashboard = true;
        } catch (Exception ignore) {
        }
    }

    /**
     *
     */
    private void initMenu() {
        rcvMenu.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvMenu.setLayoutManager(gridLayoutManager);
        homeMenuRecyclerViewAdapter = new HomeMenuRecyclerViewAdapter(
                getContext(),
                getItemsMenu(),
                gridLayoutManager.getSpanCount()
        );
        homeMenuRecyclerViewAdapter.setOnItemClick(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new HomeMenuItemTouchHelperCallback(homeMenuRecyclerViewAdapter));
        rcvMenu.setAdapter(homeMenuRecyclerViewAdapter);
        itemTouchHelper.attachToRecyclerView(rcvMenu);
    }

    @Override
    public void onItemClick(String tag) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) return;
        mLastClickTime = SystemClock.elapsedRealtime();
        AccessOption accessOption = AccessOption.ICON;
        parent.navigationMenu(tag, accessOption);
    }

    @Override
    public Context getContext() {
        return parent.getBaseContext();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onLeftOptionClick() {
    }

    @Override
    public void onRightOptionClick() {
        dialogFragmentWarning.close();
    }

    public void showConecctionError() {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), getString(R.string.network_error), getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    private void updateDashboard() {
        homeMenuRecyclerViewAdapter.setCustomMenuUpdate(getItemsMenu());
        homeMenuRecyclerViewAdapter.notifyDataSetChanged();
    }

    private List<HomeMenuItem> getItemsMenu() {
        synchronized (this) {
            return MenuUtilities.getHomeMenuItems(parent, profileResponse.getCorreo(), false, notifications, false);
        }
    }

}
