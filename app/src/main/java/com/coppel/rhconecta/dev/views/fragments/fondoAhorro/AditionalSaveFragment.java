package com.coppel.rhconecta.dev.views.fragments.fondoAhorro;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ConsultaAhorroAdicionalResponse;
import com.coppel.rhconecta.dev.business.models.GuardarAhorroResponse;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.DeviceManager;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_AHORRO;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.GUARDAR_AHORRO;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class AditionalSaveFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        DialogFragmentWarning.OnOptionClick,ICalculatetotal {

    public static final String TAG = AditionalSaveFragment.class.getSimpleName();
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

    @BindView(R.id.txvAditionalSaveValue)
    TextView txvAditionalSaveValue;

    @BindView(R.id.title)
    TextView txvTitle;


    @BindView(R.id.txtCurrentAmount)
    TextView txtCurrentAmount;
    @BindView(R.id.txtValueCurrentAmount)
    TextView txtValueCurrentAmount;



    @BindView(R.id.edtAhorroActualProceso)
    EditTextMoney edtAhorroActualProceso;
    @BindView(R.id.edtAhorroActualCambiar)
    EditTextMoney edtAhorroActualCambiar;

    private  ConsultaAhorroAdicionalResponse consultaAhorroAdicionalResponse;

    @BindView(R.id.btnAdd)
    Button btnAdd;
    private boolean EXPIRED_SESSION;
    private boolean enableDialog = false;

    private DialogFragmentAhorroAdicional dialogFragmentAhorroAdicional;
    private DialogFragmentAhorroAdicional dialogMaxAmount = new DialogFragmentAhorroAdicional();

    private  int amountSave = 0;

    private DialogFragmentAhorroAdicional.OnOptionClick onOptionClick = new DialogFragmentAhorroAdicional.OnOptionClick() {
        @Override
        public void onAccept() {
            enableDialog = false;
            dialogMaxAmount.close();
        }

        @Override
        public void onCancel() {
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().isEmpty()) {
                calculate();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static AditionalSaveFragment getInstance(){
        AditionalSaveFragment fragment = new AditionalSaveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aditiona_save, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (FondoAhorroActivity) getActivity();
        parent.setToolbarTitle("Ahorro adicional");
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        btnAdd.setOnClickListener(this);
        txvAditionalSaveValue.setText(TextUtilities.getNumberInCurrencyFormat(Double.parseDouble(TextUtilities.insertDecimalPoint(parent.getLoanSavingFundResponse().getData().getResponse().getAhorroAdicional()))));
        setFocusChangeListener(edtAhorroActualProceso);
        edtAhorroActualCambiar.getEdtQuantity().addTextChangedListener(textWatcher);
        edtAhorroActualCambiar.getEdtQuantity().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    edtAhorroActualCambiar.setTextWatcherMoney();
                    DeviceManager.showKeyBoard(getActivity());
                }

            }
        });
        setEnableButton(false);
        imgvRefresh.setOnClickListener(this);

        loadContent();

