package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IButtonControl;
import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.BenefitsStatesResponse;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.ConsultaMetodosPagoResponse;
import com.coppel.rhconecta.dev.business.models.DatosAbonoOpcion;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
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
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
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
    @BindView(R.id.edtAbonoProceso)
    EditTextMoney edtAbonoProceso;
    @BindView(R.id.viewPaymentWay)
    View viewPaymentWay;
    @BindView(R.id.payment)
    EditText payment;
    @BindView(R.id.totalImporte)
    TextView totalImporte;
    @BindView(R.id.arrowDown)
    ImageView arrowDown;





    private DialogFragmentSelectPayment dialogFragmentSelectPayment;

    private int indexPaymentSelected = 0;
    private DatosAbonoOpcion datosAbonoOpcion;

    private boolean isWatchingKeyboard;

private IButtonControl IButtonControl;

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

        totalImporte.setText(String.format("%s%s",getString(R.string.totalRemove),TextUtilities.getNumberInCurrencyFormaNoDecimal(0)));

      /*  KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(!isOpen)
                            calculate();
                    }
                });*/


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

         initFragment();

        //ViewPagerData viewPagerData =  new ViewPagerData<>(fragmentList, ElementsMainTab.values());

    }

    public void initFragment(){
        IButtonControl.enableButton(false);
        edtAbonoActual.setTitleGravity(Gravity.LEFT);
        edtAbonoProceso.setTitleGravity(Gravity.LEFT);
        edtAbonoActual.setPaddinRigthTitle();
        edtAbonoProceso.setPaddinRigthTitle();
        edtAbonoActual.setInformativeMode("Quiero abonar","");
       // edtAbonoCambiar.setInformativeMode("Cambiar abono","");
        edtAbonoActual.setEnableQuantity(true);
        edtAbonoProceso.setEnableQuantity(true);
        //edtAbonoCambiar.setHint("Ingresa una cantidad");

        viewPaymentWay.setOnClickListener(this);
        setFocusChangeListener(edtAbonoActual);


      initValues();


        payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = payment.getText().toString();

                if(!(str.toString().compareToIgnoreCase(getString(R.string.paymentway_select)) == 0) && edtAbonoActual.getQuantity().length() > 0){

                    IButtonControl.enableButton(true);

                }else {
                    IButtonControl.enableButton(false);
                }
            }
        });

        if(this.clv_Abonar == 1)
            loadPayments();
    }


    private void initValues(){
        if(datosAbonoOpcion.getImporte() > 0){
            edtAbonoProceso.setVisibility(VISIBLE);
            edtAbonoProceso.setInformativeMode(
                    datosAbonoOpcion.getDes_proceso(), "");
            edtAbonoProceso.setInformativeQuantity(String.format("$%d",datosAbonoOpcion.getImporte()));
            edtAbonoActual.setInformativeMode(datosAbonoOpcion.getDes_cambiar(),"");
            edtAbonoActual.setHint("Ingresa otra cantidad");
            edtAbonoActual.setEnableQuantity(true);
        }else {

            edtAbonoActual.setInformativeMode(getString(R.string.want_deposit), "");
            edtAbonoActual.setHint("Ingresa una cantidad");
            edtAbonoActual.setEnableQuantity(true);
            edtAbonoProceso.setVisibility(View.GONE);
            edtAbonoActual.setTextWatcherMoney();
        }
    }
    private void setFocusChangeListener(EditTextMoney editTextMoney){

        editTextMoney.getEdtQuantity().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(count > 0 && !(payment.getText().toString().compareToIgnoreCase(getString(R.string.paymentway_select))==0) )
                    IButtonControl.enableButton(true  );
                else
                    IButtonControl.enableButton(false  );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editTextMoney.getEdtQuantity().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    editTextMoney.setTextWatcherMoney();
                    DeviceManager.showKeyBoard(getActivity());
                }

            }
        });
    }

    public void setIButtonControl(IButtonControl IButtonControl) {
        this.IButtonControl = IButtonControl;
    }

    public void loadPayments(){
        initValues();
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
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.WITHDRAWSAVING:

                if(response.getResponse() instanceof ConsultaMetodosPagoResponse){
                    consultaMetodosPagoResponse = (ConsultaMetodosPagoResponse) response.getResponse();
                    if(consultaMetodosPagoResponse.getData().getResponse().size() > 1) {

                        if(datosAbonoOpcion.getClv_retiro() > 0 ){

                            for(int i = 0 ; i < consultaMetodosPagoResponse.getData().getResponse().size() ; i++){
                                ConsultaMetodosPagoResponse.PaymentWay paymentWay = consultaMetodosPagoResponse.getData().getResponse().get(i);
                                if(paymentWay.getClv_retiro() == datosAbonoOpcion.getClv_retiro()){
                                        indexPaymentSelected = i;
                                        payment.setText(paymentWay.getNom_retiro());
                                }
                            }

                        }else {
                                payment.setText(getString(R.string.paymentway_select));
                        }

                    }else {
                        payment.setText(consultaMetodosPagoResponse.getData().getResponse().get(indexPaymentSelected).getNom_retiro());
                    }

                    viewPaymentWay.setEnabled(consultaMetodosPagoResponse.getData().getResponse().size() > 1 ? true : false);
                    arrowDown.setVisibility(consultaMetodosPagoResponse.getData().getResponse().size() > 1  ? VISIBLE : View.INVISIBLE);
                }
                break;
        }
    }


    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.WITHDRAWSAVING:

                showWarningDialog(coppelServicesError.getMessage());

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
    public void onRightOptionPaymentClick(ConsultaMetodosPagoResponse.PaymentWay data, int position) {

        if(data != null){
            indexPaymentSelected = position;
            payment.setText(data.getNom_retiro());
        }

        dialogFragmentSelectPayment.close();
    }

    @Override
    public void calculate() {
        String content = edtAbonoActual.getQuantity();
        Double importe = !content.isEmpty() ? Double.parseDouble(content) : 0.0;
        totalImporte.setText(String.format("%s%s",getString(R.string.totalRemove),TextUtilities.getNumberInCurrencyFormaNoDecimal(importe)));
    }

    public DatosAbonoOpcion getDatosAbonoOpcion() {
        return datosAbonoOpcion;
    }

    public void setDatosAbonoOpcion(DatosAbonoOpcion datosAbonoOpcion) {
        this.datosAbonoOpcion = datosAbonoOpcion;
    }

    public int getClv_Abonar() {
        return clv_Abonar;
    }

    public void setClv_Abonar(int clv_Abonar) {
        this.clv_Abonar = clv_Abonar;
    }

    public ConsultaMetodosPagoResponse.PaymentWay getPaymentSelected(){

        return consultaMetodosPagoResponse.getData().getResponse().get(indexPaymentSelected);

    }

    public double getAmount(){
        return !edtAbonoActual.getQuantity().isEmpty() ?  Double.parseDouble(edtAbonoActual.getQuantity()) : 0.0;
    }
}