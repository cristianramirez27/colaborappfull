package com.coppel.rhconecta.dev.views.modelview;

import android.graphics.drawable.Drawable;

public class BannerItem {

    private String title;
    private String subTitle;
    private String imagePath;
    private Drawable image;

    public BannerItem(String title, String subTitle, String imagePath, Drawable image) {
        this.title = title;
        this.subTitle = subTitle;
        this.imagePath = imagePath;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
