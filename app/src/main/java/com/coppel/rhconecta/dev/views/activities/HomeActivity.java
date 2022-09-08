package com.coppel.rhconecta.dev.views.activities;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_BENEFICIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_CARTASCONFIG;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COLLAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COMUNICADOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COVID_SURVEY;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_BENEFICIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_CARTASCONFIG;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_COLLAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_COMUNICADOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_COVID_SURVEY;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_PAYSHEET;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_PROFILE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_QR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_SAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_STAYHOME;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_MESSAGE_VISIONARIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_PAYSHEET;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_PROFILE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_QR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_SAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_STAYHOME;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VISIONARIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_VACANCIES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_WHEATHER;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.COLLAGE;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.COVID_SURVEY;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.EXPENSESTRAVEL;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.HOLIDAYS;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.LOGIN_APPS;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.VACANCIES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COCREA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLLABORATOR_AT_HOME;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLLAGE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COVID_SURVEY;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOME;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_LETTERS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_MANAGER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTICE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTIFICATION_EXPENSES_AUTHORIZE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_PAYROLL_VOUCHER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_POLL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_QR_CODE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_VACANTES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_VISIONARIES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_WHEATHER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_EMAIL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_PASS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.URL_DEFAULT_WHEATHER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ZENDESK_DEPARTMENT;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ZENDESK_EXPECTED_MILLIS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ZENDESK_FEATURE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ZENDESK_MOCK_NUMBER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ZENDESK_OUT_SERVICE_MESSAGE;
import static com.coppel.rhconecta.dev.views.utils.AppUtilities.getStringFromSharedPreferences;
import static com.coppel.rhconecta.dev.views.utils.AppUtilities.saveStringInSharedPreferences;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.coppel.rhconecta.dev.BuildConfig;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeManager;
import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.Enums.HolidaysType;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.CoCreaRequest;
import com.coppel.rhconecta.dev.business.models.CoCreaResponse;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.models.ExternalUrlResponse;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidayRolCheckResponse;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.models.RolExpensesResponse;
import com.coppel.rhconecta.dev.business.models.TokenSSORequest;
import com.coppel.rhconecta.dev.business.models.TokenSSOResponse;
import com.coppel.rhconecta.dev.business.models.ZendeskResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesConstants;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.di.analytics.DaggerAnalyticsComponent;
import com.coppel.rhconecta.dev.domain.common.failure.ServerFailure;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.home.HomeMainFragment;
import com.coppel.rhconecta.dev.presentation.poll.PollActivity;
import com.coppel.rhconecta.dev.presentation.profile_actions.ProfileActionsActivity;
import com.coppel.rhconecta.dev.presentation.releases.ReleasesActivity;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionariesActivity;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.resources.db.models.MainSection;
import com.coppel.rhconecta.dev.system.notification.NotificationDestination;
import com.coppel.rhconecta.dev.views.adapters.HomeSlideMenuArrayAdapter;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.views.customviews.ZendeskInboxView;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.fragments.EmploymentLettersMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.LoanSavingFundFragment;
import com.coppel.rhconecta.dev.views.fragments.PayrollVoucherMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.ProfileFragment;
import com.coppel.rhconecta.dev.views.fragments.benefits.BenefitsFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.HolidaysRolMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.holidays.colaborator.ColaboratorHolidaysFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.MyRequestAndControlsFragment;
import com.coppel.rhconecta.dev.views.fragments.travelExpenses.TravelExpensesRolMenuFragment;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.databases.TableUsuario;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;
import com.coppel.rhconecta.dev.visionarios.utils.Config;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.zendesk.service.ErrorResponse;
import com.zendesk.service.ZendeskCallback;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import zendesk.answerbot.AnswerBot;
import zendesk.answerbot.AnswerBotEngine;
import zendesk.chat.Account;
import zendesk.chat.AccountStatus;
import zendesk.chat.Chat;
import zendesk.chat.ChatConfiguration;
import zendesk.chat.ChatEngine;
import zendesk.chat.ChatLog;
import zendesk.chat.ChatProvidersConfiguration;
import zendesk.chat.ObservationScope;
import zendesk.chat.Observer;
import zendesk.chat.VisitorInfo;
import zendesk.core.Zendesk;
import zendesk.messaging.Engine;
import zendesk.messaging.MessagingActivity;
import zendesk.support.Support;
import zendesk.support.SupportEngine;

