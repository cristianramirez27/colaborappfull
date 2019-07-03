package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.ElementsDepositSimpleTab;
import com.coppel.rhconecta.dev.business.Enums.ElementsDepositTab;
import com.coppel.rhconecta.dev.business.interfaces.IButtonControl;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ConsultaAbonoResponse;
import com.coppel.rhconecta.dev.business.models.DatosAbonoOpcion;
import com.coppel.rhconecta.dev.business.models.GuardarAbonoResponse;
import com.coppel.rhconecta.dev.business.models.ViewPagerData;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.customviews.GenericPagerAdapter;
import com.coppel.rhconecta.dev.views.customviews.GenericTabLayout;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAbono;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentFondoAhorro;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_ABONO;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.GUARDAR_ABONO;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_ABONO;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class AbonoFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener ,IButtonControl {

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

    @BindView(R.id.ctlConnectionError)
    ConstraintLayout ctlConnectionError;
    @BindView(R.id.layoutMain)
    RelativeLayout layoutMain;
    @BindView(R.id.txvLoanValueCurrentAcount)
    TextView txvLoanValueCurrentAcount;
    @BindView(R.id.txvLoanValueAditional)
    TextView txvLoanValueAditional;
    @BindView(R.id.txvBuyMargin7)
    TextView txvLoanTitleEmployer;
    @BindView(R.id.txvBuyMargin4)
    TextView txvLoanTitleEnterprise;


    @BindView(R.id.txvLoanValueEmployer)
    TextView txvLoanValueEmployer;

    @BindView(R.id.txvLoanValueMargin)
    TextView txvLoanValueMargin;
    @BindView(R.id.txvLoanValueEnterprise)
    TextView txvLoanValueEnterprise;

    private boolean hasEmployerOption = false;

    private DialogFragmentAbono dialogFragmentAbono;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentFondoAhorro dialogFragmentFondoAhorro;

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

        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(!isOpen)
                            ((AbonoTipoFragment)mainViewPagerAdapter.getItem(viewpager.getCurrentItem())).calculate();
                    }
                });


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEnableButton(false);

        String valueCurrentAcount = TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getCuentaCorriente())));
        String valueAditional = TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional())));
        String valueMargin = TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito())));
        String valueEnterprise = TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getFondoEmpresa())));
        String valueEmployer = TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getFondoTrabajador())));


        if(valueCurrentAcount.length() >= 12)
            txvLoanValueCurrentAcount.setTextSize(11);
        txvLoanValueCurrentAcount.setText(valueCurrentAcount);
        if(valueAditional.length() >= 12)
            txvLoanValueAditional.setTextSize(11);
        txvLoanValueAditional.setText(valueAditional);
        if(valueMargin.length() >= 12)
            txvLoanValueMargin.setTextSize(11);
        txvLoanValueMargin.setText(valueMargin);
        if(valueEnterprise.length() >= 12)
            txvLoanValueEnterprise.setTextSize(11);
        txvLoanValueEnterprise.setText(valueEnterprise);
        if(valueEmployer.length() >= 12)
            txvLoanValueEmployer.setTextSize(11);
        txvLoanValueEmployer.setText(valueEmployer);

       hasEmployerOption = Double.parseDouble(parent.getLoanSavingFundResponse().getData().getResponse().getFondoTrabajador())
                < Double.parseDouble(parent.getLoanSavingFundResponse().getData().getResponse().getFondoEmpresa());

        txvLoanTitleEmployer.setVisibility(hasEmployerOption ? View.VISIBLE :View.INVISIBLE);
        txvLoanValueEmployer.setVisibility(hasEmployerOption ? View.VISIBLE :View.INVISIBLE);

        txvLoanTitleEnterprise.setVisibility(hasEmployerOption ? View.VISIBLE :View.INVISIBLE);
        txvLoanValueEnterprise.setVisibility(hasEmployerOption ? View.VISIBLE :View.INVISIBLE);


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

        int impCorriente;
        int impAhorro;
        int impFondoTrabajador;

        ConsultaAbonoResponse.Response data = response.getData().getResponse();

        impCorriente = data.getImp_cuentacorriente();
        impAhorro = data.getImp_ahorroadicional();
        impFondoTrabajador = data.getImp_fondotrabajador();

        AbonoTipoFragment AbonarCorrienteFragment  = AbonoTipoFragment.getInstance(
                1,
                new DatosAbonoOpcion(impCorriente,data.getDes_proceso(),data.getDes_cambiar(),data.getClv_retirocuentacorriente()));
        AbonarCorrienteFragment.setIButtonControl(this);

        AbonoTipoFragment AbonarAhorroFragment  = AbonoTipoFragment.getInstance(2,
                new DatosAbonoOpcion(impAhorro,data.getDes_proceso(),data.getDes_cambiar(),data.getClv_retiroahorroadicional()));
        AbonarAhorroFragment.setIButtonControl(this);


        fragmentList.add(AbonarCorrienteFragment);
        fragmentList.add(AbonarAhorroFragment);
        if(hasEmployerOption){
            AbonoTipoFragment AbonarTrabajadorFragment  = AbonoTipoFragment.getInstance(3,
                    new DatosAbonoOpcion(impFondoTrabajador,data.getDes_proceso(),data.getDes_cambiar(),data.getClv_retirofondotrabajador()));
            AbonarTrabajadorFragment.setIButtonControl(this);
            fragmentList.add(AbonarTrabajadorFragment);
        }

        int tabSelected = 0;

        if(impCorriente > 0)
            tabSelected = 0;
        if(impAhorro > 0)
            tabSelected = 1;
        if(impFondoTrabajador > 1 && hasEmployerOption)
            tabSelected = 2;


        ViewPagerData viewPagerData =  new ViewPagerData<>(fragmentList,hasEmployerOption ? ElementsDepositTab.values() : ElementsDepositSimpleTab.values());
        loadViewPager(viewPagerData,tabSelected);
    }

    private void loadViewPager( ViewPagerData viewPagerData,int tabSelected){
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
                ((AbonoTipoFragment)fragmentList.get(i)).loadPayments();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //Seteamos el tab que tiene datos
        viewpager.setCurrentItem(tabSelected);

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

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();

                deposit();
                break;
        }
    }

    private void deposit(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                GUARDAR_ABONO,6,numEmployer);

        AbonoTipoFragment fragmentCurrent = (AbonoTipoFragment) mainViewPagerAdapter.getItem(viewpager.getCurrentItem());

        if(fragmentCurrent.getClv_Abonar() == 1){
            withDrawSavingRequestData.setImp_cuentacorriente((int)fragmentCurrent.getAmount());
            withDrawSavingRequestData.setIdu_traspaso(2);//2- Para cuenta corriente
        }else if(fragmentCurrent.getClv_Abonar() == 2){
            withDrawSavingRequestData.setImp_ahorroadicional((int)fragmentCurrent.getAmount());
            withDrawSavingRequestData.setIdu_traspaso(1);//1- Para ahorro adicional
        }else if(fragmentCurrent.getClv_Abonar() == 3){
            withDrawSavingRequestData.setImp_fondoempleado((int)fragmentCurrent.getAmount());
            withDrawSavingRequestData.setIdu_traspaso(3);//1- Para ahorro adicional
        }

        withDrawSavingRequestData.setClv_retiro(fragmentCurrent.getPaymentSelected().getClv_retiro());

        //Validamos clv_retiro 1 y 2
        if(fragmentCurrent.getPaymentSelected().getClv_retiro() == 1 || fragmentCurrent.getPaymentSelected().getClv_retiro() == 2){

            double importe = fragmentCurrent.getAmount();
            if(fragmentCurrent.getPaymentSelected().getImp_disponible() < importe){
                showAlertDialog("No cuenta con saldo disponible");
                return;
            }


            showAlertDialogPayment(fragmentCurrent.getPaymentSelected().getNom_retiro(),
                    TextUtilities.getNumberInCurrencyFormaNoDecimal(fragmentCurrent.getAmount()),
                    new DialogFragmentAbono.OnOptionClick() {
                        @Override
                        public void onAccept() {
                            dialogFragmentAbono.close();
                            coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);
                        }

                        @Override
                        public void onCancel() {
                                dialogFragmentAbono.close();
                        }
                    });
        }
    }

    @Override
    public void showResponse(ServicesResponse response) {

        ctlConnectionError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);

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
                }else if(response.getResponse() instanceof GuardarAbonoResponse){

                    if (((GuardarAbonoResponse) response.getResponse()).getData().getResponse().getClv_folio().isEmpty() ||
                            ((GuardarAbonoResponse) response.getResponse()).getData().getResponse().getClv_folio().equals("0")) {
                        showAlertDialog( ((GuardarAbonoResponse) response.getResponse()).getData().getResponse().getDes_mensaje());
                    }else {
                        showAlertDialogSuccess( (GuardarAbonoResponse) response.getResponse());
                    }

                }

                break;
        }
    }



    private void showAlertDialogSuccess(GuardarAbonoResponse response) {
        dialogFragmentFondoAhorro = new DialogFragmentFondoAhorro();
        dialogFragmentFondoAhorro.initView(response.getData().getResponse().getDes_mensaje(),
                response.getData().getResponse().getClv_folio(),
                response.getData().getResponse().getFec_captura(),
                response.getData().getResponse().getHrs_captura());
        dialogFragmentFondoAhorro.setOnOptionClick(new DialogFragmentFondoAhorro.OnOptionClick() {
            @Override
            public void onAccept() {
                dialogFragmentFondoAhorro.close();
                getActivity().finish();
            }
        });
        dialogFragmentFondoAhorro.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.WITHDRAWSAVING:
                ctlConnectionError.setVisibility(View.VISIBLE);
                layoutMain.setVisibility(View.GONE);
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


    private void showAlertDialogPayment(String title,String amount, DialogFragmentAbono.OnOptionClick optionClick) {
        dialogFragmentAbono = new DialogFragmentAbono();
        dialogFragmentAbono.setTxvTitle(title);
        dialogFragmentAbono.setTxtAmount(amount);
        dialogFragmentAbono.setOnOptionClick(optionClick);
        dialogFragmentAbono.setVisibleCancelButton(VISIBLE);
        dialogFragmentAbono.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }


    private void setEnableButton(boolean isEnable){
        btnDeposit.setEnabled(isEnable);
        btnDeposit.setBackgroundResource(isEnable ? R.drawable.background_blue_rounded : R.drawable.background_disable_rounded);
    }



    @Override
    public void enableButton(boolean isEnable) {
        setEnableButton(isEnable);
    }


}