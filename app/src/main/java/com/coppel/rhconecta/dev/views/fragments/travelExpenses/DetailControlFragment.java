package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailControlColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.business.models.ExpenseAuthorizedDetail;
import com.coppel.rhconecta.dev.business.models.ExpenseAuthorizedResume;
import com.coppel.rhconecta.dev.views.activities.GastosViajeDetalleActivity;
import com.coppel.rhconecta.dev.views.customviews.Devoluciones;
import com.coppel.rhconecta.dev.views.customviews.GastosComprobar;
import com.coppel.rhconecta.dev.views.customviews.TextViewDetail;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailControlFragment extends Fragment implements  View.OnClickListener{

    public static final String TAG = DetailControlFragment.class.getSimpleName();
    private GastosViajeDetalleActivity parent;
    @BindView(R.id.GastosComprobar)
    GastosComprobar GastosComprobar;
    @BindView(R.id.Devoluciones)
    Devoluciones Devoluciones;
    @BindView(R.id.totalTitle)
    TextView totalDetalleTitle;
    @BindView(R.id.totalDetalle)
    TextView totalDetalle;

    private DetailControlColaboratorResponse detailControlColaboratorResponse;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public static DetailControlFragment getInstance(DetailControlColaboratorResponse detailControlColaboratorResponse){
        DetailControlFragment fragment = new DetailControlFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL,detailControlColaboratorResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.detailControlColaboratorResponse = (DetailControlColaboratorResponse)getArguments().getSerializable(BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more_detail_control, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeDetalleActivity) getActivity();
        parent.setToolbarTitle(getString(R.string.title_detail_colaborator_control));
        setData(detailControlColaboratorResponse.getData().getResponse());

        totalDetalleTitle.setTextSize(16);
        totalDetalle.setTextSize(16);
        //totalDetalle.hideDivider();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    private void setData( DetailControlColaboratorResponse.Response data){
        totalDetalleTitle.setTextSize(14);
        totalDetalle.setTextSize(16);
        totalDetalleTitle.setText("Saldo Total");
        totalDetalle.setGravity(Gravity.CENTER);
        if(data.getSaldoTotal() != null)
            totalDetalle.setText(String.format("$%s",String.valueOf(data.getSaldoTotal().get(0).getSaldo_total())));
        else {
            totalDetalle.setText("-");
        }

        ExpenseAuthorizedResume expenseAuthorizedResume = new ExpenseAuthorizedResume();
        List<ExpenseAuthorizedDetail> expenseAuthorizedDetails = new ArrayList<>();
        HashMap<Integer,ExpenseAuthorizedDetail> expenseAuthorizedDetailHashMap = new HashMap<>();

        if(data.getGastoAutorizado() != null && !data.getGastoAutorizado().isEmpty()) {

            for(DetailRequest authorized : data.getGastoAutorizado()){
                //Total
                if(authorized.getIdu_tipoGasto() == -1){
                    String total= authorized.getImp_total().replace(",","");
                    expenseAuthorizedResume.setTotalAutorizado(Double.parseDouble(total));
                }

                String total= authorized.getImp_total().replace(",","");
                expenseAuthorizedDetailHashMap.put(authorized.getIdu_tipoGasto(),new ExpenseAuthorizedDetail(authorized.getIdu_tipoGasto(),authorized.getDes_tipoGasto(),Double.parseDouble(total)));
            }
        }


        if(data.getGastoComprobado() != null){
            //Gastos comprobados
            for(DetailRequest checked : data.getGastoComprobado()){
                //Total
                if(checked.getIdu_tipoGasto() == -1){
                    String total= checked.getImp_total().replace(",","");
                    expenseAuthorizedResume.setTotalComprobado(Double.parseDouble(total));
                }else if(expenseAuthorizedDetailHashMap.containsKey(checked.getIdu_tipoGasto())){
                    String total= checked.getImp_total().replace(",","");
                    expenseAuthorizedDetailHashMap.get(checked.getIdu_tipoGasto()).setImp_totalComprobado(Double.parseDouble(total));
                }
            }
        }else {
            GastosComprobar.setCheckedIsNull(true);
        }


        if(data.getGastoAutorizado() != null && !data.getGastoAutorizado().isEmpty()) {
            //Gastos comprobados

            if(data.getFaltante() != null){
                for(DetailRequest missing : data.getFaltante()){
                    //Total
                    if(missing.getIdu_tipoGasto() == -1){
                        String total= missing.getImp_total().replace(",","");
                        expenseAuthorizedResume.setImp_totalFaltante(Double.parseDouble(total));
                    }else if(expenseAuthorizedDetailHashMap.containsKey(missing.getIdu_tipoGasto())){
                        String total= missing.getImp_total().replace(",","");
                        expenseAuthorizedDetailHashMap.get(missing.getIdu_tipoGasto()).setImp_totalFaltante(Double.parseDouble(total));
                    }
                }
            }else {
                GastosComprobar.setMissingIsNull(true);
            }



            for(Integer key : expenseAuthorizedDetailHashMap.keySet()){
                if(key != -1)
                expenseAuthorizedDetails.add(expenseAuthorizedDetailHashMap.get(key));
            }

            GastosComprobar.setTotales(expenseAuthorizedResume);
            GastosComprobar.setDataRecyclerView(expenseAuthorizedDetails);
            GastosComprobar.setVisibility(View.VISIBLE);
        }

        if(data.getDevoluciones() != null && !data.getDevoluciones().isEmpty()){
            List<Devolution> devolucionesList = new ArrayList<>();
            double totalDevolution = 0;
            for(Devolution devolution : data.getDevoluciones()){
                    if(devolution.getDu_tipoGasto() != -1){
                        devolucionesList.add(devolution);
                       // String value = devolution.getTotal().replace("$","");
                       // value = value.replace(",","");
                       /// totalDevolution+= Double.parseDouble(value);
                    }else{
                    Devoluciones.setTotalesDevolutions(String.format(String.valueOf(devolution.getImp_total())));
                }
            }


            Devoluciones.setDataRecyclerView(devolucionesList);
            Devoluciones.setVisibility(View.VISIBLE);
        }




    }
}
