package com.coppel.rhconecta.dev.presentation.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.di.home.DaggerDiComponent;
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
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;
import com.coppel.rhconecta.dev.presentation.visionary_detail.VisionaryDetailActivity;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.adapters.HomeMenuRecyclerViewAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.HomeMenuItemTouchHelperCallback;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.MESSAGE_FOR_BLOCK;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;

/**
 *
 */
public class HomeMainFragment
        extends Fragment
        implements HomeMenuRecyclerViewAdapter.OnItemClick, DialogFragmentWarning.OnOptionClick {

    public static final String TAG = HomeMainFragment.class.getSimpleName();
    private HomeActivity parent;
    private HomeMenuRecyclerViewAdapter homeMenuRecyclerViewAdapter;
    private ProfileResponse.Response profileResponse;

    int[] notifications;
    @BindView(R.id.vpBanner)
    ViewPager vpBanner;
    @BindView(R.id.tabIndicator)
    ViewPagerIndicator tabIndicator;
    @BindView(R.id.rcvMenu)
    RecyclerView rcvMenu;
    @BindView(R.id.txvFavorites)
    TextView txvFavorites;
    private long mLastClickTime = 0;
    private boolean reloadDashboard;
    private ISurveyNotification ISurveyNotification;
    private DialogFragmentWarning dialogFragmentWarning;
    @Inject
    public HomeViewModel homeViewModel;
    private ViewPager viewPagerBanners;

    /**
     *
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        ((HomeActivity) Objects.requireNonNull(getActivity())).showProgress();
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);
        ButterKnife.bind(this, view);
        parent = (HomeActivity) getActivity();
        profileResponse = parent.getProfileResponse();
        // Poll icon
        ISurveyNotification.getSurveyIcon().setVisibility(View.VISIBLE);
        ISurveyNotification.getSurveyIcon().setCountMessages(0);
        ISurveyNotification.getSurveyIcon().setOnClickListener(this::onSurveyIconClickListener);
        return view;
    }

    /**
     *
     */
    private void onSurveyIconClickListener(View view) {
        // Verifica que la opcion de encuestas este disponible
        if (AppUtilities.getStringFromSharedPreferences(requireContext(), BLOCK_ENCUESTAS).equals(YES)) {
            showBlockOptionWarningDialog();
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
            } catch (Exception ignore) {
            }
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
    private void showBlockOptionWarningDialog() {
        try {
            String message = AppUtilities
                    .getStringFromSharedPreferences(requireContext(), MESSAGE_FOR_BLOCK);
            dialogFragmentWarning = new DialogFragmentWarning();
            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
            dialogFragmentWarning.setOnOptionClick(this);
            dialogFragmentWarning
                    .show(getActivity().getSupportFragmentManager(), DialogFragmentWarning.TAG);
        } catch (Exception ignore) { /* IGNORE */ }
    }

    /**
     *
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerDiComponent.create().inject(this);
        initViews();
        observeViewModel();
    }

    /**
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        execute();
    }

    /**
     *
     */
    private void initViews() {
        initBannersViews();
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
                if (homeViewModel.getBadges() != null)
                    setBadges(homeViewModel.getBadges());
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
        initMenu();
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
            if (banner.isRelease()) {
                intent = new IntentBuilder(new Intent(getContext(), ReleaseDetailActivity.class))
                        .putIntExtra(ReleaseDetailActivity.RELEASE_ID, Integer.parseInt(banner.getId()))
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
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
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
        super.onPause();
        try {
            RealmHelper.updateMenuItems(profileResponse.getCorreo(), homeMenuRecyclerViewAdapter.getCustomMenu());
            ((HomeActivity) Objects.requireNonNull(getActivity())).hideProgress();
            reloadDashboard = true;
        } catch (Exception ignore) {
        }
    }

    private void initMenu() {
        rcvMenu.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvMenu.setLayoutManager(gridLayoutManager);
        homeMenuRecyclerViewAdapter = new HomeMenuRecyclerViewAdapter(
                getContext(),
                MenuUtilities.getHomeMenuItems(parent, profileResponse.getCorreo(), false, notifications),
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
        parent.navigationMenu(tag);
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
        homeMenuRecyclerViewAdapter.setCustomMenuUpdate(MenuUtilities.getHomeMenuItems(parent, profileResponse.getCorreo(), false, notifications));
        homeMenuRecyclerViewAdapter.notifyDataSetChanged();
    }

}
