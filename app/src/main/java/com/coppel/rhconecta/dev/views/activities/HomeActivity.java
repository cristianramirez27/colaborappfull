package com.coppel.rhconecta.dev.views.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.Enums.HolidaysType;
import com.coppel.rhconecta.dev.business.interfaces.IScheduleOptions;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.CollageResponse;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidayRolCheckResponse;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.LogoutResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.business.models.RolExpensesResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.adapters.HomeSlideMenuArrayAdapter;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.fragments.EmploymentLettersMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.HomeMainFragment;
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
import com.coppel.rhconecta.dev.visionarios.comunicados.views.ComunicadosActivity;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableComunicados;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.databases.TableUsuario;
import com.coppel.rhconecta.dev.visionarios.databases.TableVideos;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;
import com.coppel.rhconecta.dev.visionarios.utils.Config;
import com.coppel.rhconecta.dev.visionarios.videos.views.VideosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COLLAGE;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.COLLAGE;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.EXPENSESTRAVEL;
import static com.coppel.rhconecta.dev.business.utils.ServicesRequestType.HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_GOTO_SECTION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYREQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_BENEFICIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_CARTASCONFIG;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_COMUNICADOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_PAYSHEET;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_SAVINGS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_VISIONARIOS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.MESSAGE_FOR_BLOCK;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_COLLAGE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOME;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_LETTERS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTICE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_PAYROLL_VOUCHER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_POLL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_VISIONARIES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

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
    private Encuesta ultimaEncuesta;
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

    private DialogFragmentLoader dialogFragmentLoader;

    @BindView(R.id.titleToolbar)
    TextView titleToolbar;

    @BindView(R.id.eliminateOption)
    TextView eliminateOption;
    private String titleActivity;
    private boolean isOpenMenuToolbar;

    private boolean EXPIRED_SESSION;
    private String goTosection="";
    private CoppelServicesPresenter coppelServicesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifications = new int[]{0,0};
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        getWindow().setBackgroundDrawable(null);
        initFirebase();

        coppelServicesPresenter = new CoppelServicesPresenter(this, this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.getString(AppConstants.BUNDLE_LOGIN_RESPONSE) != null
                && bundle.getString(AppConstants.BUNLDE_PROFILE_RESPONSE) != null) {
            realm = Realm.getDefaultInstance();
            loginResponse = new Gson().fromJson(bundle.getString(AppConstants.BUNDLE_LOGIN_RESPONSE), LoginResponse.class).getData().getResponse();
            profileResponse = new Gson().fromJson(bundle.getString(AppConstants.BUNLDE_PROFILE_RESPONSE), ProfileResponse.class).getData().getResponse()[0];

            initNavigationComponents();
            initMenu();
            getData();
            ctlLogout.setOnClickListener(this);

            if (bundle.containsKey(AppConstants.BUNDLE_GOTO_SECTION)) {
                goTosection = bundle.getString(AppConstants.BUNDLE_GOTO_SECTION);
                navigationMenu(goTosection);
            }


        } else {
            finish();
        }
    }

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

    @Override
    public void onBackPressed() {
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
        switch (tag) {
            case OPTION_HOME:
                fragmentManager.popBackStack(HomeMainFragment.TAG, 0);
                dlHomeContainer.closeDrawers();
                break;
            case OPTION_NOTICE:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COMUNICADOS).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    Intent intentNotice = new Intent(this, ComunicadosActivity.class);
                    startActivity(intentNotice);
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
                }else{
                    Intent intentVisionaries = new Intent(this, VideosActivity.class);
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

                if(AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_GTE)){
                    replaceFragment(new HolidaysRolMenuFragment(), HolidaysRolMenuFragment.TAG);
                }else if(AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_SUPLENTE)){
                    getRolType(HOLIDAYS);
                }else {
                    getRolType(HOLIDAYS);
                }

                RealmHelper.deleteNotifications(AppUtilities.getStringFromSharedPreferences(this,SHARED_PREFERENCES_NUM_COLABORADOR),10);
                break;
            case OPTION_COLLAGE:
                if(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), BLOCK_COLLAGE).equals(YES)){
                    showWarningDialog(AppUtilities.getStringFromSharedPreferences(getApplicationContext(), MESSAGE_FOR_BLOCK));
                }else{
                    getCollageURL();
                }

                break;

            case OPTION_POLL:

                break;
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
                        Log.d(TAG, msg);
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

        Usuario usuarioLocalExistente = tableUsuario.select("1");

        if(usuarioLocalExistente != null){
            if(!profileResponse.getColaborador().equals(usuarioLocalExistente.getNumeroempleado())){
                TableComunicados tableComunicados = new TableComunicados(idb,true);
                TableVideos tableVideos = new TableVideos(idb,true);
            }
        }else{
            TableComunicados tableComunicados = new TableComunicados(idb,true);
            TableVideos tableVideos = new TableVideos(idb,true);
        }


        tableUsuario.insertIfNotExist(usuario);
        tableUsuario.closeDB();



        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(HomeMainFragment.TAG);
        fragmentTransaction.add(R.id.flFragmentContainer, new HomeMainFragment(), HomeMainFragment.TAG).commit();
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

                        replaceFragment(new ColaboratorHolidaysFragment(), HolidaysRolMenuFragment.TAG);
                    }
                }

                break;


            case COLLAGE:

                if(response.getResponse() instanceof CollageResponse) {
                    CollageResponse collageResponse = (CollageResponse) response.getResponse();
                    String token = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_TOKEN);
                    String url = String.format("%s%s", collageResponse.getData().getResponse().get(0).getClv_urlservicio(), token);
                    openCollage(url);
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
                    }, 500);
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
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {

        if(dialogFragmentLoader != null)
            dialogFragmentLoader.close();
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