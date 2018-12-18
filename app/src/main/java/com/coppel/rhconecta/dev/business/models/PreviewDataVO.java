package com.coppel.rhconecta.dev.business.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faustolima on 29/11/18.
 */

public class PreviewDataVO {

    private boolean hasStamp;
    private LetterPreviewResponse.Data dataLetter;
    private List<LetterConfigResponse.Datos> fieldsLetter;
    private CoppelServicesLettersGenerateRequest.Data dataOptional;

    public boolean isHasStamp() {
        return hasStamp;
    }

    public void setHasStamp(boolean hasStamp) {
        this.hasStamp = hasStamp;
    }

    public LetterPreviewResponse.Data getDataLetter() {

        return dataLetter;
    }

    public void setDataLetter(LetterPreviewResponse.Data dataLetter) {
        this.dataLetter = dataLetter;
    }

    public List<LetterConfigResponse.Datos> getFieldsLetter() {

        if(fieldsLetter == null)
            fieldsLetter = new ArrayList<>();

        return fieldsLetter;
    }

    public void setFieldsLetter(List<LetterConfigResponse.Datos> fieldsLetter) {
        this.fieldsLetter = fieldsLetter;
    }

    public CoppelServicesLettersGenerateRequest.Data getDataOptional() {

        if(dataOptional == null)
            dataOptional = new CoppelServicesLettersGenerateRequest.Data();
        return dataOptional;
    }

    public void setDataOptional(CoppelServicesLettersGenerateRequest.Data dataOptional) {
        this.dataOptional = dataOptional;
    }
}
