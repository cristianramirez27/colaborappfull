package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.AuthorizedResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorRequestsListExpensesResponse;
import com.coppel.rhconecta.dev.business.models.DetailExpenseTravelData;
import com.coppel.rhconecta.dev.business.models.ExpensesTravelRequestData;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.GastosViajeDetalleActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.COMPLEMENTO;
import static com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType.SOLICITUD;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_EXPENSES_TRAVEL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_GTE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class RefuseReasonFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        DialogFragmentGetDocument.OnButtonClickListener,DialogFragmentWarning.OnOptionClick{

    public static final String TAG = RefuseReasonFragment.class.getSimpleName();
    private GastosViajeDetalleActivity parent;
    @BindView(R.id.reason)
    EditText reason;
    @BindView(R.id.btnActionLeft)
    Button btnActionLeft;
    @BindView(R.id.btnActionRight)
    Button btnActionRight;


    private boolean EXPIRED_SESSION;


    private DialogFragmentWarning dialogFragmentWarning;
    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;

    private DetailExpenseTravelData detailExpenseTravelData;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static RefuseReasonFragment getInstance(DetailExpenseTravelData data){
        RefuseReasonFragment fragment = new RefuseReasonFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL,data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.reason_refuse_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeDetalleActivity) getActivity();
        ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator request = (ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator)detailExpenseTravelData.getData();


        parent.setToolbarTitle(request.getTipo() == 1 ?  getString(R.string.title_refuse_complement) :getString(R.string.title_refuse_request));

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        btnActionLeft.setOnClickListener(this);
        btnActionRight.setOnClickListener(this);

        //reason.setFilters(new InputFilter[]{InputFilter.AllCaps});
        reason.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

       /* reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()  > 0){
                    btnActionRight.setEnabled(true);
                    btnActionRight.setBackgroundResource(R.drawable.background_blue_rounded);
                }else {
                    btnActionRight.setEnabled(false);
                    btnActionRight.setBackgroundResource(R.drawable.background_darkgray_rounder);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.detailExpenseTravelData = (DetailExpenseTravelData)getArguments().getSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL);
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
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
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1200){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.btnActionLeft:
                getActivity().finish();
                break;
            case R.id.btnActionRight:
                refuseRequest();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    private void refuseRequest(){
        if(!hasReason()){
            Toast.makeText(getActivity(),"Favor de ingresar el motivo de rechazo.",Toast.LENGTH_SHORT).show();
            return;
        }
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_GTE);


        ExpensesTravelRequestData expensesTravelRequestData = new ExpensesTravelRequestData(ExpensesTravelType.RECHAZAR_SOLICITUD, 9,numEmployer);
        expensesTravelRequestData.setClv_solicitud(detailExpenseTravelData.getClave());
        expensesTravelRequestData.setNum_gerente(Integer.parseInt(numGte));
        ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator requestComplementsColaborator  = (ColaboratorRequestsListExpensesResponse.RequestComplementsColaborator) this.detailExpenseTravelData.getData();
        expensesTravelRequestData.setNum_control(Integer.parseInt(requestComplementsColaborator.getCLV_CONTROL()));
        expensesTravelRequestData.setClv_estatus(5);
        expensesTravelRequestData.setDes_observaciones(this.detailExpenseTravelData.getObservations());
        expensesTravelRequestData.setDes_motivoRechazo(reason.getText().toString());
        expensesTravelRequestData.setClv_tipo(detailExpenseTravelData.getDetailExpenseTravelType() == COMPLEMENTO ? 1 : 0);
        coppelServicesPresenter.getExpensesTravel(expensesTravelRequestData,token);
    }

    private boolean hasReason(){
        return reason.getText()!=null && !reason.getText().toString().isEmpty() ? true : false;
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {

            case ServicesRequestType.EXPENSESTRAVEL:
                if(response.getResponse() instanceof AuthorizedResponse){
                    showAlertDialog(((AuthorizedResponse)response.getResponse()).getData().getResponse().getDes_mensaje());
                }
                break;
        }
    }

    private void showAlertDialog(String msg) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setType(MSG_EXPENSES_TRAVEL, parent);
        dialogFragmentGetDocument.setContentText(msg);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    @Override
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {
        dialogFragmentGetDocument.close();
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showError(ServicesError coppelServicesError) {
        if(coppelServicesError.getMessage() != null ){
            switch (coppelServicesError.getType()) {
                case ServicesRequestType.EXPENSESTRAVEL:
                    showWarningDialog(coppelServicesError.getMessage());
                    break;
                case ServicesRequestType.INVALID_TOKEN:
                    EXPIRED_SESSION = true;
                    showWarningDialog(getString(R.string.expired_session));
                    break;
            }

        }
    }

    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(parent.getSupportFragmentManager(), DialogFragmentWarning.TAG);
    }

    @Override
    public void onLeftOptionClick() {
        dialogFragmentWarning.close();
    }

    @Override
    public void onRightOptionClick() {
        if (EXPIRED_SESSION) {
            AppUtilities.closeApp(parent);
        }else {
            dialogFragmentWarning.close();
            getActivity().finish();
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
}
