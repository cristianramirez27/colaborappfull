package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.AuthorizedRequestColaboratorSingleton;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.utils.Command;
import com.coppel.rhconecta.dev.views.adapters.AmountsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportesView  extends RelativeLayout  {

    @BindView(R.id.rcvImporte)
    RecyclerView rcvImportes;
    @BindView(R.id.totalesImports)
    TextViewDetail totalesImports;
    @BindView(R.id.btnEdit)
    Button btnEdit;

    private Command actionEdit;

    private List<DetailRequest> detailRequestList;

    private  AmountsRecyclerAdapter amountsRecyclerAdapter;


    public ImportesView(Context context) {
        super(context);
        initViews();
    }

    public ImportesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_importes, this);
        ButterKnife.bind(this);

        totalesImports.hideDivider();
        detailRequestList = new ArrayList<>();
        amountsRecyclerAdapter = new AmountsRecyclerAdapter(detailRequestList);
        rcvImportes.setHasFixedSize(true);
        rcvImportes.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvImportes.setAdapter(amountsRecyclerAdapter);

        totalesImports.setStartTextSize(14);
        totalesImports.setEndTextSize(16);
        totalesImports.hideDivider();
        totalesImports.setStartFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));
        totalesImports.setEndFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));

        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( actionEdit != null) {
                    actionEdit.execute();
                }
            }
        });


    }



    public void setActionEdit(Command actionEdit) {
        this.actionEdit = actionEdit;
    }

    public void setVisibilityEdit(int visibility){
        btnEdit.setVisibility(visibility);
    }
    public void setTotalesImportes(String totales,boolean isEdit) {

        String amount = totales.replace(",","");
        totalesImports.setTexts("Total", !isEdit  ?String.format("$%s",totales) :  String.format("%s", TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( amount))));
    }

    public void setDataRecyclerView(List<DetailRequest> importes,boolean isEdit){
        detailRequestList.clear();
        for (DetailRequest detailRequest :importes)
            detailRequestList.add(detailRequest);

        amountsRecyclerAdapter.setEdit(isEdit);
        amountsRecyclerAdapter.notifyDataSetChanged();
    }
}
