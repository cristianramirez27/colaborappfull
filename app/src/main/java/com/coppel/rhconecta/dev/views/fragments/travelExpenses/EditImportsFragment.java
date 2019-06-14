package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.interfaces.ITotalAmounts;
import com.coppel.rhconecta.dev.business.models.AuthorizedRequestColaboratorSingleton;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.models.ImportsList;
import com.coppel.rhconecta.dev.business.models.RolExpensesResponse;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesRequestType;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.GastosViajeDetalleActivity;
import com.coppel.rhconecta.dev.views.adapters.EditableAmountsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditImportsFragment extends Fragment implements  View.OnClickListener, IServicesContract.View, ITotalAmounts {

    public static final String TAG = EditImportsFragment.class.getSimpleName();
    private GastosViajeDetalleActivity parent;
    @BindView(R.id.rcvImporte)
    RecyclerView rcvImporte;
    @BindView(R.id.btnActionLeft)
    Button btnActionLeft;
    @BindView(R.id.btnActionRight)
    Button btnActionRight;
    @BindView(R.id.totalColaborador)
    TextView totalColaborador;
    @BindView(R.id.totalGerente)
    TextView totalGerente;

    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private ImportsList importsLists;
    private List<DetailRequest> importsListsFilter;
    private EditableAmountsRecyclerAdapter editableAmountsRecyclerAdapter;

    private HashMap<Integer,Double> mapAmounts;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static EditImportsFragment getInstance(ImportsList detail){
        EditImportsFragment fragment = new EditImportsFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL,detail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.importsLists = (ImportsList)getArguments().getSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_amounts_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeDetalleActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.title_refuse_complement));

       initValues();

        editableAmountsRecyclerAdapter = new EditableAmountsRecyclerAdapter(getActivity(),this.importsListsFilter,this);
        rcvImporte.setHasFixedSize(true);
        rcvImporte.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvImporte.setAdapter(editableAmountsRecyclerAdapter);

        setTotalColaborator();

        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        btnActionLeft.setOnClickListener(this);
        btnActionRight.setOnClickListener(this);

        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(!isOpen)
                            calculateTotalGte();
                    }
                });

        return view;
    }

    private void  initValues(){
        this.importsListsFilter = new ArrayList<>();
        this.mapAmounts = new HashMap<>();
        for(DetailRequest detailRequest : this.importsLists.getImportes()){
            if(detailRequest.getIdu_tipoGasto() != -1) {
                this.importsListsFilter.add(detailRequest);
                mapAmounts.put(detailRequest.getIdu_tipoGasto(),0.0);
            }
        }
        calculateTotalGte();
    }

    private void  calculateTotalGte(){
        double total = 0;
        for(Integer key : mapAmounts.keySet()){
            total+= mapAmounts.get(key);
        }
        setTotalGte(total);
    }

    private void saveNewAmounts(){
        List<DetailRequest> newAmounts = new ArrayList<>();

        for(Integer key : mapAmounts.keySet()){
            newAmounts.add(new DetailRequest(key,"",mapAmounts.get(key)));
        }
        //Actualizamos la lista de importes  para autorizar con los nuevos valores
        AuthorizedRequestColaboratorSingleton.getInstance().getCoppelServicesAuthorizedRequest().setCapturaGerente(newAmounts);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

                saveNewAmounts();


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

            case ServicesRequestType.EXPENSESTRAVEL:
                if(response.getResponse() instanceof RolExpensesResponse){

                }
                break;
        }
    }

    @Override
    public void showError(ServicesError coppelServicesError) {

    }

    @Override
    public void showProgress() {
        dialogFragmentLoader = new DialogFragmentLoader();
        dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }


    @Override
    public void setTotalColaborator() {
        double total = 0;
        for(DetailRequest detailRequest : this.importsListsFilter){
            if(detailRequest.getIdu_tipoGasto() != -1)
                total+= detailRequest.getImp_total();
        }

        totalColaborador.setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(String.valueOf(total))));
    }

    @Override
    public void setTotalGte(double total) {

        totalGerente.setText(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(String.valueOf(total))));
    }

    @Override
    public void setValueGte(int id, double total) {
        mapAmounts.put(id,total);
    }

    @Override
    public void hideProgress() {
        if(dialogFragmentLoader != null) dialogFragmentLoader.close();
    }




}
