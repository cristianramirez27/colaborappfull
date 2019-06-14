package com.coppel.rhconecta.dev.views.fragments.travelExpenses;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.models.DetailRequestColaboratorResponse;
import com.coppel.rhconecta.dev.business.models.ImportsList;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.views.activities.GastosViajeDetalleActivity;
import com.coppel.rhconecta.dev.views.customviews.ImportesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_DATA_DETAIL_EXPENSE_TRAVEL;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_DATA_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_OPTION_TRAVEL_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EDIT_AMOUNTS;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRequestComplementFragment extends Fragment implements  View.OnClickListener{

    public static final String TAG = DetailRequestComplementFragment.class.getSimpleName();
    private GastosViajeDetalleActivity parent;
    @BindView(R.id.ImportesView)
    ImportesView Importes;


    private DetailRequestColaboratorResponse detailRequestColaboratorResponse;
    private ImportsList importsLists;

    private long mLastClickTime = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


       public static DetailRequestComplementFragment getInstance(ImportsList detail){
        DetailRequestComplementFragment fragment = new DetailRequestComplementFragment();
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

        View view = inflater.inflate(R.layout.fragment_more_detail_request, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (GastosViajeDetalleActivity) getActivity();
        parent.setToolbarTitle(importsLists.getType() == 1 ?   getString(R.string.title_detail_colaborator_complement )
                :  getString(R.string.title_detail_colaborator_request));

        setData();
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


    private void setData(){

        if(importsLists.getImportes()  != null && !importsLists.getImportes() .isEmpty()){
            List<DetailRequest> requestList = new ArrayList<>();
            for(DetailRequest detailRequest :importsLists.getImportes()){
                if(detailRequest.getIdu_tipoGasto() == -1){
                    Importes.setTotalesImportes(String.valueOf(detailRequest.getImp_total()));
                }else {
                    requestList.add(detailRequest);
                }
            }

           Importes.setVisibilityEdit(importsLists.isShowEdit() ? View.VISIBLE : INVISIBLE);
            //Importes.setVisibilityEdit(importsLists.isGte() && importsLists.getStatus() == 1 ? View.VISIBLE : INVISIBLE);
            //TODO REMOVE Test
            //Importes.setVisibilityEdit(View.VISIBLE);

            Importes.setActionEdit(new Command() {
                @Override
                public void execute(Object... params) {
                    openEditAmounts();
                }
            });

            Importes.setDataRecyclerView(requestList);
            Importes.setVisibility(View.VISIBLE);
        }
    }


    private void openEditAmounts(){
        NavigationUtil.openActivityParamsSerializable(getActivity(), GastosViajeDetalleActivity.class,
                BUNDLE_OPTION_DATA_TRAVEL_EXPENSES, this.importsLists,
                BUNDLE_OPTION_TRAVEL_EXPENSES,OPTION_EDIT_AMOUNTS);
    }

}
