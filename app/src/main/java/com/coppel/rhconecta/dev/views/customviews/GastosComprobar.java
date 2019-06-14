package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.ExpenseAuthorizedDetail;
import com.coppel.rhconecta.dev.business.models.ExpenseAuthorizedResume;
import com.coppel.rhconecta.dev.views.adapters.ExpenseCheckRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GastosComprobar extends RelativeLayout {

    @BindView(R.id.headerExpenses)
    HeaderTitlesList headerExpenses;
    @BindView(R.id.rcvGastosComprobar)
    RecyclerView rcvGastosComprobar;
    @BindView(R.id.totales)
    HeaderTitlesList totales;


    public GastosComprobar(Context context) {
        super(context);
        initViews();
    }

    public GastosComprobar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.layout_gastos_comrpobar, this);
        ButterKnife.bind(this);

        headerExpenses.setTitle1("Conceptos");
        headerExpenses.setTitle2("Autorizado");
        headerExpenses.setTitle3("Comprobado");
        headerExpenses.setTitle4("Faltante");

        headerExpenses.setPaddingTitle4();
    }


    public void setTotales(ExpenseAuthorizedResume expenseAuthorizedResume) {
        totales.setTitle1("Total");
        totales.setTitle2(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(expenseAuthorizedResume.getTotalAutorizado()))));
        totales.setTitle3(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(expenseAuthorizedResume.getTotalComprobado()))));

        totales.setTitle4(TextUtilities.getNumberInCurrencyFormat(
                Double.parseDouble( String.valueOf(expenseAuthorizedResume.getImp_totalFaltante()))));

        totales.setFontTitle1(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));
        totales.setFontTitle2(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));
        totales.setFontTitle3(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));
        totales.setFontTitle4(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_bold));

        totales.setSizeTitle1(12);
        totales.setSizeTitle2(12);
        totales.setSizeTitle3(12);
        totales.setSizeTitle4(12);

        totales.setColorTitle1(R.color.colorBackgroundCoppelNegro);
        totales.setColorTitle2(R.color.colorBackgroundCoppelNegro);
        totales.setColorTitle3(R.color.colorBackgroundCoppelNegro);
        totales.setColorTitle4(R.color.colorBackgroundCoppelNegro);
    }

    public void setDataRecyclerView(List<ExpenseAuthorizedDetail> expenseAuthorizedDetails){

        rcvGastosComprobar.setHasFixedSize(true);
        rcvGastosComprobar.setLayoutManager(new LinearLayoutManager(getContext()));
        ExpenseCheckRecyclerAdapter expenseCheckRecyclerAdapter = new ExpenseCheckRecyclerAdapter(expenseAuthorizedDetails);
        rcvGastosComprobar.setAdapter(expenseCheckRecyclerAdapter);

    }
}
