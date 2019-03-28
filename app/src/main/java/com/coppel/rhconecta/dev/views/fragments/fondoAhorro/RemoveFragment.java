package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import android.Manifest;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType;
import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.GuardarRetiroResponse;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.LetterPreviewResponse;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.business.models.RetiroResponse;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.adapters.FieldLetterRecyclerAdapter;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
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

import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_RETIRO;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.GUARDAR_RETIRO;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.NO_REFUSE_REMOVE;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.NO_RESULT_BENEFITS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_RESPONSE_CONFIG_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.TYPE_KINDERGARTEN;

public class RemoveFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener,ICalculatetotal {

    public static final String TAG = RemoveFragment.class.getSimpleName();
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
        txtValueCreditMargin.setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito()))));
        txvLoanMarginValue.setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito()))));
        txtAditionaSave.setText("Disponible en Ahorro adicional");
        txtValueAditionaSave.setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional()))));
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

        edtRetiroProceso.setSizeQuantity(22);
        edtRetiro.setSizeQuantity(22);
        edtRetiroAhorroProceso.setSizeQuantity(22);
        edtRetiroAhorro.setSizeQuantity(22);


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
                guardarRetiro();
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

    private void guardarRetiro(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        int margenCredito = !edtRetiro.getQuantity().isEmpty() ?  Integer.parseInt(edtRetiro.getQuantity()) : 0;
        int ahorroAdicional = !edtRetiroAhorro.getQuantity().isEmpty() ?  Integer.parseInt(edtRetiroAhorro.getQuantity()) : 0;

        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                GUARDAR_RETIRO,3,numEmployer,margenCredito,ahorroAdicional);

        coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);
    }

    @Override
    public void showResponse(ServicesResponse response) {
        ctlConnectionError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);
        switch (response.getType()) {
            case ServicesRequestType.WITHDRAWSAVING:
                if(response.getResponse() instanceof  RetiroResponse){
                    retiroResponse = (RetiroResponse)response.getResponse();
                    /**Se muestra mensaje si hay contenido que mostrar*/
                    if(retiroResponse.getData().getResponse().getDes_mensaje() != null &&
                            !retiroResponse.getData().getResponse().getDes_mensaje().isEmpty()){
                        showWarningDialog(retiroResponse.getData().getResponse().getDes_mensaje());
                    }
                    configurationUI(retiroResponse);
                }else if(response.getResponse() instanceof GuardarRetiroResponse){
                    if (((GuardarRetiroResponse) response.getResponse()).getData().getResponse().getClv_folio().isEmpty() ||
                            ((GuardarRetiroResponse) response.getResponse()).getData().getResponse().getClv_folio().equals("0")) {
                        showAlertDialog( ((GuardarRetiroResponse) response.getResponse()).getData().getResponse().getDes_mensaje());
                    }else {
                        showAlertDialog( (GuardarRetiroResponse) response.getResponse());
                    }
                }
                break;
        }
    }

    private void showAlertDialog(String msg) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setType(NO_REFUSE_REMOVE, parent);
        dialogFragmentGetDocument.setContentText(msg);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    private void showAlertDialog(GuardarRetiroResponse response) {
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
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {
        dialogFragmentGetDocument.close();
    }

    private void configurationUI(RetiroResponse retiroResponse ){

        if(retiroResponse.getData().getResponse().getImp_margencredito() > 0){
            edtRetiroProceso.setVisibility(View.VISIBLE);
            edtRetiroProceso.setInformativeMode(
                    retiroResponse.getData().getResponse().getDes_proceso(), "");
            edtRetiroProceso.setInformativeQuantity(String.format("$%s",retiroResponse.getData().getResponse().getImp_margencredito()));
            edtRetiro.setInformativeMode(retiroResponse.getData().getResponse().getDes_cambiar(),"");
            edtRetiro.setHint("Ingresa otra cantidad");
            edtRetiro.setEnableQuantity(true);
        }else {
            edtRetiro.setInformativeMode(getString(R.string.want_remove), "");
            edtRetiro.setHint("Ingresa una cantidad");
            edtRetiro.setEnableQuantity(true);
            edtRetiroProceso.setVisibility(View.GONE);
            edtRetiro.setTextWatcherMoney();
        }

        if(retiroResponse.getData().getResponse().getImp_ahorroadicional() > 0){
            edtRetiroAhorroProceso.setInformativeMode(
                    retiroResponse.getData().getResponse().getDes_proceso(), "");
            edtRetiroAhorroProceso.setInformativeQuantity(String.format("$%s",retiroResponse.getData().getResponse().getImp_ahorroadicional()));
            edtRetiroAhorro.setInformativeMode(retiroResponse.getData().getResponse().getDes_cambiar(),"");
            edtRetiroAhorro.setHint("Ingresa otra cantidad");
            edtRetiroAhorro.setEnableQuantity(true);
        }else {
            edtRetiroAhorro.setInformativeMode(getString(R.string.want_remove), "");
            edtRetiroAhorro.setHint("Ingresa una cantidad");
            edtRetiroAhorro.setEnableQuantity(true);
            edtRetiroAhorroProceso.setVisibility(View.GONE);
            edtRetiroAhorro.setTextWatcherMoney();
        }

        totalImporte.setText(String.format("%s $%s",getString(R.string.totalRemove),String.valueOf(retiroResponse.getData().getResponse().getImp_total())));

    }

    @Override
    public void showError(ServicesError coppelServicesError) {

            if(coppelServicesError.getMessage() != null ){
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
                if(edtRetiro.getQuantity().length() >0 || edtRetiroAhorro.getQuantity().length() >0 )
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
                    editTextMoney.setTextWatcherMoney();
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
        String contentMargin = edtRetiro.getQuantity();
        String contentAhorro= edtRetiroAhorro.getQuantity();
        int margin;
        int ahorro;
        try{
            margin = !contentMargin.isEmpty() ? Integer.parseInt(contentMargin) : 0;
        }catch (Exception e){
            margin = 0;
        }



        try{
            ahorro = !contentAhorro.isEmpty() ?Integer.parseInt(contentAhorro) : 0;
        }catch (Exception e){
            ahorro = 0;
        }


        int total = margin + ahorro;
        totalImporte.setText(String.format("%s%s",getString(R.string.totalRemove),TextUtilities.getNumberInCurrencyFormaNoDecimal(total)));


        if((int) Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getMargenCredito())) < margin ||
                (int)Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional())) < ahorro) {

            setEnableButton(false);

            showAlertDialog("No cuenta con saldo disponible");

            return;

        }




    }
}