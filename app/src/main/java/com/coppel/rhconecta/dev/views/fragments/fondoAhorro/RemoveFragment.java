package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_RETIRO;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.GUARDAR_RETIRO;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.NO_REFUSE_REMOVE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.GuardarRetiroResponse;
import com.coppel.rhconecta.dev.business.models.RetiroResponse;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentFondoAhorro;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.math.BigDecimal;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemoveFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener,ICalculatetotal {

    public static final String TAG = RemoveFragment.class.getSimpleName();
    private static final float INIT_TOTAL_VALUE = 0;
    private FondoAhorroActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private long mLastClickTime = 0;

    private DialogFragmentWarning dialogFragmentWarning;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean WARNING_PERMISSIONS;


    @BindView(R.id.ctlConnectionError)
    ConstraintLayout ctlConnectionError;
    @BindView(R.id.layoutMain)
    LinearLayout layoutMain;
    @BindView(R.id.imgvRefresh)
    ImageView imgvRefresh;

    @BindView(R.id.txvLoanMarginValue)
    TextView txvLoanMarginValue;
    @BindView(R.id.txvAditionalSaveValue)
    TextView txvAditionalSaveValue;

    @BindView(R.id.txtCreditMargin)
    TextView txtCreditMargin;
    @BindView(R.id.txtValueCreditMargin)
    TextView txtValueCreditMargin;
    @BindView(R.id.txtAditionaSave)
    TextView txtAditionaSave;
    @BindView(R.id.txtValueAditionaSave)
    TextView txtValueAditionaSave;

    @BindView(R.id.edtRetiroProceso)
    EditTextMoney edtRetiroProceso;
    @BindView(R.id.edtRetiro)
    EditTextMoney edtRetiro;

    @BindView(R.id.edtRetiroAhorroProceso)
    EditTextMoney edtRetiroAhorroProceso;

    @BindView(R.id.edtRetiroAhorro)
    EditTextMoney edtRetiroAhorro;

    @BindView(R.id.totalImporte)
    TextView totalImporte;
    @BindView(R.id.btnRemove)
    Button btnRemove;
    private boolean EXPIRED_SESSION;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentFondoAhorro dialogFragmentFondoAhorro;

    private RetiroResponse retiroResponse;
    private boolean online;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static RemoveFragment getInstance(){
        RemoveFragment fragment = new RemoveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (FondoAhorroActivity) getActivity();
        parent.setToolbarTitle("Retirar");
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        btnRemove.setOnClickListener(this);
        imgvRefresh.setOnClickListener(this);
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(!isOpen)
                            calculate();
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

         loadContent();

        //Seteamos los valores de margen de credito y ahorro adicional
        txtCreditMargin.setText("Disponible en Margen de crédito");
        String valueCreditMargin = TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito())));

        if(valueCreditMargin.length() >= 12)
            txtValueCreditMargin.setTextSize(11);

        txtValueCreditMargin.setText(valueCreditMargin);

        txvLoanMarginValue.setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito()))));

        txtAditionaSave.setText("Disponible en Ahorro adicional");
        String valueAditionaSave = TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional())));


        if(valueAditionaSave.length() >= 12)
            txtValueAditionaSave.setTextSize(11);


        txtValueAditionaSave.setText(valueAditionaSave);


        txvAditionalSaveValue.setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional()))));

        edtRetiroProceso.setTitleGravity(Gravity.LEFT);
        edtRetiro.setTitleGravity(Gravity.LEFT);
        edtRetiroAhorroProceso.setTitleGravity(Gravity.LEFT);
        edtRetiroAhorro.setTitleGravity(Gravity.LEFT);

        edtRetiroProceso.setPaddinRigthTitle();
        edtRetiro.setPaddinRigthTitle();
        edtRetiroAhorroProceso.setPaddinRigthTitle();
        edtRetiroAhorro.setPaddinRigthTitle();

        setEnableButton(false);
        setFocusChangeListener(edtRetiro);
        setFocusChangeListener(edtRetiroProceso);
        setFocusChangeListener(edtRetiroAhorro);
        setFocusChangeListener(edtRetiroAhorroProceso);

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

    private void loadContent(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                CONSULTA_RETIRO,2,numEmployer);
        coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRemove:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();
                validateWithdrawal();
                break;

            case R.id.imgvRefresh:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();
                loadContent();
                break;
        }
    }
