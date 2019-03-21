package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.ElementsDepositSimpleTab;
import com.coppel.rhconecta.dev.business.Enums.ElementsDepositTab;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ConsultaAbonoResponse;
import com.coppel.rhconecta.dev.business.models.DatosAbonoOpcion;
import com.coppel.rhconecta.dev.business.models.ViewPagerData;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.customviews.GenericPagerAdapter;
import com.coppel.rhconecta.dev.views.customviews.GenericTabLayout;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_ABONO;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_METODOS_PAGO;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_ABONO;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.NO_REFUSE_REMOVE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class AbonoFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener  {

    public static final String TAG = AbonoFragment.class.getSimpleName();
    private FondoAhorroActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private long mLastClickTime = 0;

    private DialogFragmentWarning dialogFragmentWarning;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean WARNING_PERMISSIONS;
    private boolean EXPIRED_SESSION;
    private List<Fragment> fragmentList;
    private GenericPagerAdapter mainViewPagerAdapter;

    @BindView(R.id.tabs)
    protected GenericTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.btnDeposit)
    Button btnDeposit;


    @BindView(R.id.txvLoanValueCurrentAcount)
    TextView txvLoanValueCurrentAcount;
    @BindView(R.id.txvLoanValueAditional)
    TextView txvLoanValueAditional;
    @BindView(R.id.txvBuyMargin7)
    TextView txvLoanTitleEmployer;

    @BindView(R.id.txvLoanValueEmployer)
    TextView txvLoanValueEmployer;

    @BindView(R.id.txvLoanValueMargin)
    TextView txvLoanValueMargin;
    @BindView(R.id.txvLoanValueEnterprise)
    TextView txvLoanValueEnterprise;

    private boolean hasEmployerOption = false;


    private DialogFragmentGetDocument dialogFragmentGetDocument;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static AbonoFragment getInstance(){
        AbonoFragment fragment = new AbonoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abono, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (FondoAhorroActivity) getActivity();
        parent.setToolbarTitle("Abonar");
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txvLoanValueCurrentAcount.setText(TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getCuentaCorriente()))));
        txvLoanValueAditional.setText(TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional()))));
        txvLoanValueMargin.setText(TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito()))));
        txvLoanValueEnterprise.setText(TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getFondoEmpresa()))));
        txvLoanValueEmployer.setText(TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getFondoTrabajador()))));

        //hasEmployerOption = parent.getLoanSavingFundResponse().getData().getResponse().getFondoTrabajador() < parent.getLoanSavingFundResponse().getData().getResponse().getFondoEmpresa();
        txvLoanTitleEmployer.setVisibility(hasEmployerOption ? View.VISIBLE :View.INVISIBLE);
        txvLoanValueEmployer.setVisibility(hasEmployerOption ? View.VISIBLE :View.INVISIBLE);

        btnDeposit.setOnClickListener(this);

        /*Consulta */
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                CONSULTA_ABONO,4,numEmployer);
        coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);

    }


    private void configUI(ConsultaAbonoResponse response){
        //scrollView.setFillViewport (true);
        fragmentList = new ArrayList<>();
        ConsultaAbonoResponse.Response data = response.getData().getResponse();

        fragmentList.add(AbonoTipoFragment.getInstance(1,new DatosAbonoOpcion(data.getImp_cuentacorriente(),data.getDes_proceso(),data.getDes_cambiar())));
        fragmentList.add(AbonoTipoFragment.getInstance(2,new DatosAbonoOpcion(data.getImp_ahorroadicional(),data.getDes_proceso(),data.getDes_cambiar())));
        if(hasEmployerOption) fragmentList.add(AbonoTipoFragment.getInstance(3,new DatosAbonoOpcion(data.getImp_fondotrabajador(),data.getDes_proceso(),data.getDes_cambiar())));

        ViewPagerData viewPagerData =  new ViewPagerData<>(fragmentList,hasEmployerOption ? ElementsDepositTab.values() : ElementsDepositSimpleTab.values());
        loadViewPager(viewPagerData);
    }

    private void loadViewPager( ViewPagerData viewPagerData){
        mainViewPagerAdapter = new GenericPagerAdapter<>(getActivity(), getChildFragmentManager(), fragmentList, viewPagerData.getTabData());
        viewpager.setAdapter(mainViewPagerAdapter);
        viewpager.setOffscreenPageLimit(viewPagerData.getTabData().length - 1);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.getSelectedTabPosition();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Log.e("TabActivity", "indicator position " + tabLayout.getSelectedTabPosition());
        tabLayout.setSelectedTabIndicatorHeight(6);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryCoppelAzul));
        tabLayout.setVisibility(VISIBLE);


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                ((AbonoTipoFragment)fragmentList.get(i)).initFragment();


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                parent.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeposit:

                break;
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.WITHDRAWSAVING:

                if(response.getResponse() instanceof ConsultaAbonoResponse){
                    //Se muestra el mensaje de Des_abonoProceso si tiene valor
                    if(((ConsultaAbonoResponse) response.getResponse()).getData().getResponse().getDes_abonoProceso() != null
                            && !(((ConsultaAbonoResponse) response.getResponse()).getData().getResponse().getDes_abonoProceso().isEmpty())){
                        showAlertDialog(((ConsultaAbonoResponse) response.getResponse()).getData().getResponse().getDes_abonoProceso());
                    }

                    //Si se obtiene un mensaje en la propiedad Des_mensaje, se despliega.
                    if(((ConsultaAbonoResponse) response.getResponse()).getData().getResponse().getDes_mensaje() != null
                    && !(((ConsultaAbonoResponse) response.getResponse()).getData().getResponse().getDes_mensaje().isEmpty())){
                        showWarningDialog(((ConsultaAbonoResponse) response.getResponse()).getData().getResponse().getDes_mensaje());
                    }

                    configUI((ConsultaAbonoResponse)response.getResponse());
                }

                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.LETTERSCONFIG:

                break;
            case ServicesRequestType.INVALID_TOKEN:
                EXPIRED_SESSION = true;
                showWarningDialog(getString(R.string.expired_session));
                break;
        }


        hideProgress();
    }


    private void showAlertDialog(String msg) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setType(MSG_ABONO, parent);
        dialogFragmentGetDocument.setContentText(msg);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    @Override
    public void showProgress() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
       if(dialogFragmentLoader != null) dialogFragmentLoader.close();
    }


    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        } else if (WARNING_PERMISSIONS) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[0]) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(parent, permissions[1])) {
                AppUtilities.openAppSettings(parent);
            }
        }
        WARNING_PERMISSIONS = false;
        dialogFragmentWarning.close();

    }

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {
        dialogFragmentGetDocument.close();
    }
}