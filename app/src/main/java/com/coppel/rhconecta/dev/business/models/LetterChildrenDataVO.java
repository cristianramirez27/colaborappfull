package com.coppel.rhconecta.dev.business.models;

/**
 * Created by faustolima on 29/11/18.
 */

public class LetterChildrenDataVO {

    private LetterChildrenData data;
    private boolean isSelected;

    public LetterChildrenDataVO(LetterChildrenData data) {
        this.data = data;
    }

    public LetterChildrenData getData() {
        return data;
    }

    public void setData(LetterChildrenData data) {
        this.data = data;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
