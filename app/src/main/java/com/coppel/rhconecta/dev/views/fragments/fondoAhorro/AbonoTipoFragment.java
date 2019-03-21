package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.BenefitsStatesResponse;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.ConsultaMetodosPagoResponse;
import com.coppel.rhconecta.dev.business.models.DatosAbonoOpcion;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentSelectPayment;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentSelectState;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_METODOS_PAGO;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_RETIRO;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_CLV_ABONAR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_DATOS_OPT_ABONAR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class AbonoTipoFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentSelectPayment.OnButonOptionClick,ICalculatetotal {

    public static final String TAG = AbonoTipoFragment.class.getSimpleName();
    private FondoAhorroActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private long mLastClickTime = 0;

    private DialogFragmentWarning dialogFragmentWarning;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean WARNING_PERMISSIONS;

    private boolean EXPIRED_SESSION;
    private List<Fragment> fragmentList;

    @BindView(R.id.edtAbonoActual)
    EditTextMoney edtAbonoActual;
    @BindView(R.id.edtAbonoCambiar)
    EditTextMoney edtAbonoCambiar;
    @BindView(R.id.viewPaymentWay)
    View viewPaymentWay;
    @BindView(R.id.payment)
    EditText payment;

    private DialogFragmentSelectPayment dialogFragmentSelectPayment;

    private int indexPaymentSelected = 0;
    private DatosAbonoOpcion datosAbonoOpcion;

    private boolean isWatchingKeyboard;



    private ConsultaMetodosPagoResponse consultaMetodosPagoResponse;


    private int clv_Abonar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static AbonoTipoFragment getInstance(int clv_Abonar,DatosAbonoOpcion datosAbonoOpcion){
        AbonoTipoFragment fragment = new AbonoTipoFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_CLV_ABONAR,clv_Abonar);
        args.putSerializable(BUNDLE_DATOS_OPT_ABONAR,datosAbonoOpcion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposit_option, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.clv_Abonar = getArguments().getInt(BUNDLE_CLV_ABONAR);
        this.datosAbonoOpcion = (DatosAbonoOpcion) getArguments().getSerializable(BUNDLE_DATOS_OPT_ABONAR);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parent = (FondoAhorroActivity) getActivity();

        edtAbonoActual.setTitleGravity(Gravity.LEFT);
        edtAbonoCambiar.setTitleGravity(Gravity.LEFT);
        edtAbonoActual.setPaddinRigthTitle();
        edtAbonoCambiar.setPaddinRigthTitle();

        if(this.clv_Abonar == 1)
            initFragment();

        //ViewPagerData viewPagerData =  new ViewPagerData<>(fragmentList, ElementsMainTab.values());

    }

    public void initFragment(){
        edtAbonoActual.setInformativeMode("Quiero abonar","");
        edtAbonoCambiar.setInformativeMode("Cambiar abono","");
        edtAbonoActual.setEnableQuantity(true);
        edtAbonoCambiar.setEnableQuantity(true);
        edtAbonoCambiar.setHint("Ingresa una cantidad");

        viewPaymentWay.setOnClickListener(this);


        if(datosAbonoOpcion.getImporte() > 0){
            edtAbonoActual.setVisibility(View.VISIBLE);
            edtAbonoActual.setInformativeMode(
                    datosAbonoOpcion.getDes_proceso(), "");
            edtAbonoActual.setInformativeQuantity(String.format("$%d",datosAbonoOpcion.getImporte()));
            edtAbonoCambiar.setInformativeMode(datosAbonoOpcion.getDes_cambiar(),"");
            edtAbonoCambiar.setHint("Ingresa una cantidad");
            edtAbonoCambiar.setEnableQuantity(true);
        }else {

            edtAbonoCambiar.setInformativeMode(getString(R.string.want_deposit), "");
            edtAbonoCambiar.setHint("Ingresa una cantidad");
            edtAbonoCambiar.setEnableQuantity(true);
            edtAbonoActual.setVisibility(View.GONE);
            edtAbonoActual.setTextWatcherMoney(this);
            edtAbonoCambiar.setTextWatcherMoney(this);
        }

        /*Consultamos formas de pago*/
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                CONSULTA_METODOS_PAGO,5,numEmployer);

        withDrawSavingRequestData.setClv_abonar(clv_Abonar);
        coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);
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
            case R.id.btnAdd:

                break;

            case R.id.viewPaymentWay:
                showPaymentWay();
                break;
        }
    }

    private void showPaymentWay(){
        if(consultaMetodosPagoResponse != null){
            showSelectPayment(consultaMetodosPagoResponse.getData().getResponse());
        }

        Toast.makeText(getActivity(), "Open payment", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.WITHDRAWSAVING:

                if(response.getResponse() instanceof ConsultaMetodosPagoResponse){
                    consultaMetodosPagoResponse = (ConsultaMetodosPagoResponse) response.getResponse();
                    if(consultaMetodosPagoResponse.getData().getResponse().size() > 0) {
                        payment.setText(consultaMetodosPagoResponse.getData().getResponse().get(indexPaymentSelected).getNom_retiro());
                    }

                    viewPaymentWay.setEnabled(consultaMetodosPagoResponse.getData().getResponse().size() > 1 ? true : false);
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


    private void showSelectPayment( List<ConsultaMetodosPagoResponse.PaymentWay> paymentWays){
        CatalogueData statesData = new CatalogueData();
        statesData.setData(paymentWays);
        dialogFragmentSelectPayment = DialogFragmentSelectPayment.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentSelectPayment.setOnButtonClickListener(this);
        dialogFragmentSelectPayment.show(parent.getSupportFragmentManager(), DialogFragmentSelectState.TAG);
    }

    @Override
    public void onLeftOptionPaymentClick() {
        dialogFragmentSelectPayment.close();
    }

    @Override
    public void onRightOptionPaymentClick(ConsultaMetodosPagoResponse.PaymentWay data) {

        payment.setText(data.getNom_retiro());
        dialogFragmentSelectPayment.close();
    }

    @Override
    public void calculate() {

    }
}