/*        KeyboardVisibilityEvent.setEventListener(
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

    private void loadContent(){
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);

        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                CONSULTA_AHORRO,7,numEmployer);

        coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtAhorroActualProceso.setTitleGravity(Gravity.LEFT);
        edtAhorroActualCambiar.setTitleGravity(Gravity.LEFT);
        edtAhorroActualProceso.setPaddinRigthTitle();
        edtAhorroActualCambiar.setPaddinRigthTitle();
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
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();
                addAditionaSave();
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

    private void addAditionaSave(){

        String content = "";
        if(consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaproceso() > 0) {
            content = edtAhorroActualCambiar.getQuantity();
        }else {
            content = edtAhorroActualProceso.getQuantity();
        }

        try {
            amountSave = Integer.parseInt(content);

        }catch (Exception e){

        }


        showAlertDialog(getString(R.string.attention), "",
                    getString(R.string.new_saving),TextUtilities.getNumberInCurrencyFormaNoDecimal(Double.parseDouble(content)),"Confirmar",0,true, new DialogFragmentAhorroAdicional.OnOptionClick() {
                        @Override
                        public void onAccept() {
                            String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
                            String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
                            WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                                    GUARDAR_AHORRO,8,numEmployer);
                            withDrawSavingRequestData.setImp_cuotaahorro(amountSave);
                            coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);

                            dialogFragmentAhorroAdicional.close();
                        }

                    @Override
                    public void onCancel() {

                        dialogFragmentAhorroAdicional.close();

                    }
                });
    }

    @Override
    public void showResponse(ServicesResponse response) {

        ctlConnectionError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);
        switch (response.getType()) {

            case ServicesRequestType.WITHDRAWSAVING:

                if(response.getResponse() instanceof ConsultaAhorroAdicionalResponse) {
                    consultaAhorroAdicionalResponse = (ConsultaAhorroAdicionalResponse) response.getResponse();
                    txvTitle.setText(consultaAhorroAdicionalResponse.getData().getResponse().getDes_titulo());
                    txtCurrentAmount.setText(consultaAhorroAdicionalResponse.getData().getResponse().getDes_actual());
                    txtValueCurrentAmount.setText(String.format("$%.2f",consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaahorro()));
                    /**Se muestra mensaje si hay contenido que mostrar*/
                    if(consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaproceso() > 0) {
                        edtAhorroActualCambiar.setVisibility(VISIBLE);
                        edtAhorroActualProceso.setVisibility(VISIBLE);
                        edtAhorroActualProceso.setInformativeMode(consultaAhorroAdicionalResponse.getData().getResponse().getDes_proceso(),"");
                        edtAhorroActualProceso.setInformativeQuantity(String.format("$%.2f",consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaproceso()));
                        edtAhorroActualCambiar.setInformativeMode(consultaAhorroAdicionalResponse.getData().getResponse().getDes_ahorro(),"");
                        edtAhorroActualCambiar.setHint("Ingresa otra cantidad");
                        edtAhorroActualCambiar.setEnableQuantity(true);
                        btnAdd.setText("Cambiar");

                    }else {
                        edtAhorroActualProceso.setVisibility(VISIBLE);
                        edtAhorroActualProceso.setInformativeMode(consultaAhorroAdicionalResponse.getData().getResponse().getDes_ahorro(), "");
                        edtAhorroActualProceso.setHint("Ingresa una cantidad");
                        edtAhorroActualProceso.setEnableQuantity(true);
                        edtAhorroActualCambiar.setVisibility(View.GONE);
                        btnAdd.setText("Agregar");

                    }
                }else if(response.getResponse() instanceof GuardarAhorroResponse){

                    String des_mensaje = ((GuardarAhorroResponse) response.getResponse()).getData().getResponse().getDes_mensaje();

                    String subtitle =  des_mensaje.substring(0,des_mensaje.indexOf('.'));
                    String msg =  des_mensaje.substring(des_mensaje.indexOf('.')+1,des_mensaje.indexOf('$'));
                    String amount  =  des_mensaje.substring(des_mensaje.indexOf('$'),des_mensaje.length());
                    showAlertDialog("", subtitle, msg, amount,"Aceptar",R.drawable.ic_sent,false, new DialogFragmentAhorroAdicional.OnOptionClick() {
                        @Override
                        public void onAccept() {
                            dialogFragmentAhorroAdicional.close();
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }

                        @Override
                        public void onCancel() {
                            dialogFragmentAhorroAdicional.close();
                        }
                    });

                }

                break;
        }
    }


    private void showAlertDialog(String title,String subtitle,String msg,String amount,String btnAccept,int icon,boolean cancelBtn,DialogFragmentAhorroAdicional.OnOptionClick onOptionClick) {
        dialogFragmentAhorroAdicional = new DialogFragmentAhorroAdicional();
        dialogFragmentAhorroAdicional.setTxvTitle(title);
        dialogFragmentAhorroAdicional.setTxvSubtitle(subtitle);
        dialogFragmentAhorroAdicional.setTxvMessage(msg);
        dialogFragmentAhorroAdicional.setTxtAmount(amount);
        dialogFragmentAhorroAdicional.setiResIcon(icon);
        dialogFragmentAhorroAdicional.setBtnTitle(btnAccept);
        dialogFragmentAhorroAdicional.setOnOptionClick(onOptionClick);
        dialogFragmentAhorroAdicional.setVisibleCancelButton(cancelBtn ? VISIBLE : GONE);
        dialogFragmentAhorroAdicional.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }

    private void showAlertDialogMaxAmount(String title,String subtitle,String msg,String amount,String btnAccept,int icon,boolean cancelBtn,DialogFragmentAhorroAdicional.OnOptionClick onOptionClick) {
        dialogMaxAmount.setTxvTitle(title);
        dialogMaxAmount.setTxvSubtitle(subtitle);
        dialogMaxAmount.setTxvMessage(msg);
        dialogMaxAmount.setTxtAmount(amount);
        dialogMaxAmount.setiResIcon(icon);
        dialogMaxAmount.setBtnTitle(btnAccept);
        dialogMaxAmount.setOnOptionClick(onOptionClick);
        dialogMaxAmount.setVisibleCancelButton(cancelBtn ? VISIBLE : GONE);
        dialogMaxAmount.show(parent.getSupportFragmentManager(), "DialogFragmentAhorroAdicionalMaxAmount");
        enableDialog = true;
    }




    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.WITHDRAWSAVING:
                layoutMain.setVisibility(View.GONE);
                ctlConnectionError.setVisibility(View.VISIBLE);

                break;
            case ServicesRequestType.INVALID_TOKEN:
                EXPIRED_SESSION = true;
                showWarningDialog(getString(R.string.expired_session));
                break;
        }


        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(dialogFragmentLoader != null && dialogFragmentLoader.isVisible()){
                        hideProgress();
                    }

                }
            }, 1200);

        }catch (Exception e){

        }



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
                    calculate();
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if(s.toString().isEmpty()){
                   calculate();
                }*/
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
        btnAdd.setEnabled(isEnable);
        btnAdd.setBackgroundResource(isEnable ? R.drawable.background_blue_rounded : R.drawable.background_disable_rounded);
    }

    @Override
    public void calculate() {

        String content = "";
        if(consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaproceso() > 0) {
            content = edtAhorroActualCambiar.getQuantity();

        }else {
            content = edtAhorroActualProceso.getQuantity();
        }

        if(!content.isEmpty() && Double.parseDouble(content) >= 0.0){
            if(Double.parseDouble(content) > consultaAhorroAdicionalResponse.getData().getResponse().getImp_maximo()){
                setEnableButton(false);
                if (!enableDialog) {
                    showAlertDialogMaxAmount(getString(R.string.attention),
                            "",
                            getString(R.string.max_saving),
                            String.format(Locale.getDefault(),
                                    "$%.2f", consultaAhorroAdicionalResponse.getData().getResponse().getImp_maximo()),
                            "Aceptar",
                            0,
                            false,
                            onOptionClick);
                }
            }else {
                setEnableButton(true);
            }
        }else {
            setEnableButton(false);
        }


    }
}