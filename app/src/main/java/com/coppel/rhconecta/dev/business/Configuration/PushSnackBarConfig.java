package com.coppel.rhconecta.dev.business.Configuration;

import androidx.annotation.LayoutRes;
import android.view.View;

/**
 * Created by faust on 12/7/17.
 */

public class PushSnackBarConfig {

    private View view;
    private String name;
    private int color;
    private int colorText;
    private int iResLayout;


    public PushSnackBarConfig(@LayoutRes int iResLayout) {
        this.iResLayout = iResLayout;
    }

    public PushSnackBarConfig(View view, String name, int color) {
        this.view = view;
        this.name = name;
        this.color = color;
    }

    public PushSnackBarConfig(View view, String name) {
        this.view = view;
        this.name = name;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getiResLayout() {
        return iResLayout;
    }

    public void setiResLayout(int iResLayout) {
        this.iResLayout = iResLayout;
    }

    public int getColorText() {
        return colorText;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }
}
