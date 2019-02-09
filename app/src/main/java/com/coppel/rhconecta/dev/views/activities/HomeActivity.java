package com.coppel.rhconecta.dev.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ISurveyNotification;
import com.coppel.rhconecta.dev.business.models.LoginResponse;
import com.coppel.rhconecta.dev.business.models.ProfileResponse;
import com.coppel.rhconecta.dev.resources.db.models.HomeMenuItem;
import com.coppel.rhconecta.dev.views.adapters.HomeSlideMenuArrayAdapter;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.fragments.EmploymentLettersMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.HomeMainFragment;
import com.coppel.rhconecta.dev.views.fragments.LoanSavingFundFragment;
import com.coppel.rhconecta.dev.views.fragments.PayrollVoucherMenuFragment;
import com.coppel.rhconecta.dev.views.fragments.ProfileFragment;
import com.coppel.rhconecta.dev.views.fragments.benefits.BenefitsFragment;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.MenuUtilities;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.comunicados.views.ComunicadosActivity;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableComunicados;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.databases.TableUsuario;
import com.coppel.rhconecta.dev.visionarios.databases.TableVideos;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.views.EncuestaActivity;
import com.coppel.rhconecta.dev.visionarios.firebase.MyFirebaseReferences;
import com.coppel.rhconecta.dev.visionarios.inicio.interfaces.Inicio;
import com.coppel.rhconecta.dev.visionarios.inicio.objects.Usuario;
import com.coppel.rhconecta.dev.visionarios.inicio.presenters.InicioPresenter;
import com.coppel.rhconecta.dev.visionarios.utils.Config;
import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.views.VideosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOME;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_LETTERS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTICE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_PAYROLL_VOUCHER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_POLL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_VISIONARIES;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener, ProfileFragment.OnPictureChangedListener,
        DialogFragmentWarning.OnOptionClick,ISurveyNotification {

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

    @BindView(R.id.titleToolbar)
    TextView titleToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifications = new int[]{0,0};
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        getWindow().setBackgroundDrawable(null);
        initFirebase();

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
                Intent intentNotice = new Intent(this, ComunicadosActivity.class);
                startActivity(intentNotice);
                break;
            case OPTION_PAYROLL_VOUCHER:
                replaceFragment(new PayrollVoucherMenuFragment(), PayrollVoucherMenuFragment.TAG);
                break;
            case OPTION_BENEFITS:
                replaceFragment(new BenefitsFragment(), BenefitsFragment.TAG);
                break;
            case OPTION_SAVING_FUND:
                replaceFragment(new LoanSavingFundFragment(), LoanSavingFundFragment.TAG);
                break;
            case OPTION_VISIONARIES:
                Intent intentVisionaries = new Intent(this, VideosActivity.class);
                startActivity(intentVisionaries);
                break;

            case OPTION_LETTERS:
                replaceFragment(new EmploymentLettersMenuFragment(), EmploymentLettersMenuFragment.TAG);
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
    }

    @Override
    public void onRightOptionClick() {
        AppUtilities.closeApp(this);
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
        Config config = new Config(1,ConstantesGlobales.URL_API);
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
}
