package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.DetailRequest;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.views.adapters.AmountsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.adapters.DevolutionsRecyclerAdapter;
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


    }

    public void setTotalesImportes(String totales) {
        totalesImports.setTexts("Total", TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble(totales)) );
    }

    public void setDataRecyclerView(List<DetailRequest> importes){

        for (DetailRequest detailRequest :importes)
            detailRequestList.add(detailRequest);

        amountsRecyclerAdapter.notifyDataSetChanged();
    }
}
