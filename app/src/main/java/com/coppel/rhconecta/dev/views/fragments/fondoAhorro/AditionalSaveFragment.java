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
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.ICalculatetotal;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.ConsultaAhorroAdicionalResponse;
import com.coppel.rhconecta.dev.business.models.GuardarAhorroResponse;
import com.coppel.rhconecta.dev.business.models.GuardarRetiroResponse;
import com.coppel.rhconecta.dev.business.models.RetiroResponse;
import com.coppel.rhconecta.dev.business.models.WithDrawSavingRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.FondoAhorroActivity;
import com.coppel.rhconecta.dev.views.customviews.EditTextMoney;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentAhorroAdicional;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentFondoAhorro;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_AHORRO;
import static com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType.CONSULTA_RETIRO;
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

    private DialogFragmentAhorroAdicional dialogFragmentAhorroAdicional;

    private  int amountSave = 0;


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
        setFocusChangeListener(edtAhorroActualCambiar);
        setEnableButton(false);

        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);

        WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                CONSULTA_AHORRO,7,numEmployer);

        coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);




        return view;
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
                addAditionaSave();
                break;
        }
    }

    private void addAditionaSave(){


        if(consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaproceso() > 0 ){
            amountSave = edtAhorroActualCambiar.getQuantity() != null ? Integer.parseInt(edtAhorroActualCambiar.getQuantity()) : 0;
        }else {
            amountSave = edtAhorroActualProceso.getQuantity() != null ? Integer.parseInt(edtAhorroActualProceso.getQuantity()) : 0;
        }

        if(amountSave > consultaAhorroAdicionalResponse.getData().getResponse().getImp_maximo()){
            showAlertDialog(getString(R.string.attention), "",
                    getString(R.string.max_saving), String.valueOf(consultaAhorroAdicionalResponse.getData().getResponse().getImp_maximo()), new DialogFragmentAhorroAdicional.OnOptionClick() {
                        @Override
                        public void onAccept() {
                            dialogFragmentAhorroAdicional.close();
                            getActivity().finish();
                        }
                    });
        }else {

            showAlertDialog(getString(R.string.attention), "",
                    getString(R.string.new_saving), String.format("$%d", amountSave), new DialogFragmentAhorroAdicional.OnOptionClick() {
                        @Override
                        public void onAccept() {

                            String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
                            String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
                            WithDrawSavingRequestData withDrawSavingRequestData = new WithDrawSavingRequestData(
                                    GUARDAR_AHORRO,8,numEmployer);
                            withDrawSavingRequestData.setImp_cuotaahorro(amountSave);

                            coppelServicesPresenter.getWithDrawSaving(withDrawSavingRequestData,token);

                        }
                    });

        }
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.WITHDRAWSAVING:

                if(response.getResponse() instanceof ConsultaAhorroAdicionalResponse) {
                    consultaAhorroAdicionalResponse = (ConsultaAhorroAdicionalResponse) response.getResponse();
                    txvTitle.setText(consultaAhorroAdicionalResponse.getData().getResponse().getDes_titulo());
                    txtCurrentAmount.setText(consultaAhorroAdicionalResponse.getData().getResponse().getDes_actual());
                    txtValueCurrentAmount.setText(String.format("$%d",consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaahorro()));
                    /**Se muestra mensaje si hay contenido que mostrar*/
                    if(consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaproceso() > 0) {
                        edtAhorroActualCambiar.setVisibility(View.VISIBLE);
                        edtAhorroActualProceso.setVisibility(View.VISIBLE);
                        edtAhorroActualProceso.setInformativeMode(consultaAhorroAdicionalResponse.getData().getResponse().getDes_proceso(),"");
                        edtAhorroActualProceso.setInformativeQuantity(String.format("$%d",consultaAhorroAdicionalResponse.getData().getResponse().getImp_cuotaproceso()));
                        edtAhorroActualProceso.setSizeQuantity(30);
                        edtAhorroActualCambiar.setInformativeMode(consultaAhorroAdicionalResponse.getData().getResponse().getDes_ahorro(),"");
                        edtAhorroActualCambiar.setSizeQuantity(22);
                        edtAhorroActualCambiar.setHint("Ingresa otra cantidad");
                        edtAhorroActualCambiar.setEnableQuantity(true);
                        btnAdd.setText("Cambiar");

                    }else {
                        edtAhorroActualProceso.setVisibility(View.VISIBLE);
                        edtAhorroActualProceso.setInformativeMode(consultaAhorroAdicionalResponse.getData().getResponse().getDes_ahorro(), "");
                        edtAhorroActualCambiar.setSizeQuantity(22);
                        edtAhorroActualProceso.setHint("Ingresa una cantidad");
                        edtAhorroActualProceso.setEnableQuantity(true);
                        edtAhorroActualCambiar.setVisibility(View.GONE);
                        btnAdd.setText("Agregar");

                    }
                }else if(response.getResponse() instanceof GuardarAhorroResponse){

                    String des_mensaje = ((GuardarAhorroResponse) response.getResponse()).getData().getResponse().getDes_mensaje();

                    String subtitle =  des_mensaje.substring(0,des_mensaje.indexOf('.'));
                    String msg =  des_mensaje.substring(des_mensaje.indexOf('.')+1,des_mensaje.indexOf('$'));
                    String amount  =  des_mensaje.substring(des_mensaje.indexOf('$'),des_mensaje.length() -1);
                    showAlertDialog("", subtitle, msg, amount, new DialogFragmentAhorroAdicional.OnOptionClick() {
                        @Override
                        public void onAccept() {
                            dialogFragmentAhorroAdicional.close();
                            getActivity().finish();
                        }
                    });

                }

                break;
        }
    }


    private void showAlertDialog(String title,String subtitle,String msg,String amount,DialogFragmentAhorroAdicional.OnOptionClick onOptionClick) {
        dialogFragmentAhorroAdicional = new DialogFragmentAhorroAdicional();
        dialogFragmentAhorroAdicional.setTxvTitle(title);
        dialogFragmentAhorroAdicional.setTxvSubtitle(subtitle);
        dialogFragmentAhorroAdicional.setTxvMessage(msg);
        dialogFragmentAhorroAdicional.setTxtAmount(amount);
        dialogFragmentAhorroAdicional.setOnOptionClick(onOptionClick);

        dialogFragmentAhorroAdicional.show(parent.getSupportFragmentManager(), DialogFragmentAhorroAdicional.TAG);
    }




    @Override
    public void showError(ServicesError coppelServicesError) {
        switch (coppelServicesError.getType()) {
            case ServicesRequestType.WITHDRAWSAVING:

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


    private void setFocusChangeListener(EditTextMoney editTextMoney){

        editTextMoney.getEdtQuantity().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEnableButton(count > 0 ? true : false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextMoney.getEdtQuantity().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus)
                    editTextMoney.setTextWatcherMoney(AditionalSaveFragment.this);
            }
        });
    }


    private void setEnableButton(boolean isEnable){
        btnAdd.setEnabled(isEnable);
        btnAdd.setBackgroundResource(isEnable ? R.drawable.background_blue_rounded : R.drawable.background_disable_rounded);
    }

    @Override
    public void calculate() {

    }
}