//TODO bug 45 is fixed
    private void validateWithdrawal() {
        Double margenCredito = AppUtilities.toDouble(edtRetiro.getQuantity());
        //Revisamos si hay que reenviar el valor anterior
        if (edtRetiro.getQuantity().isEmpty() && edtRetiroProceso.getVisibility() == VISIBLE) {
            margenCredito = !edtRetiroProceso.getQuantity().isEmpty() ? Double.parseDouble(edtRetiroProceso.getQuantity()) : 0;
        }

        Double ahorroAdicional =  AppUtilities.toDouble(edtRetiroAhorro.getQuantity()) ;
        //Revisamos si hay que reenviar el valor anterior
        if (edtRetiroAhorro.getQuantity().isEmpty() && edtRetiroAhorroProceso.getVisibility() == VISIBLE) {
            ahorroAdicional = !edtRetiroAhorroProceso.getQuantity().isEmpty() ? Double.parseDouble(edtRetiroAhorroProceso.getQuantity()) : 0;
        }

        checkBalance(ahorroAdicional, margenCredito);
    }

    private void checkBalance(Double ahorroAdicional, Double margenCredito) {
        if (ahorroAdicional > getBalanceAhorroAdicional() || margenCredito > getBalanceMargenCredito()) {
            showAlertDialog(getString(R.string.insufficient_balance));
        } else {
            requestWithdrawal(ahorroAdicional, margenCredito);
        }
    }

    private void requestWithdrawal(Double ahorroAdicional, Double margenCredito) {
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(), SHARED_PREFERENCES_TOKEN);

        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                GUARDAR_RETIRO, 3, numEmployer, margenCredito, ahorroAdicional);

        coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData, token);
    }

    private Double getBalanceAhorroAdicional() {
        return Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional()));
    }

    private Double getBalanceMargenCredito() {
        return Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito()));
    }

    @Override
    public void showResponse(ServicesResponse response) {
        ctlConnectionError.setVisibility(View.GONE);
        layoutMain.setVisibility(VISIBLE);
        switch (response.getType()) {
            case ServicesRequestType.WITHDRAWSAVING:
                if(response.getResponse() instanceof  RetiroResponse){
                    retiroResponse = (RetiroResponse)response.getResponse();
                    online = retiroResponse.getData().getResponse().getOpc_retiroenlinea() == 1;
                    /**Se muestra mensaje si hay contenido que mostrar*/
                    if(retiroResponse.getData().getResponse().getDes_mensaje() != null &&
                            !retiroResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                        showWarningDialog(retiroResponse.getData().getResponse().getDes_mensaje());
                    }
                    configurationUI(retiroResponse);
                }else if(response.getResponse() instanceof GuardarRetiroResponse){
                    if (((GuardarRetiroResponse) response.getResponse()).getData().getResponse().getClv_clave() == 0) {
                        showAlertDialog(((GuardarRetiroResponse) response.getResponse()).getData().getResponse().getDes_mensaje());
                    } else {
                        showAlertDialog((GuardarRetiroResponse) response.getResponse());
                    }
                }
                break;
        }
    }

    private void showAlertDialog(String msg) {
        if (dialogFragmentGetDocument == null) {
            dialogFragmentGetDocument = new DialogFragmentGetDocument();
            dialogFragmentGetDocument.setType(NO_REFUSE_REMOVE, parent);
            dialogFragmentGetDocument.setContentText(msg);
            dialogFragmentGetDocument.setOnButtonClickListener(this);
            dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
        }
    }

    private void showAlertDialog(GuardarRetiroResponse response) {
        dialogFragmentFondoAhorro = new DialogFragmentFondoAhorro();
        if (online) {
            GuardarRetiroResponse.Response data = response.getData().getResponse();
            String folioCredito = "";
            String folioAdicional = "";
            if (data.getFormapago() != null && !data.getFormapago().isEmpty() && data.getClv_folio() != null && !data.getClv_folio().isEmpty())
                folioCredito = data.getFormapago() + "\n" + data.getClv_folio();
            if (data.getFormapagoahorro() != null && !data.getFormapagoahorro().isEmpty() && data.getClv_folioahorro() != null && !data.getClv_folioahorro().isEmpty())
                folioAdicional = data.getFormapagoahorro() + "\n" + data.getClv_folioahorro();

            dialogFragmentFondoAhorro.initView(response.getData().getResponse().getDes_mensaje(), folioCredito, folioAdicional);
        } else {
            dialogFragmentFondoAhorro.initView(response.getData().getResponse().getDes_mensaje(),
                    response.getData().getResponse().getClv_folio(),
                    response.getData().getResponse().getFec_captura(),
                    response.getData().getResponse().getHrs_captura());
        }
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
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {
        dialogFragmentGetDocument.close();
        dialogFragmentGetDocument = null;
    }

    private void configurationUI(RetiroResponse retiroResponse) {
        if (retiroResponse.getData().getResponse().getImp_margencredito() > 0) {
            String information = getInformation(retiroResponse.getData().getResponse().getDes_proceso());
            edtRetiroProceso.setVisibility(VISIBLE);
            edtRetiroProceso.setInformativeMode(information, "");
            edtRetiroProceso.setInformativeQuantity(String.format(Locale.getDefault(), "$%.2f", retiroResponse.getData().getResponse().getImp_margencredito()));
            edtRetiro.setInformativeMode(retiroResponse.getData().getResponse().getDes_cambiar(), "");
            edtRetiro.setHint("Ingresa otra cantidad");
            edtRetiro.setEnableQuantity(true);
        } else {
            edtRetiro.setInformativeMode(getString(R.string.want_remove), "");
            edtRetiro.setHint("Ingresa una cantidad");
            edtRetiro.setEnableQuantity(true);
            edtRetiroProceso.setVisibility(View.GONE);
            edtRetiro.setTextWatcherMoneyDecimal();
        }

        if (retiroResponse.getData().getResponse().getImp_ahorroadicional() > 0) {
            String information = getInformation(retiroResponse.getData().getResponse().getDes_proceso());
            edtRetiroAhorroProceso.setInformativeMode(information, "");
            edtRetiroAhorroProceso.setInformativeQuantity(String.format(Locale.getDefault(), "$%.2f", retiroResponse.getData().getResponse().getImp_ahorroadicional()));
            edtRetiroAhorro.setInformativeMode(retiroResponse.getData().getResponse().getDes_cambiar(), "");
            edtRetiroAhorro.setHint("Ingresa otra cantidad");
            edtRetiroAhorro.setEnableQuantity(true);
        } else {
            edtRetiroAhorro.setInformativeMode(getString(R.string.want_remove), "");
            edtRetiroAhorro.setHint("Ingresa una cantidad");
            edtRetiroAhorro.setEnableQuantity(true);
            edtRetiroAhorroProceso.setVisibility(View.GONE);
            edtRetiroAhorro.setTextWatcherMoneyDecimal();
        }
        totalImporte.setText(String.format("%s $%.2f", getString(R.string.totalRemove), INIT_TOTAL_VALUE));
    }

    private String getInformation(String information) {
        return information != null && !information.isEmpty() ? information : getString(R.string.withdrawal_in_process);
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        if (coppelServicesError.getMessage() != null && !coppelServicesError.getMessage().isEmpty()) {
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.WITHDRAWSAVING:
                    showAlertDialog(coppelServicesError.getMessage());
                    break;
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;
            }
        } else {
            layoutMain.setVisibility(View.GONE);
            ctlConnectionError.setVisibility(VISIBLE);
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

    private void setFocusChangeListener(EditTextMoney editTextMoney){



        editTextMoney.getEdtQuantity().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEnable = false;
                String value = edtRetiro.getQuantity();
                String value2 = edtRetiroAhorro.getQuantity();
                if((value.length() >0 && AppUtilities.isNumeric(value)) || (value2.length() >0 && AppUtilities.isNumeric(value2)) )
                    isEnable = true;

                setEnableButton(isEnable);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editTextMoney.getEdtQuantity().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editTextMoney.setTextWatcherMoneyDecimal();
                    DeviceManager.showKeyBoard(getActivity());
                }

            }
        });
    }

    private void setEnableButton(boolean isEnable){
        btnRemove.setEnabled(isEnable);
        btnRemove.setBackgroundResource(isEnable ? R.drawable.background_blue_rounded : R.drawable.background_disable_rounded);
    }

    @Override
    public void calculate() {
        BigDecimal margin;
        BigDecimal ahorro;

        String contentMargin = edtRetiro.getQuantity();
        String contentAhorro = edtRetiroAhorro.getQuantity();

        contentMargin = !contentMargin.isEmpty() ? contentMargin : "0";
        margin = new BigDecimal(contentMargin);
        contentAhorro = !contentAhorro.isEmpty() ? contentAhorro : "0";
        ahorro = new BigDecimal(contentAhorro);

        BigDecimal total = margin.add(ahorro);
        totalImporte.setText(String.format("%s $%.2f", getString(R.string.totalRemove), total));

        if ((int) Double.parseDouble(
                TextUtilities.insertDecimalPoint(
                        parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito()
                )) < margin.doubleValue()
                || (int) Double.parseDouble(
                TextUtilities.insertDecimalPoint(
                        parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional()
                )) < ahorro.doubleValue()) {
            setEnableButton(false);
            showAlertDialog("No cuenta con saldo disponible");
        }
    }
}