package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

/**
 * Created by faust on 1/26/18.
 */

public class PushData implements Serializable {

    private String type;
    private String title;
    private String subtitle;
    private String bt1Title;
    private String btn1;
    private String bt2Title;
    private String btn2;
    private String bt3Title;
    private String btn3;
    private String loginRequired;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBt1Title() {
        return bt1Title;
    }

    public void setBt1Title(String bt1Title) {
        this.bt1Title = bt1Title;
    }

    public String getBtn1() {
        return btn1;
    }

    public void setBtn1(String btn1) {
        this.btn1 = btn1;
    }

    public String getBt2Title() {
        return bt2Title;
    }

    public void setBt2Title(String bt2Title) {
        this.bt2Title = bt2Title;
    }

    public String getBtn2() {
        return btn2;
    }

    public void setBtn2(String btn2) {
        this.btn2 = btn2;
    }

    public String getBt3Title() {
        return bt3Title;
    }

    public void setBt3Title(String bt3Title) {
        this.bt3Title = bt3Title;
    }

    public String getBtn3() {
        return btn3;
    }

    public void setBtn3(String btn3) {
        this.btn3 = btn3;
    }

    public String getLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(String loginRequired) {
        this.loginRequired = loginRequired;
    }
}
