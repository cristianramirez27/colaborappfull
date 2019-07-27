package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderTitlesExpensesList extends LinearLayout {

    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.title4)
    TextView title4;
    @BindView(R.id.divider)
    View divider;



    public HeaderTitlesExpensesList(Context context) {
        super(context);
        initViews();
    }

    public HeaderTitlesExpensesList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.header_titles_list, this);
        ButterKnife.bind(this);
    }

    public void setTitle1(String title) {
        title1.setText(title);
    }

    public void setTitle2(String title) {
        title2.setText(title);
    }
    public void setTitle3(String title) {
        title3.setText(title);
    }
    public void setTitle4(String title) {
        title4.setText(title);
    }

    public void setPaddingTitle4(){
        title4.setPadding(0/*30*/,0,0,0);
    }

    public void setPaddingTitle4(int left){
        title4.setPadding(left,0,0,0);
    }


    public void setWeightTitle(int numTitle, float weigth){

        switch (numTitle){
            case 1:
                title1.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, weigth));
                break;
            case 2:
                title2.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, weigth));
                break;
            case 3:
                title3.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, weigth));
                break;
            case 4:
                title4.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, weigth));
                break;
        }
    }

    public void setGravityTitle1(int gravity){
        title1.setGravity(gravity);
    }

    public void setGravityTitle2(int gravity){
        title2.setGravity(gravity);
    }
    public void setGravityTitle3(int gravity){
        title3.setGravity(gravity);
    }
    public void setGravityTitle4(int gravity){
        title4.setGravity(gravity);
    }
    public void setColorTitle1(int color){
        title1.setTextColor(CoppelApp.getContext().getResources().getColor(color));
    }

    public void setColorTitle2(int color){
        title2.setTextColor(CoppelApp.getContext().getResources().getColor(color));
    }

    public void setColorTitle3(int color){
        title3.setTextColor(CoppelApp.getContext().getResources().getColor(color));
    }

    public void setColorTitle4(int color){
        title4.setTextColor(CoppelApp.getContext().getResources().getColor(color));
    }

    public void setSizeTitle1(float size){
        title1.setTextSize(size);
    }

    public void setFontTitle1(Typeface typeface) {
        title1.setTypeface(typeface);
    }
    public void setFontTitle2(Typeface typeface) {
        title2.setTypeface(typeface);
    }
    public void setFontTitle3(Typeface typeface) {
        title3.setTypeface(typeface);
    }
    public void setFontTitle4(Typeface typeface) {
        title4.setTypeface(typeface);
    }


    public void setSizeTitle2(float size){
        title2.setTextSize(size);
    }

    public void setSizeTitle3(float size){
        title3.setTextSize(size);
    }

    public void setSizeTitle4(float size){
        title4.setTextSize(size);
    }

    public void setVisibilityDivider(int visibility){
        divider.setVisibility(visibility);
    }
}
