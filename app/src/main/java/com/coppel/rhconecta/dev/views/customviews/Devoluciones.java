package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.views.adapters.DevolutionsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Devoluciones extends RelativeLayout {

    @BindView(R.id.titleDevolutions)
    TextViewDetail titleDevolutions;
    @BindView(R.id.rcvDevoluciones)
    RecyclerView rcvDevoluciones;
    @BindView(R.id.totalesDevolutions)
    TextViewDetail totalesDevolutions;


    public Devoluciones(Context context) {
        super(context);
        initViews();
    }

    public Devoluciones(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_devoluciones, this);
        ButterKnife.bind(this);

        titleDevolutions.setStartFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));
        titleDevolutions.setEndFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));
        titleDevolutions.setStartTextColor(getContext().getResources().getColor(R.color.colorTextGray));
        titleDevolutions.setEndTextColor(getContext().getResources().getColor(R.color.colorTextGray));
        titleDevolutions.setStartTextSize(11);
        titleDevolutions.setEndTextSize(11);
        titleDevolutions.setTexts("Fecha","Importes");

        totalesDevolutions.setStartTextSize(14);
        totalesDevolutions.setEndTextSize(14);
        titleDevolutions.setStartFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));
        titleDevolutions.setEndFont(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));
        titleDevolutions.hideDivider();

        totalesDevolutions.hideDivider();
    }

    public void setTotalesDevolutions(String totalesDevolutions) {
        this.totalesDevolutions.setTexts("Total devoluciones",
                TextUtilities.getNumberInCurrencyFormat(
                        Double.parseDouble( totalesDevolutions)));
    }

    public void setDataRecyclerView(List<Devolution> devolutions){

        rcvDevoluciones.setHasFixedSize(true);
        rcvDevoluciones.setLayoutManager(new LinearLayoutManager(getContext()));
        DevolutionsRecyclerAdapter devolutionsRecyclerAdapter = new DevolutionsRecyclerAdapter(devolutions);
        rcvDevoluciones.setAdapter(devolutionsRecyclerAdapter);

    }
}
