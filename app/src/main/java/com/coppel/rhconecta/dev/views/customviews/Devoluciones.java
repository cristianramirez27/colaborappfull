package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.Devolution;
import com.coppel.rhconecta.dev.views.adapters.DevolutionsRecyclerAdapter;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Devoluciones extends RelativeLayout {

    @BindView(R.id.titleDevolutions)
    HeaderTitlesExpensesList titleDevolutions;
    @BindView(R.id.rcvDevoluciones)
    RecyclerView rcvDevoluciones;
    @BindView(R.id.totalesDevolutionsTitle)
    TextView totalesDevolutionsTitle;
    @BindView(R.id.totalesDevolutions)
    TextView totalesDevolutions;
    @BindView(R.id.space)
    View space;




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

        titleDevolutions.setFontTitle1(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));
        titleDevolutions.setFontTitle4(ResourcesCompat.getFont(getContext(), R.font.lineto_circular_pro_book));
        titleDevolutions.setColorTitle1(R.color.colorTextGray);
        titleDevolutions.setColorTitle4(R.color.colorTextGray);
        titleDevolutions.setSizeTitle1(11);
        titleDevolutions.setSizeTitle4(11);
        titleDevolutions.setTitle1("Fecha");
        titleDevolutions.setTitle2("");
        titleDevolutions.setTitle3("");
        titleDevolutions.setTitle4("Importes");

        titleDevolutions.setGravityTitle4(Gravity.CENTER);

        totalesDevolutionsTitle.setTextSize(14);
        totalesDevolutions.setTextSize(14);
        //titleDevolutions.hideDivider();

        // totalesDevolutions.hideDivider();
    }

    public void setTotalesDevolutions(String totalesDevolutions) {
        if(totalesDevolutions != null && totalesDevolutions.length() <= 8){
            this.totalesDevolutions.setGravity(Gravity.CENTER);
            space.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 5, 1f));
        }else {
            space.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,5, 2f));
        }
      //  this.totalesDevolutions.setGravity(Gravity.CENTER);
       // this.totalesDevolutionsTitle.setGravity(Gravity.LEFT);
        this.totalesDevolutionsTitle.setText("Total devoluciones");
        this.totalesDevolutions.setText(String.format("$%s",String.valueOf(totalesDevolutions)));

    }

    public void setDataRecyclerView(List<Devolution> devolutions){

        rcvDevoluciones.setHasFixedSize(true);
        rcvDevoluciones.setLayoutManager(new LinearLayoutManager(getContext()));
        DevolutionsRecyclerAdapter devolutionsRecyclerAdapter = new DevolutionsRecyclerAdapter(devolutions);
        rcvDevoluciones.setAdapter(devolutionsRecyclerAdapter);

    }
}
