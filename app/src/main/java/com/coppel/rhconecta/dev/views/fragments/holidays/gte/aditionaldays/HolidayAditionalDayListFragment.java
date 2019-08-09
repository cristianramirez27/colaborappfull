package com.coppel.rhconecta.dev.views.fragments.holidays.gte.aditionaldays;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CatalogueData;
import com.coppel.rhconecta.dev.business.models.Center;
import com.coppel.rhconecta.dev.business.models.CentersHolidayResponse;
import com.coppel.rhconecta.dev.business.models.ColaboratorHoliday;
import com.coppel.rhconecta.dev.business.models.HolidayRequestData;
import com.coppel.rhconecta.dev.business.models.HolidaysColaboratorsResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.VacacionesActivity;
import com.coppel.rhconecta.dev.views.adapters.ColaboratorHolidayRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentCenter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentEstatus;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_CENTERS;
import static com.coppel.rhconecta.dev.business.Enums.HolidaysType.GET_COLABORATORS;
import static com.coppel.rhconecta.dev.views.dialogs.DialogFragmentGetDocument.MSG_HOLIDAYS_WARNING;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_HOLIDAY_ADITIONAL_COLABORATOR_REQUESTS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidayAditionalDayListFragment extends Fragment implements  View.OnClickListener, IServicesContract.View,
        ColaboratorHolidayRecyclerAdapter.OnRequestSelectedClickListener,
        DialogFragmentCenter.OnButonOptionClick, DialogFragmentWarning.OnOptionClick,DialogFragmentGetDocument.OnButtonClickListener{

    public static final String TAG = HolidayAditionalDayListFragment.class.getSimpleName();
    private AppCompatActivity parent;

    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.seleccionCentroLayout)
    RelativeLayout seleccionCentroLayout;
    @BindView(R.id.centro)
    TextView centro;
    @BindView(R.id.rcvSolicitudes)
    RecyclerView rcvSolicitudes;

    private DialogFragmentGetDocument dialogFragmentGetDocument;
    private DialogFragmentCenter dialogFragmentCenter;
    private Center centerSelected;
    private DialogFragmentEstatus dialogFragmentEstatus;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private List<ColaboratorHoliday> colaboratorHolidays;
    private String searchName = "";
    private boolean doSearch;
    private ColaboratorHolidayRecyclerAdapter colaboratorHolidayRecyclerAdapter;
    private boolean EXPIRED_SESSION;
    private DialogFragmentWarning dialogFragmentWarning;
    private   CentersHolidayResponse centersResponse;
    private long mLastClickTime = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static HolidayAditionalDayListFragment getInstance(){
        HolidayAditionalDayListFragment fragment = new HolidayAditionalDayListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitudes_vacaciones, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        seleccionCentroLayout.setOnClickListener(this);
        if(getActivity() instanceof VacacionesActivity){
            parent = (VacacionesActivity) getActivity();
            ( (VacacionesActivity) parent).setToolbarTitle(getString(R.string.title_add_aditional_days_toolbar));
        }

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvSolicitudes.setHasFixedSize(true);
        rcvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        colaboratorHolidays = new ArrayList<>();
        colaboratorHolidayRecyclerAdapter = new ColaboratorHolidayRecyclerAdapter(getActivity(),colaboratorHolidays);
        colaboratorHolidayRecyclerAdapter.setOnRequestSelectedClickListener(this);
        rcvSolicitudes.setAdapter(colaboratorHolidayRecyclerAdapter);
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(edtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 0){
                    if(doSearch && s.toString().length() == 0){
                        searchName = edtSearch.getText() != null && !edtSearch.getText().toString().isEmpty() ?
                                edtSearch.getText().toString() : "";
                        getColaborators(centerSelected.getNum_centro(),searchName);
                    }
                }else {
                    doSearch = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCenters();
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
            case R.id.seleccionCentroLayout:
                if(centersResponse == null){
                    getCenters();
                }else {
                    showCenters(centersResponse.getData().getResponse().getCentros());
                }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showResponse(ServicesResponse response) {
        switch (response.getType()) {
            case ServicesRequestType.HOLIDAYS:
                if(response.getResponse() instanceof CentersHolidayResponse){
                    //Centros
                    centersResponse = (CentersHolidayResponse)response.getResponse();
                    if (centersResponse.getData().getResponse().getCentros() != null &&
                            !centersResponse.getData().getResponse().getCentros().isEmpty()) {
                        //Se toma el primero por default si solo regresa 1 centro
                            centerSelected = centersResponse.getData().getResponse().getCentros().get(0);
                            centro.setText(centersResponse.getData().getResponse().getCentros().get(0).getNom_centro());
                        if (centersResponse.getData().getResponse().getCentros().size() == 1) {
                            seleccionCentroLayout.setEnabled(false);
                            seleccionCentroLayout.setClickable(false);
                        }
                    }
                    /**Si clv_mensaje = 1 entonces mostrar el mensaje de des_mensaje**/
                    if(centersResponse.getData().getResponse().getClv_mensaje() == 1){
                        showWarningDialog(centersResponse.getData().getResponse().getDes_mensaje());
                    }
                    //Buscamos los colaboradores sin importar el centro
                    getColaborators(-1,searchName);
                } if(response.getResponse() instanceof HolidaysColaboratorsResponse) {
                    HolidaysColaboratorsResponse colaboratorsResponse = (HolidaysColaboratorsResponse)response.getResponse();
                    colaboratorHolidays.clear();
                    /**Si clv_mensaje = 1 entonces mostrar el mensaje de des_mensaje**/
                    if (colaboratorsResponse.getData().getResponse().getClv_mensaje() == 1) {
                        showSuccessDialog(MSG_HOLIDAYS_WARNING,colaboratorsResponse.getData().getResponse().getDes_mensaje(),"");
                    } else if(colaboratorsResponse.getData().getResponse().getClv_mensaje() == 0) {
                        for (ColaboratorHoliday colaborator : colaboratorsResponse.getData().getResponse().getEmpleados()) {
                            colaboratorHolidays.add(colaborator);
                        }
                    }

                colaboratorHolidayRecyclerAdapter.notifyDataSetChanged();
                }

                break;
        }
    }


    private void showSuccessDialog(int type,String title,String content) {
        dialogFragmentGetDocument = new DialogFragmentGetDocument();
        dialogFragmentGetDocument.setContentText(title);
        dialogFragmentGetDocument.setMsgText(content);
        dialogFragmentGetDocument.setType(type, parent);
        dialogFragmentGetDocument.setOnButtonClickListener(this);
        dialogFragmentGetDocument.show(parent.getSupportFragmentManager(), DialogFragmentGetDocument.TAG);
    }

    @Override
    public void onSend(String email) {

    }

    @Override
    public void onAccept() {

        dialogFragmentGetDocument.close();
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
           // getActivity().onBackPressed();
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
    public void onLeftOptionStateClick() {

        if(dialogFragmentCenter != null && dialogFragmentCenter.isVisible())
            dialogFragmentCenter.close();
        else if(dialogFragmentEstatus != null && dialogFragmentEstatus.isVisible())
            dialogFragmentEstatus.close();
    }

    @Override
    public void onRightOptionStateClick(Center data) {
        if(data != null) {
            centerSelected = data;
            dialogFragmentCenter.close();
            centro.setText(centerSelected.getNom_centro());
            getColaborators(centerSelected.getNum_centro(),searchName);
        }
    }

    private void performSearch(String search) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if(search != null && !search.isEmpty()){
            edtSearch.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
            searchName = search;
            getColaborators(centerSelected.getNum_centro(),searchName);
        }
    }

    private void showCenters(List<Center> centers){
        CatalogueData statesData = new CatalogueData();
        statesData.setData(centers);
        dialogFragmentCenter = DialogFragmentCenter.newInstance(statesData, R.layout.dialog_fragment_scheduledata);
        dialogFragmentCenter.setOnButtonClickListener(this);
        dialogFragmentCenter.show(parent.getSupportFragmentManager(), DialogFragmentCenter.TAG);
    }

    private void getCenters(){
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_CENTERS, 6);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }

    private void getColaborators(int centerSelectedId,String search){
        String numGte = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        HolidayRequestData holidayRequestData = new HolidayRequestData(GET_COLABORATORS, 7);
        holidayRequestData.setNum_gerente(Integer.parseInt(numGte));
        holidayRequestData.setClv_estatus(-1);//0
        holidayRequestData.setNum_centro(centerSelectedId);
        holidayRequestData.setNom_empleado(search);

        coppelServicesPresenter.getHolidays(holidayRequestData,token);
    }


    @Override
    public void onRequestSelectedClick(ColaboratorHoliday colaboratorHoliday) {
        doSearch = false;
        ((VacacionesActivity)getActivity()).onEvent(BUNDLE_OPTION_HOLIDAY_ADITIONAL_COLABORATOR_REQUESTS,colaboratorHoliday);
    }
}