/* */
public class HomeActivity
        extends AnalyticsTimeAppCompatActivity
        implements IServicesContract.View, View.OnClickListener, ListView.OnItemClickListener,
        ProfileFragment.OnPictureChangedListener, DialogFragmentWarning.OnOptionClick,
        ISurveyNotification, IScheduleOptions {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LoginResponse.Response loginResponse;
    private ProfileResponse.Response profileResponse;
    private Realm realm;
    private DialogFragmentWarning dialogFragmentWarning;
    private int[] notifications;
    @BindView(R.id.dlHomeContainer)
    DrawerLayout dlHomeContainer;
    @BindView(R.id.tbActionBar)
    Toolbar tbActionBar;
    @BindView(R.id.ctlProfile)
    ConstraintLayout ctlProfile;
    @BindView(R.id.clContent)
    CoordinatorLayout clContent;
    @BindView(R.id.lvOptions)
    ListView lvOptions;
    @BindView(R.id.imgvProfile)
    ImageView imgvProfile;
    @BindView(R.id.txvProfileName)
    TextView txvProfileName;
    @BindView(R.id.txvCollaboratorNumber)
    TextView txvCollaboratorNumber;
    @BindView(R.id.ctlLogout)
    ConstraintLayout ctlLogout;
    @BindView(R.id.surveyInbox)
    SurveyInboxView surveyInboxView;
    @BindView(R.id.zendeskInbox)
    ZendeskInboxView zendeskInboxView;

    private boolean requestLogout = false;
    private boolean hideLoader = false;
    private String titleActivity = "";
    private DialogFragmentLoader dialogFragmentLoader;

    private Fragment currentHomeFragment;

    private long mLastClickTime = 0;

    @BindView(R.id.titleToolbar)
    TextView titleToolbar;

    @BindView(R.id.eliminateOption)
    TextView eliminateOption;
    private boolean isOpenMenuToolbar;

    private boolean EXPIRED_SESSION;
    private String goTosection = "";
    private CoppelServicesPresenter coppelServicesPresenter;
    private int externalOption;

    /* */
    @Inject
    public HomeActivityViewModel homeActivityViewModel;

    ObservationScope observationScope;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifications = new int[]{0, 0, 0};
        setContentView(R.layout.activity_home);
        DaggerAnalyticsComponent.create().inject(this);

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        getWindow().setBackgroundDrawable(null);
        initFirebase();

        coppelServicesPresenter = new CoppelServicesPresenter(this, this);

        Bundle bundle = getIntent().getExtras();
        LoginResponse bundleLoginResponse = (LoginResponse) IntentExtension
                .getSerializableExtra(getIntent(), AppConstants.BUNDLE_LOGIN_RESPONSE);
        ProfileResponse bundleProfileResponse = (ProfileResponse) IntentExtension
                .getSerializableExtra(getIntent(), AppConstants.BUNLDE_PROFILE_RESPONSE);
        String bundleGotoSection = IntentExtension
                .getStringExtra(getIntent(), AppConstants.BUNDLE_GOTO_SECTION);

        if (bundle != null && bundleLoginResponse != null && bundleProfileResponse != null) {
            realm = Realm.getDefaultInstance();
            loginResponse = bundleLoginResponse.getData().getResponse();
            profileResponse = bundleProfileResponse.getData().getResponse()[0];

            initNavigationComponents();
            initMenu();
            getData();
            ctlLogout.setOnClickListener(this);

            if (bundle.containsKey(AppConstants.BUNDLE_GOTO_SECTION)) {
                goTosection = bundleGotoSection;
                navigationMenu(goTosection, null);
                hideLoader = true;
            }

        } else finish();

        if (bundle.containsKey(NotificationDestination.NOTIFICATION_DESTINATION)) {
            NotificationDestination destination = (NotificationDestination) IntentExtension
                    .getSerializableExtra(getIntent(), NotificationDestination.NOTIFICATION_DESTINATION);
            navigateToDestination(destination);
        }

        observeViewModel();

        zendeskInboxView.setOnClickListener(view -> {
                    String saveDataExpectedMillis = getStringFromSharedPreferences(this, ZENDESK_EXPECTED_MILLIS);

                    if (saveDataExpectedMillis != null && !saveDataExpectedMillis.isEmpty()) {

                        Long expectedMillis = Long.parseLong(saveDataExpectedMillis);
                        Date currentDate = new Date();
                        Long currentMillis = currentDate.getTime();

                        if (currentMillis > expectedMillis) {
                            verifyAvailableZendesk();
                        } else {
                            handlePreChat();
                        }
                    } else {
                        verifyAvailableZendesk();
                    }
                }
        );
    }

    private void verifyAvailableZendesk() {
        homeActivityViewModel.getHelpDeskServiceAvailability();
    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        checkoutAnalyticsTime();
    }

    /**
     *
     */
    public void checkoutAnalyticsTime() {
        AnalyticsTimeManager atm = getAnalyticsTimeManager();
        if (atm.existsFlow()) {
            AnalyticsFlow analyticsFlow = atm.getAnalyticsFlow();
            long totalTimeInSeconds = atm.end();
            homeActivityViewModel.sendTimeByAnalyticsFlow(analyticsFlow, totalTimeInSeconds);
        }
    }

    /**
     *
     */
    private void observeViewModel() {
        homeActivityViewModel.getSendTimeByAnalyticsFlowStatus()
                .observe(this, this::sendTimeByAnalyticsFlowStatusObserver);

        homeActivityViewModel.getHelpDeskServiceAvailabilityData()
                .observe(this, this::getHelpDeskServiceAvailabilityData);
    }

    /**
     *
     */
    private void sendTimeByAnalyticsFlowStatusObserver(ProcessStatus processStatus) {
        switch (processStatus) {
            case LOADING:
                break;
            case FAILURE:
                getAnalyticsTimeManager().clear();
                if (homeActivityViewModel.getFailure() instanceof ServerFailure) {
                    ServerFailure result = (ServerFailure) homeActivityViewModel.getFailure();
                    if (result.getSessionInvalid()) {
                        EXPIRED_SESSION = true;
                        showWarningDialog(getString(R.string.expired_session));
                    }
                }
                break;
            case COMPLETED:
                getAnalyticsTimeManager().clear();
                break;
        }
    }

    private void getHelpDeskServiceAvailabilityData(ZendeskResponse.Response data) {
        String message = data.getDes_msj();
        String hours = data.getHoras();
        handleAvailabilityZendesk(hours, message);
    }

    /**
     *
     */
    private void navigateToDestination(NotificationDestination notificationDestination) {
        checkoutAnalyticsTime();

        AccessOption accessOption = AccessOption.MENU;
        switch (notificationDestination) {
            case HOLIDAYS:
                new Handler().postDelayed(this::executeOptionHolidays, 0);
                break;
            case VIDEOS:
                navigateToCollaboratorAtHome(accessOption);
                break;
            case VISIONARIES:
                navigateToVisionaries(accessOption);
                break;
            case RELEASES:
                navigateToReleases(accessOption);
                break;
            case POLL:
                navigateToPoll();
                break;
            case MAIN:
                /* Stay here */
                break;
            case SAVING_FOUND:
            case EXPENSES:
            case EXPENSES_AUTHORIZE:
                /* Managed by goto variable */
                break;
        }
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        AppUtilities.setProfileImage(this, profileResponse.getCorreo(), profileResponse.getFotoperfil(), imgvProfile);
    }

    /**
     *
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /**
     *
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    public void hideOptionToolbar() {
        if (isOpenMenuToolbar) {
            isOpenMenuToolbar = false;
            showTitle(true);
            showEliminatedOption(false, "");
        } else
            onBackPressed();
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        if (hideLoader)
            forceHideProgress();
        if (isOpenMenuToolbar) {
            isOpenMenuToolbar = false;
            showTitle(true);
            showEliminatedOption(false, "");
        }
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            finish();
        } else if (backStackEntryCount == 2) {
            setToolbarTitle(getString(R.string.title_home));
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            super.onBackPressed();
        } else if (backStackEntryCount > 2) {
            super.onBackPressed();
        }
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     *
     */
    private void initNavigationComponents() {
        ctlProfile.setOnClickListener(this);
        setSupportActionBar(tbActionBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlHomeContainer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                clContent.setTranslationX(slideOffset * drawerView.getWidth());
                dlHomeContainer.bringChildToFront(drawerView);
                dlHomeContainer.requestLayout();
                dlHomeContainer.setScrimColor(Color.TRANSPARENT);
            }
        };
        dlHomeContainer.addDrawerListener(actionBarDrawerToggle);
    }

    /**
     *
     */
    private void initMenu() {
        String name = profileResponse.getNombreColaborador().split(" ")[0];
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        txvProfileName.setText(getString(R.string.hello) + ", " + name);
        txvCollaboratorNumber.setText(profileResponse.getColaborador());
        HomeSlideMenuArrayAdapter homeSlideMenuArrayAdapter = new HomeSlideMenuArrayAdapter(this, R.layout.item_slider_home_menu, MenuUtilities.getHomeMenuItems(this, profileResponse.getCorreo(), true, notifications, true));
        lvOptions.setAdapter(homeSlideMenuArrayAdapter);
        lvOptions.setOnItemClickListener(this);
    }

    /**
     *
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
            return;
        mLastClickTime = SystemClock.elapsedRealtime();
        HomeMenuItem option = (HomeMenuItem) adapterView.getItemAtPosition(i);
        AccessOption accessOption = AccessOption.MENU;
        navigationMenu(option.getTAG(), accessOption);
    }

    /**
     *
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ctlProfile:
                List<MainSection> sections = MenuUtilities.getMainSection();
                if (MenuUtilities.isFilial(this) && !MenuUtilities.findItem(sections, 8)) {
                    return;
                }
                if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_PROFILE).equals(YES)) {
                    showBlockDialog(BLOCK_MESSAGE_PROFILE);
                } else {
                    Intent intent = new IntentBuilder(new Intent(this, ProfileActionsActivity.class))
                            .putSerializableExtra(AppConstants.BUNLDE_PROFILE_RESPONSE, profileResponse)
                            .build();
                    startActivity(intent);
                }
                break;
            case R.id.ctlLogout:
                dlHomeContainer.closeDrawers();
                dialogFragmentWarning = new DialogFragmentWarning();
                dialogFragmentWarning.setTwoOptionsData(getString(R.string.logout), getString(R.string.logout_sure),
                        getString(R.string.back), getString(R.string.get_out));
                dialogFragmentWarning.setOnOptionClick(this);
                dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
                requestLogout = true;
                break;
        }
    }

    /**
     *
     */
    @Override
    public void pictureChanged() {
        AppUtilities.setProfileImage(this, profileResponse.getCorreo(), profileResponse.getFotoperfil(), imgvProfile);
    }

    /**
     *
     */
    public void replaceFragment(Fragment fragment, String tag) {
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.replace(R.id.flFragmentContainer, fragment, tag).commit();
        dlHomeContainer.closeDrawers();
    }

    /**
     *
     */
    public void navigationMenu(String tag, AccessOption accessOption) {
        checkoutAnalyticsTime();

        if (DeviceManager.isOnline(this)) {
            switch (tag) {
                case OPTION_HOME:
                    fragmentManager.popBackStack(HomeMainFragment.TAG, 0);
                    dlHomeContainer.closeDrawers();
                    getAnalyticsTimeManager().clear();
                    break;
                case OPTION_NOTICE:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COMUNICADOS).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_COMUNICADOS);
                    } else
                        navigateToReleases(accessOption);
                    break;
                case OPTION_PAYROLL_VOUCHER:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_PAYSHEET).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_PAYSHEET);
                    } else {
                        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.PAYROLL_VOUCHER);
                        replaceFragment(new PayrollVoucherMenuFragment(), PayrollVoucherMenuFragment.TAG);
                    }
                    break;
                case OPTION_BENEFITS:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_BENEFICIOS).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_BENEFICIOS);
                    } else {
                        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.BENEFITS);
                        replaceFragment(new BenefitsFragment(), BenefitsFragment.TAG);
                    }
                    break;
                case OPTION_SAVING_FUND:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_SAVINGS).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_SAVINGS);
                    } else {
                        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.SAVING_FUND);
                        replaceFragment(new LoanSavingFundFragment(), LoanSavingFundFragment.TAG);
                        RealmHelper.deleteNotifications(
                                AppUtilities.getStringFromSharedPreferences(
                                        this,
                                        SHARED_PREFERENCES_NUM_COLABORADOR
                                ),
                                9
                        );
                    }
                    break;
                case OPTION_VISIONARIES:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_VISIONARIOS).equals(YES))
                        showBlockDialog(BLOCK_MESSAGE_VISIONARIOS);
                    else
                        navigateToVisionaries(accessOption);
                    break;
                case OPTION_COLLABORATOR_AT_HOME:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_STAYHOME).equals(YES))
                        showBlockDialog(BLOCK_MESSAGE_STAYHOME);
                    else
                        navigateToCollaboratorAtHome(accessOption);
                    break;
                case OPTION_LETTERS:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_CARTASCONFIG).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_CARTASCONFIG);
                    } else {
                        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.LETTERS);
                        replaceFragment(new EmploymentLettersMenuFragment(), EmploymentLettersMenuFragment.TAG);
                    }
                    break;
                case OPTION_EXPENSES:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_EXPENSES).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_TRAVEL_EXPENSES);
                    } else {
                        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.TRAVEL_EXPENSES);
                        if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_GTE)) {
                            replaceFragment(new TravelExpensesRolMenuFragment(), TravelExpensesRolMenuFragment.TAG);
                        } else if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_SUPLENTE)) {
                            getRolType(EXPENSESTRAVEL);
                        } else {
                            replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                        }
                        RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_NUM_COLABORADOR), 11);
                    }
                    break;
                case OPTION_HOLIDAYS:
                    executeOptionHolidays();
                    break;
                case OPTION_NOTIFICATION_EXPENSES_AUTHORIZE:
                    initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.TRAVEL_EXPENSES);
                    replaceFragment(new TravelExpensesRolMenuFragment(), TravelExpensesRolMenuFragment.TAG);
                    NavigationUtil.openActivityToAuthorize(
                            this,
                            GastosViajeActivity.class,
                            BUNDLE_OPTION_TRAVEL_EXPENSES, OPTION_MANAGER
                    );
                    RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_NUM_COLABORADOR), 10);
                    break;
                case OPTION_QR_CODE:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_QR).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_QR);
                    } else {
                        Intent intentQr = new IntentBuilder(new Intent(this, QrCodeActivity.class))
                                .putStringExtra("numEmp", profileResponse.getColaborador())
                                .putStringExtra("emailEmp", profileResponse.getCorreo())
                                .build();
                        startActivity(intentQr);
                    }
                    break;
                case OPTION_COVID_SURVEY:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COVID_SURVEY).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_COVID_SURVEY);
                    } else {
                        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.COVID_SURVEY);
                        externalOption = COVID_SURVEY;
                        ValidateAcces();
                    }
                    break;
                case OPTION_COLLAGE:
                    if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COLLAGE).equals(YES)) {
                        showBlockDialog(BLOCK_MESSAGE_COLLAGE);
                    } else {
                        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.COLLAGE);
                        externalOption = COLLAGE;
                        ValidateAcces();
                    }
                    break;
                case OPTION_COCREA:
                    String token = AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_TOKEN);
                    hideLoader = true;
                    try {
                        getPackageManager().getPackageInfo("com.coppel.cocrea", PackageManager.GET_ACTIVITIES);
                        CoCreaRequest coCreaRequest = new CoCreaRequest(3,
                                AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_EMAIL),
                                AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_PASS),
                                "rhconecta",
                                AppConfig.getVersionApp(),
                                1
                        );
                        coppelServicesPresenter.getCoCrea(coCreaRequest, token);
                    } catch (PackageManager.NameNotFoundException e) {
                        coppelServicesPresenter.getPlayGoogleUrl(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR), 54, token);
                    }
                    break;
                case OPTION_POLL:
                    break;
                case OPTION_WHEATHER:
                    String url = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_WHEATHER);
                    if (url.isEmpty())
                        url = URL_DEFAULT_WHEATHER;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    break;
                case OPTION_VACANTES:
                    String urlCoppel = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), ENDPOINT_VACANCIES);
                    externalOption = VACANCIES;
                    getExternalUrl(urlCoppel);
                    break;
            }
        } else
            showError(new ServicesError(getString(R.string.network_error)));
        forceHideProgress();
    }

    /**
     *
     */
    private void executeOptionHolidays() {
        if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS).equals(YES)) {
            showBlockDialog(BLOCK_MESSAGE_HOLIDAYS);
        } else {
            initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.HOLIDAYS);
            if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_GTE)) {
                replaceFragment(new HolidaysRolMenuFragment(), HolidaysRolMenuFragment.TAG);
            } else if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_SUPLENTE)) {
                getRolType(HOLIDAYS);
            } else {
                getRolType(HOLIDAYS);
            }
            forceHideProgress();
            RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_NUM_COLABORADOR), 10);
        }
    }

    /* */
    private void navigateToReleases(AccessOption accessOption) {
        Intent intentReleases = new IntentBuilder(new Intent(this, ReleasesActivity.class))
                .putSerializableExtra(ReleasesActivity.ACCESS_OPTION, accessOption)
                .build();
        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.RELEASES);
        startActivity(intentReleases);
    }

    /* */
    private void navigateToVisionaries(AccessOption accessOption) {
        Intent intentVisionaries = new IntentBuilder(new Intent(this, VisionariesActivity.class))
                .putSerializableExtra(VisionariesActivity.TYPE, VisionaryType.VISIONARIES)
                .putSerializableExtra(VisionariesActivity.ACCESS_OPTION, accessOption)
                .build();
        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.VISIONARIES);
        startActivity(intentVisionaries);
    }

    /* */
    private void navigateToCollaboratorAtHome(AccessOption accessOption) {
        Intent collaboratorAtHome = new IntentBuilder(new Intent(this, VisionariesActivity.class))
                .putSerializableExtra(VisionariesActivity.TYPE, VisionaryType.COLLABORATOR_AT_HOME)
                .putSerializableExtra(VisionariesActivity.ACCESS_OPTION, accessOption)
                .build();
        initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow.VIDEOS);
        startActivity(collaboratorAtHome);
    }

    /**
     *
     */
    private void navigateToPoll() {
        Intent intent = new Intent(this, PollActivity.class);
        startActivity(intent);
    }

    /**
     *
     */
    private void initAnalyticsTimeManagerByAnalyticsFlow(AnalyticsFlow analyticsFlow) {
        AnalyticsTimeManager atm = getAnalyticsTimeManager();
        atm.start(analyticsFlow);
    }

    /**
     *
     */
    private void ValidateAcces() {
        if (!validateLoginSSO()) {
            String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
            String email = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_EMAIL);
            String password = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_PASS);
            String num_empleado = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR);
            TokenSSORequest tokenSSORequest = new TokenSSORequest(email, password, num_empleado);
            coppelServicesPresenter.getTokenSSO(tokenSSORequest, token);
        } else {
            getExternalUrl();
        }
    }

    private boolean validateLoginSSO() {
        String strLastLogin = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_LAST_SSO_LOGIN);
        Date lastLogin = new Gson().fromJson(strLastLogin, Date.class);
        Date currentTime = Calendar.getInstance().getTime();
        long timeLapse = currentTime.getTime() - lastLogin.getTime();
        long totalHours = TimeUnit.MILLISECONDS.toHours(timeLapse);

        // Se valida si pasaron mas de 6 hrs desde el anterior loggeo de SSO.
        if (totalHours <= 6) {
            return true;
        } else {
            return false;
        }
    }

    private void getExternalUrl(String endPointCoppel) {
        String endPoint = ServicesConstants.GET_ENDPOINT_COLLAGES;
        if (endPointCoppel != null && endPointCoppel.length() > 0) {
            endPoint = ServicesConstants.GET_ENDPOINT_VACANCIES;
        }

        String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getExternalUrl(endPoint, profileResponse.getColaborador(), externalOption, token);
    }

    private void getExternalUrl() {
        String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getExternalUrl(ServicesConstants.GET_ENDPOINT_COLLAGES, profileResponse.getColaborador(), externalOption, token);
    }

    /**
     *
     */
    private void openExternalUrl(String url) {
        Intent intentExternalUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intentExternalUrl.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentExternalUrl.setPackage("com.android.chrome");
        try {
            startActivity(intentExternalUrl);
        } catch (ActivityNotFoundException ex) {
            intentExternalUrl.setPackage(null);
            startActivity(intentExternalUrl);
        }
    }

    /**
     *
     */
    public void setToolbarTitle(String title) {
        titleToolbar.setText(title);
    }

    /**
     *
     */
    public LoginResponse.Response getLoginResponse() {
        return loginResponse;
    }

    /**
     *
     */
    public ProfileResponse.Response getProfileResponse() {
        return profileResponse;
    }

    /**
     *
     */
    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
        if (requestLogout)
            requestLogout = false;
    }

    /**
     *
     */
    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(this);
        } else {
            if (requestLogout) {
                requestLogout = false;
                String token = AppUtilities
                        .getStringFromSharedPreferences(
                                getApplicationContext(),
                                AppConstants.SHARED_PREFERENCES_TOKEN
                        );
                coppelServicesPresenter.requestLogOut(
                        profileResponse.getColaborador(),
                        profileResponse.getCorreo(),
                        token
                );
            }
            dialogFragmentWarning.close();
        }
    }

    /**
     *
     */
    private void initFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful())
                        AppUtilities.closeApp(this);
                });
    }

    /**
     *
     */
    private void getData() {
        InternalDatabase idb = new InternalDatabase(this);

        TableConfig tableConfig = new TableConfig(idb, false);
        Config config = new Config(1, AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.VISIONARIOS_URL));
        tableConfig.insertIfNotExist(config);

        TableUsuario tableUsuario = new TableUsuario(idb, false);


        Usuario usuario = new Usuario(
                1,
                profileResponse.getColaborador(),
                profileResponse.getNombreColaborador(),
                profileResponse.getApellidop(),
                profileResponse.getApellidom(),
                1,
                Integer.parseInt(profileResponse.getCentro()),
                Integer.parseInt(profileResponse.getSexo()),
                profileResponse.getFechanacimiento(),
                profileResponse.getFechaIngreso(),
                profileResponse.getNombrePuesto(),
                profileResponse.getEstado(),
                profileResponse.getNombreRegion());

        tableUsuario.insertIfNotExist(usuario);
        tableUsuario.closeDB();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(HomeMainFragment.TAG);
        currentHomeFragment = new HomeMainFragment();
        fragmentTransaction.add(R.id.flFragmentContainer, currentHomeFragment, HomeMainFragment.TAG).commit();
    }

    /**
     *
     */
    @Override
    public SurveyInboxView getSurveyIcon() {
        return surveyInboxView;
    }

    /**
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment myFragment = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer);
        if (myFragment instanceof ColaboratorHolidaysFragment)
            myFragment.onActivityResult(requestCode, resultCode, data);
    }

    /**
     *
     */
    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.LOGOUT:
                hideProgress();
                AppUtilities.closeApp(this);
                break;
            case ServicesRequestType.GOOGLE_PLAY_URL:
                if (response.getResponse() instanceof ExternalUrlResponse) {
                    ExternalUrlResponse externalUrlResponse = (ExternalUrlResponse) response.getResponse();

                    Intent play = new Intent();
                    play.setAction(Intent.ACTION_VIEW);
                    play.setData(Uri.parse(externalUrlResponse.getData().getResponse().get(0).getClv_urlservicio()));
                    play.setPackage("com.android.vending");
                    startActivity(play);
                }
                break;
            case EXPENSESTRAVEL:
                if (response.getResponse() instanceof RolExpensesResponse) {
                    if (((RolExpensesResponse) response.getResponse()).getData().getResponse().getClv_estatus() == 1) {
                        replaceFragment(new TravelExpensesRolMenuFragment(), TravelExpensesRolMenuFragment.TAG);
                    } else
                        replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                }
                break;
            case ServicesRequestType.HOLIDAYS:
                if (response.getResponse() instanceof HolidayRolCheckResponse) {
                    if (((HolidayRolCheckResponse) response.getResponse()).getData().getResponse().getGerente() == 1 ||
                            ((HolidayRolCheckResponse) response.getResponse()).getData().getResponse().getSuplente() == 1) {
                        replaceFragment(new HolidaysRolMenuFragment(), HolidaysRolMenuFragment.TAG);
                    } else {
                        hideProgress();
                        replaceFragment(new ColaboratorHolidaysFragment(), ColaboratorHolidaysFragment.TAG);
                    }
                }
                break;
            case COLLAGE:
                if (response.getResponse() instanceof ExternalUrlResponse) {
                    ExternalUrlResponse externalUrlResponse = (ExternalUrlResponse) response.getResponse();
                    String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER);
                    String url = String.format("%s%s", externalUrlResponse.getData().getResponse().get(0).getClv_urlservicio(), token);
                    openExternalUrl(url);
                }
                break;
            case COVID_SURVEY:
                if (response.getResponse() instanceof ExternalUrlResponse) {
                    ExternalUrlResponse externalUrlResponse = (ExternalUrlResponse) response.getResponse();
                    String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER);
                    String url = String.format("%s%s", externalUrlResponse.getData().getResponse().get(0).getClv_urlservicio(), token);
                    openExternalUrl(url);
                }
                break;
            case LOGIN_APPS:
                if (response.getResponse() instanceof TokenSSOResponse) {
                    TokenSSOResponse tokenSSOResponse = (TokenSSOResponse) response.getResponse();
                    /* Almacenamos el nuevo token */
                    String newToken = tokenSSOResponse.getToken_user();
                    AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER, newToken);
                    /* Almacenamos la fecha en la que se obtuvo */
                    Date currentTime = Calendar.getInstance().getTime();
                    AppUtilities.saveStringInSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_LAST_SSO_LOGIN, new Gson().toJson(currentTime));

                    getExternalUrl();
                }

                if (response.getResponse() instanceof CoCreaResponse) {
                    CoCreaResponse coCreaResponse = (CoCreaResponse) response.getResponse();
                    String data = coCreaResponse.getData().getResponse().getAuthCode();
                    if (data != null && !data.isEmpty()) {
                        try {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction("com.coppel.cocrea.action.COCREA");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                        } catch (Exception e) {
                            String msg = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.ENDPOINT_COCREA_STORE);
                            showWarningDialog(msg);
                        }
                    }
                }
                break;

            case VACANCIES:
                ExternalUrlResponse externalUrlResponse = (ExternalUrlResponse) response.getResponse();
                if (!externalUrlResponse.getData().getResponse().isEmpty()) {
                    String externalURL = externalUrlResponse.getData().getResponse().get(0).getClv_urlservicio();

                    if (externalURL != null && externalURL.length() > 0) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(externalURL));
                        startActivity(webIntent);
                    }
                }
                break;
        }
    }

    private void handleAvailabilityZendesk(String horas, String mensaje) {
        String[] date = horas.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, Integer.parseInt(date[0]));
        calendar.add(Calendar.MINUTE, Integer.parseInt(date[1]));
        calendar.add(Calendar.SECOND, Integer.parseInt(date[2]));

        Date expectedDate = calendar.getTime();
        Long millisExpected = expectedDate.getTime();

        Date currentDate = new Date();
        Long currentMillis = currentDate.getTime();

        saveStringInSharedPreferences(this, ZENDESK_EXPECTED_MILLIS, String.valueOf(millisExpected));
        saveStringInSharedPreferences(this, ZENDESK_OUT_SERVICE_MESSAGE, mensaje);
        if (currentMillis > millisExpected) {
            handlePreChat();
        } else if (!mensaje.isEmpty()) {
            showWarningDialog(mensaje);
        }
    }

    /**
     *
     */
    private void openCollage(String urlString) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent.setPackage(null);
            startActivity(intent);
        }
    }

    /**
     *
     */
    @Override
    public void showError(ServicesError coppelServicesError) {
        if (coppelServicesError.getMessage() != null) {
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;
                default:
                    new Handler().postDelayed(() -> {
                        dialogFragmentWarning = new DialogFragmentWarning();
                        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                        dialogFragmentWarning.setOnOptionClick(HomeActivity.this);
                        dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
                        hideProgress();
                    }, 300);
                    break;
            }
        }
    }

    /**
     *
     */
    private void showBlockDialog(String key) {
        String message = AppUtilities
                .getStringFromSharedPreferences(getApplicationContext(), key);
        showWarningDialog(message);
    }

    /**
     *
     */
    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    /**
     *
     */
    @Override
    public void showProgress() {
        if (hideLoader) {
            if (dialogFragmentLoader != null && dialogFragmentLoader.isVisible()) {
                dialogFragmentLoader.close();
            }
            dialogFragmentLoader = new DialogFragmentLoader();
            dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
            hideLoader = true;
        }

    }

    /**
     *
     */
    @Override
    public void hideProgress() {
        if (dialogFragmentLoader != null && dialogFragmentLoader.isVisible()) {
            dialogFragmentLoader.dismissAllowingStateLoss();
            dialogFragmentLoader.close();
            hideLoader = false;
        }
    }

    /**
     *
     */
    public void forceHideProgress() {
        if (dialogFragmentLoader != null && dialogFragmentLoader.isVisible()) {
            dialogFragmentLoader.dismissAllowingStateLoss();
            dialogFragmentLoader.close();
            hideLoader = false;
        }
    }

    /**
     *
     */
    private void getRolType(int type) {
        String numEmployer = AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(this, SHARED_PREFERENCES_TOKEN);
        switch (type) {
            case EXPENSESTRAVEL:
                ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_PERMISO_ROL, 2, numEmployer);
                coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData, token);
                break;
            case HOLIDAYS:
                HolidayRequestData holidayRequestData = new HolidayRequestData(HolidaysType.CONSULTA_ROL, 1, numEmployer);
                coppelServicesPresenter.getHolidays(holidayRequestData, token);
                break;
        }
    }

    /**
     *
     */
    @Override
    public boolean validateSurveyAccess() {
        if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_ENCUESTAS).equals(YES)) {
            showBlockDialog(BLOCK_MESSAGE_ENCUESTAS);
            return false;
        }
        return true;
    }

    /**
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer);
        if (f instanceof BenefitsFragment)
            f.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     *
     */
    private void getCollageURL() {
        String token = AppUtilities
                .getStringFromSharedPreferences(
                        getApplicationContext(),
                        AppConstants.SHARED_PREFERENCES_TOKEN
                );
        coppelServicesPresenter.getCollege(profileResponse.getColaborador(), 19, token);
    }

    /**
     *
     */
    @Override
    public void showTitle(boolean show) {
        tbActionBar.setTitle(show ? titleActivity : "");
        changeIconToolbar(show ? R.drawable.ic_left_arrow_black : R.drawable.ic_close_black);
        if (!show) {
            isOpenMenuToolbar = true;
        }
    }

    /**
     *
     */
    @Override
    public void changeIconToolbar(int icon) {
        tbActionBar.setNavigationIcon(icon);
    }

    /**
     *
     */
    @Override
    public void showEliminatedOption(boolean show, String name) {
        eliminateOption.setVisibility(show ? View.VISIBLE : View.GONE);
        eliminateOption.setText(name);
        surveyInboxView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     *
     */
    @Override
    public void showAuthorizeOption(boolean show) { /* USELESS IMPLEMENTATION */ }

    /**
     *
     */
    @Override
    public void setActionEliminatedOption(Command action) {
        eliminateOption.setOnClickListener(action::execute);
    }

    /**
     *
     */
    @Override
    public void setActionAuthorizeOption(Command action) { /* USELESS IMPLEMENTATION */ }

    private String getDeviceName() {
        return Build.MODEL + " " + Build.DEVICE + " " + Build.BRAND;
    }

    public String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return "Android SDK: " + sdkVersion + " (" + release + ")";
    }

    private void handlePreChat() {
        String zendeskOutServiceMessage = getStringFromSharedPreferences(this, ZENDESK_OUT_SERVICE_MESSAGE);
        if (zendeskOutServiceMessage == null || zendeskOutServiceMessage.isEmpty()) {
            initChat();
        } else {
            showWarningDialog(zendeskOutServiceMessage);
        }
    }


    private void zendeskChatEnable() {
        zendeskInboxView.setActive(0);
    }

    private void zendeskChatDisable(boolean resetIdentity) {
        zendeskInboxView.setDisable();
        if (resetIdentity)
            Chat.INSTANCE.resetIdentity();
    }

    public void checkZendeskFeature() {
        String zendeskFeature = getStringFromSharedPreferences(this, ZENDESK_FEATURE);

        if (zendeskFeature != null && zendeskFeature.equals("1")) {
            initZendeskInstance();
            enableZendeskFeature();
        } else {
            disableZendeskFeature();
        }
    }

    private void enableZendeskFeature() {
        zendeskInboxView.setVisibility(View.VISIBLE);
        zendeskInboxView.setCountMessages(0);
    }

    private void disableZendeskFeature() {
        zendeskInboxView.setVisibility(View.GONE);
    }

    /**
     * Methods zendesk
     */

    private void initZendeskInstance() {
        Zendesk.INSTANCE.init(
                this,
                BuildConfig.ZENDESK_URL,
                BuildConfig.ZENDESK_APPLICATION,
                BuildConfig.ZENDESK_CLIENT
        );
        Support.INSTANCE.init(Zendesk.INSTANCE);
        AnswerBot.INSTANCE.init(Zendesk.INSTANCE, Support.INSTANCE);
        Chat.INSTANCE.init(
                this,
                BuildConfig.ZENDESK_ACCOUNT,
                BuildConfig.ZENDESK_APPLICATION
        );

        observationScope = new ObservationScope();
        configureUserData();
        monitorChatEvent();
        monitorAvailableAgents();

    }

    private void configureUserData() {
        VisitorInfo visitorInfo = VisitorInfo.builder()
                .withName(profileResponse.getNombre())
                .withEmail(profileResponse.getCorreo())
                .withPhoneNumber(ZENDESK_MOCK_NUMBER)
                .build();
        ChatProvidersConfiguration chatProvidersConfiguration = ChatProvidersConfiguration.builder()
                .withVisitorInfo(visitorInfo)
                .withDepartment(ZENDESK_DEPARTMENT)
                .build();

        String employNumber = AppUtilities.getStringFromSharedPreferences(
                this,
                SHARED_PREFERENCES_NUM_COLABORADOR
        );

        String job = profileResponse.getNombrePuesto();
        String department = profileResponse.getCentro();
        String deviceName = getDeviceName();
        String versionAndroid = getAndroidVersion();
        String versionAPP = BuildConfig.VERSION_NAME;

        List<String> tags = Arrays.asList(employNumber, job, department, deviceName, versionAndroid, versionAPP);
        Chat.INSTANCE.providers().profileProvider().addVisitorTags(tags, new ZendeskCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
            }

            @Override
            public void onError(ErrorResponse error) {
            }
        });

        Chat.INSTANCE.setChatProvidersConfiguration(chatProvidersConfiguration);
    }

    @SuppressLint("RestrictedApi")
    private void initChat(){
        Engine answerBotEngine = AnswerBotEngine.engine();
        Engine supportEngine = SupportEngine.engine();
        Engine chatEngine = ChatEngine.engine();

        MessagingActivity.builder()
                .withEngines(answerBotEngine,/* supportEngine,*/ chatEngine)
                .show(this, configChat());
        if (chatEngine != null) {
            chatEngine.isConversationOngoing((engine, isConversationOngoing) -> Log.v("VER->", "ConversationOnGoingCallback: " + isConversationOngoing));
        }
    }

    private ChatConfiguration configChat() {
        return ChatConfiguration.builder()
                .withAgentAvailabilityEnabled(true)
                .withTranscriptEnabled(true)
                .build();
    }

    private void monitorChatEvent() {
        Chat.INSTANCE.providers().chatProvider().observeChatState(observationScope, chatState -> {
            //Do something with chat state
            //Log.v("VER->Chat event", "ID:" + chatState.getChatId() + " - " + chatState.getChatComment() + " - " + chatState.getChatSessionStatus());
            System.out.println("JSON de chetevent:");
            System.out.println(new Gson().toJson(chatState));

            if (chatState.getAgents().size() > 0)
                zendeskChatEnable();
            else
                zendeskChatDisable(false);

            if (!chatState.getChatLogs().isEmpty()) {
                ChatLog chatLog = chatState.getChatLogs().get(chatState.getChatLogs().size() - 1);
                if (chatLog instanceof ChatLog.Message && ((ChatLog.Message) chatLog).getDisplayName() != null) {
                    Log.v("VER->ChatEvent", "mensaje: "
                            + chatLog.getChatParticipant() + " - "
                            + ((ChatLog.Message) chatLog).getMessage());

                    Log.v("VER->ChatEvent", "cola: " + chatState.getQueuePosition());
                }

            }

            switch (chatState.getChatSessionStatus()) {
                case ENDED:
                    zendeskChatDisable(true);
                    break;
            }
        });
    }

    private void monitorAvailableAgents() {
        Chat.INSTANCE.providers().accountProvider().getAccount(new ZendeskCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                // Insert your account handling code here
                Log.v("VER-> AvailableAgents", "Status:" + account.getStatus() + " - " + account.getDepartments());
                System.out.println(new Gson().toJson(account));

                if (account.getStatus() == AccountStatus.OFFLINE) {
                    //Toast.makeText(HomeActivity.this, "El servicio del chat por el momento no está activo, favor de enviar correo a soportecolaborapp@coppel.com", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                // Handle error in getting Account here
                Log.v("VER-> AvailableAgents", "Error:" + errorResponse.getReason());
            }
        });

        Chat.INSTANCE.providers().accountProvider().observeAccount(observationScope, new Observer<Account>() {

            @Override
            public void update(Account account) {
                Log.v("VER->AvailableAgentsUpd", "Status:" + account.getStatus() + " - " + account.getDepartments());
            }
        });

    }

    private void monitorConnectionStatus() {
        Chat.INSTANCE.providers().connectionProvider().observeConnectionStatus(observationScope,
                connectionStatus -> {
                    String status = connectionStatus.name();
                    Log.v("VER->ConnectionStatus", "Status:" + connectionStatus.name());
                    if (status.equals("CONNECTED")) {
                        Log.v("VER->ConnectionStatus", "en chat - icono verde");
                    } else if (status.equals("DISCONNECTED")) {
                        Log.v("VER->ConnectionStatus", "si chat o minimizado - icono gris");
                    } else if (status.equals("CONNECTING")) {
                        Log.v("VER->ConnectionStatus", "conectando - icono gris");
                    }
                }
        );
    }

}
