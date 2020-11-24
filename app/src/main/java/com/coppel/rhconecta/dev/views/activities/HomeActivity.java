package com.coppel.rhconecta.dev.views.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.Enums.HolidaysType;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.CollageResponse;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.models.ExternalUrlResponse;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidayRolCheckResponse;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.LogoutResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.models.RolExpensesResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.home.HomeMainFragment;
import com.coppel.rhconecta.dev.presentation.releases.ReleasesActivity;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionariesActivity;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.adapters.HomeSlideMenuArrayAdapter;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_BENEFICIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_CARTASCONFIG;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COLLAGE;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COMUNICADOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COVID_SURVEY;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_PAYSHEET;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_QR;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_SAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_STAYHOME;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VISIONARIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.MESSAGE_FOR_BLOCK;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.COLLAGE;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.EXPENSESTRAVEL;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.COVID_SURVEY;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLLABORATOR_AT_HOME;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLLAGE;
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
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COVID_SURVEY;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_VISIONARIES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class HomeActivity extends AppCompatActivity implements  IServicesContract.View,View.OnClickListener, ListView.OnItemClickListener, ProfileFragment.OnPictureChangedListener,
        DialogFragmentWarning.OnOptionClick,ISurveyNotification, IScheduleOptions {

    private static final String TAG = "HomeActivity";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LoginResponse.Response loginResponse;
    private ProfileResponse.Response profileResponse;
    private Realm realm;
    private DialogFragmentWarning dialogFragmentWarning;
    private int[] notifications  ;
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

    private boolean requestLogout = false;
    private boolean hideLoader = false;
    private DialogFragmentLoader dialogFragmentLoader;


    private Fragment currentHomeFragment;


    private long mLastClickTime = 0;

    @BindView(R.id.titleToolbar)
    TextView titleToolbar;

    @BindView(R.id.eliminateOption)
    TextView eliminateOption;
    private String titleActivity;
    private boolean isOpenMenuToolbar;

    private boolean EXPIRED_SESSION;
    private String goTosection="";
    private CoppelServicesPresenter coppelServicesPresenter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifications = new int[]{0,0,0};
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        getWindow().setBackgroundDrawable(null);
        initFirebase();

        this.context = this;

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
                navigationMenu(goTosection);
                hideLoader = true;
            }

        } else finish();
    }

    /*
    public boolean editColaborador(String newNumber){
        boolean res = false;
        if (newNumber.equals("") || newNumber.equals(null) || newNumber.equals("0")){
            Toast.makeText(context, "Numero de empleado invalido", Toast.LENGTH_LONG).show();
        } else {
            if (newNumber.length() == 8){
                profileResponse.setColaborador(newNumber);
                initMenu();
                showWarningDialog("Numero de empleado cambiado correctamente");
                res = true;
            } else {
                Toast.makeText(context, "Numero de empleado invalido", Toast.LENGTH_LONG).show();
            }
        }

        return res;
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
        AppUtilities.setProfileImage(this, profileResponse.getCorreo(), profileResponse.getFotoperfil(), imgvProfile);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            /*switch (item.getItemId()) {

                case R.id.menuAnalytic:
                    if (ultimaEncuesta != null) {
                        Intent intentEncuesta = new Intent(this, EncuestaActivity.class);
                        intentEncuesta.putExtra("encuesta", ultimaEncuesta);
                        startActivity(intentEncuesta);
                    } else {
                        Toast.makeText(getBaseContext(), "No hay encuestas nuevas", Toast.LENGTH_SHORT).show();
                    }
                    return true;
            }*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void hideOptionToolbar(){
        if(isOpenMenuToolbar) {
            isOpenMenuToolbar = false;
            showTitle(true);
            showEliminatedOption(false, "");
        }else {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {

        if(hideLoader){
            forceHideProgress();
        }

        if(isOpenMenuToolbar) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

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

    private void initMenu() {
        String name = profileResponse.getNombreColaborador().split(" ")[0];
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        txvProfileName.setText(getString(R.string.hello) + ", " + name);
        txvCollaboratorNumber.setText(profileResponse.getColaborador());
        HomeSlideMenuArrayAdapter homeSlideMenuArrayAdapter = new HomeSlideMenuArrayAdapter(this, R.layout.item_slider_home_menu, MenuUtilities.getHomeMenuItems(this, profileResponse.getCorreo(), true,notifications));
        lvOptions.setAdapter(homeSlideMenuArrayAdapter);
        lvOptions.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();
        HomeMenuItem option = (HomeMenuItem) adapterView.getItemAtPosition(i);
        navigationMenu(option.getTAG());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ctlProfile:
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setOnPictureChanged(this);
                replaceFragment(profileFragment, ProfileFragment.TAG);
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

    @Override
    public void pictureChanged() {
        AppUtilities.setProfileImage(this, profileResponse.getCorreo(), profileResponse.getFotoperfil(), imgvProfile);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.replace(R.id.flFragmentContainer, fragment, tag).commit();
        dlHomeContainer.closeDrawers();
    }

    public void navigationMenu(String tag) {

        if(DeviceManager.isOnline(this)){

            switch (tag) {
            case OPTION_HOME:
                fragmentManager.popBackStack(HomeMainFragment.TAG, 0);
                dlHomeContainer.closeDrawers();
                break;
            case OPTION_NOTICE:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COMUNICADOS).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    // Change activity management
                    // Intent intentNotice = new Intent(this, ComunicadosActivity.class);
                    Intent intentReleases = new Intent(this, ReleasesActivity.class);
                    startActivity(intentReleases);
                }
                break;
            case OPTION_PAYROLL_VOUCHER:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_PAYSHEET).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    replaceFragment(new PayrollVoucherMenuFragment(), PayrollVoucherMenuFragment.TAG);
                }
                break;
            case OPTION_BENEFITS:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_BENEFICIOS).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    replaceFragment(new BenefitsFragment(), BenefitsFragment.TAG);
                }
                break;
            case OPTION_SAVING_FUND:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_SAVINGS).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    replaceFragment(new LoanSavingFundFragment(), LoanSavingFundFragment.TAG);
                    RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_NUM_COLABORADOR),9);
                }
                break;
            case OPTION_VISIONARIES:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_VISIONARIOS).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                } else {
                    Intent intentVisionaries = new IntentBuilder(new Intent(this, VisionariesActivity.class))
                            .putSerializableExtra(VisionariesActivity.TYPE, VisionaryType.VISIONARIES)
                            .build();
                    startActivity(intentVisionaries);
                }
                break;
            case OPTION_COLLABORATOR_AT_HOME:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_STAYHOME).equals(YES))
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                else {
                    Intent intentVisionaries = new IntentBuilder(new Intent(this, VisionariesActivity.class))
                            .putSerializableExtra(VisionariesActivity.TYPE, VisionaryType.COLLABORATOR_AT_HOME)
                            .build();
                    startActivity(intentVisionaries);
                }
                break;
            case OPTION_LETTERS:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_CARTASCONFIG).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{

                    replaceFragment(new EmploymentLettersMenuFragment(), EmploymentLettersMenuFragment.TAG);
                }
                break;
            case OPTION_EXPENSES:

                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_TRAVEL_EXPENSES).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{

                        if(AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_GTE)){
                            replaceFragment(new TravelExpensesRolMenuFragment(), TravelExpensesRolMenuFragment.TAG);
                        }else if(AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_SUPLENTE)){
                            getRolType(EXPENSESTRAVEL);
                        }else{
                            replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                        }

                        RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_NUM_COLABORADOR),11);
                    }
                break;

            case OPTION_HOLIDAYS:

                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_HOLIDAYS).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{

                    if(AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_GTE)){
                        replaceFragment(new HolidaysRolMenuFragment(), HolidaysRolMenuFragment.TAG);
                    }else if(AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_SUPLENTE)){
                        getRolType(HOLIDAYS);
                    }else {
                        getRolType(HOLIDAYS);
                    }

                    RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_NUM_COLABORADOR),10);
                }

                break;

            case OPTION_NOTIFICATION_EXPENSES_AUTHORIZE:
                    replaceFragment(new TravelExpensesRolMenuFragment(), TravelExpensesRolMenuFragment.TAG);
                    NavigationUtil.openActivityToAuthorize(
                            this,
                            GastosViajeActivity.class,
                            BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_MANAGER
                    );

                  /*  if(currentHomeFragment != null){
                        ((HomeMainFragment)currentHomeFragment).hideProgress();
                    }*/

                    RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_NUM_COLABORADOR),10);
                    break;
            case OPTION_QR_CODE:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_QR).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else {
                    Intent intentQr = new IntentBuilder(new Intent(this, QrCodeActivity.class))
                            .putStringExtra("numEmp", profileResponse.getColaborador())
                            .putStringExtra("emailEmp", profileResponse.getCorreo())
                            .build();
                    startActivity(intentQr);
                }
                break;

            case OPTION_COVID_SURVEY:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COVID_SURVEY).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    getExternalUrl(COVID_SURVEY);
                }
                break;

            case OPTION_COLLAGE:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COLLAGE).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    getCollageURL();
                }


            case OPTION_POLL:

                break;
            }
        }else {
            showError(new ServicesError(getString(R.string.network_error)));
        }


        forceHideProgress();

    }

    private void getExternalUrl(int option){
        String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getExternalUrl( profileResponse.getColaborador(), option, token);
    }

    private void openCovidSurvey(String url){
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

    public void setToolbarTitle(String title) {

        titleToolbar.setText(title);
        // tbActionBar.setTitle(title);
    }

    public LoginResponse.Response getLoginResponse() {
        return loginResponse;
    }

    public ProfileResponse.Response getProfileResponse() {
        return profileResponse;
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
        if(requestLogout) {
            requestLogout = false;
        }
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(this);
        }else {
            if(requestLogout){
                requestLogout = false;
                /*Se implementa llamada a endpoint de cerrar sesión*/
                String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
                coppelServicesPresenter.requestLogOut( profileResponse.getColaborador(), profileResponse.getCorreo(),token);
            }
            dialogFragmentWarning.close();
        }

    }

    private void initFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Registrado";
                        if (!task.isSuccessful()) {
                            msg = "Error al registrar";
                        }
                    }
                });
    }

    private void getData() {
        InternalDatabase idb = new InternalDatabase(this);

        TableConfig tableConfig = new TableConfig(idb, false);
        Config config = new Config(1,AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.VISIONARIOS_URL));
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
        currentHomeFragment =  new HomeMainFragment();
        fragmentTransaction.add(R.id.flFragmentContainer, currentHomeFragment, HomeMainFragment.TAG).commit();
    }



    @Override
    public SurveyInboxView getSurveyIcon() {
        return surveyInboxView;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment myFragment = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer);
        if (myFragment != null && myFragment instanceof ColaboratorHolidaysFragment) {
            // add your code here
            ((ColaboratorHolidaysFragment)myFragment).onActivityResult(requestCode,resultCode,data
            );
        }

    }


    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.LOGOUT:
                LogoutResponse logoutResponse = (LogoutResponse) response.getResponse();
                hideProgress();
                AppUtilities.closeApp(this);
                break;

            case EXPENSESTRAVEL:

                if(response.getResponse() instanceof RolExpensesResponse) {

                    if (((RolExpensesResponse) response.getResponse()).getData().getResponse().getClv_estatus() == 1) {
                        replaceFragment(new TravelExpensesRolMenuFragment(), TravelExpensesRolMenuFragment.TAG);
                    }else {
                        replaceFragment(new MyRequestAndControlsFragment(), MyRequestAndControlsFragment.TAG);
                    }
                }

                break;

            case ServicesRequestType.HOLIDAYS:

                if(response.getResponse() instanceof HolidayRolCheckResponse) {

                    if (((HolidayRolCheckResponse) response.getResponse()).getData().getResponse().getGerente() == 1 ||
                            ((HolidayRolCheckResponse) response.getResponse()).getData().getResponse().getSuplente() == 1)  {
                        replaceFragment(new HolidaysRolMenuFragment(), HolidaysRolMenuFragment.TAG);
                    }else {

                   /*     NavigationUtil.openActivityWithStringParam(this, VacacionesActivity.class,
                                BUNDLE_OPTION_HOLIDAYS,BUNDLE_OPTION_HOLIDAYREQUESTS);*/
                        hideProgress();
                        replaceFragment(new ColaboratorHolidaysFragment(), ColaboratorHolidaysFragment.TAG);
                        //hideProgress();
                    }
                }

                break;


            case COLLAGE:

                if(response.getResponse() instanceof CollageResponse) {
                    CollageResponse collageResponse = (CollageResponse) response.getResponse();
                    String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER);
                    String url = String.format("%s%s", collageResponse.getData().getResponse().get(0).getClv_urlservicio(), token);
                    openCollage(url);
                }

                break;

            case COVID_SURVEY:
                if(response.getResponse() instanceof ExternalUrlResponse) {
                    ExternalUrlResponse externalUrlResponse = (ExternalUrlResponse) response.getResponse();
                    String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN_USER);
                    String url = String.format("%s%s", externalUrlResponse.getData().getResponse().get(0).getClv_urlservicio(), token);
                    openCovidSurvey(url);
                }
                break;

        }
    }

    private void openCollage(String urlString){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
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

    @Override
    public void showError(ServicesError coppelServicesError) {

        if(coppelServicesError.getMessage() != null ){
            switch (coppelServicesError.getType()) {

                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;

                default:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogFragmentWarning = new DialogFragmentWarning();
                            dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), coppelServicesError.getMessage(), getString(R.string.accept));
                            dialogFragmentWarning.setOnOptionClick(HomeActivity.this);
                            dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
                            hideProgress();
                        }
                    }, 300);
                    break;
            }

        }
    }

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void showProgress() {
        if(hideLoader){
            dialogFragmentLoader = new DialogFragmentLoader();
            dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
            hideLoader = true;
        }

    }

    @Override
    public void hideProgress() {

        if(dialogFragmentLoader != null && dialogFragmentLoader.isVisible()) {
            dialogFragmentLoader.dismissAllowingStateLoss();
            dialogFragmentLoader.close();
            hideLoader = false;
        }
    }

    public void forceHideProgress(){
        if(dialogFragmentLoader != null) {
            dialogFragmentLoader.dismissAllowingStateLoss();
            dialogFragmentLoader.close();
            hideLoader = false;
        }
    }

    private void getRolType(int type){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_TOKEN);

        switch (type) {
            case EXPENSESTRAVEL:
                ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.CONSULTA_PERMISO_ROL, 2,numEmployer);
                coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
                break;
            case HOLIDAYS:

                HolidayRequestData holidayRequestData = new HolidayRequestData(HolidaysType.CONSULTA_ROL,1,numEmployer);
                coppelServicesPresenter.getHolidays(holidayRequestData,token);
                break;
        }

    }


    @Override
    public boolean validateSurveyAccess() {

        if (AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_ENCUESTAS).equals(YES)) {
            showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer);
        if(f instanceof BenefitsFragment){
            ((BenefitsFragment)f).onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    private void getCollageURL(){
        /*Se implementa llamada a endpoint de cerrar sesión*/
        String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
        coppelServicesPresenter.getCollege( profileResponse.getColaborador(),19,token);
    }


    @Override
    public void showTitle(boolean show) {
        tbActionBar.setTitle(show ? titleActivity : "");
        changeIconToolbar(show ? R.drawable.ic_left_arrow_black : R.drawable.ic_close_black );
        if(!show){
            isOpenMenuToolbar = true;
        }
    }

    @Override
    public void changeIconToolbar(int icon) {
        tbActionBar.setNavigationIcon(icon);
    }

    @Override
    public void showEliminatedOption(boolean show, String name) {
        this.eliminateOption.setVisibility(show ? View.VISIBLE : View.GONE);
        this.eliminateOption.setText(name);

        this.surveyInboxView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showAuthorizeOption(boolean show) {

    }

    @Override
    public void setActionEliminatedOption(Command action) {
        this.eliminateOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.execute();
            }
        });
    }

    @Override
    public void setActionAuthorizeOption(Command action) {

    }
}
