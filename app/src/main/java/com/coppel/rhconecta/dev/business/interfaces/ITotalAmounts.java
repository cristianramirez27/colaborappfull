package com.coppel.rhconecta.dev.business.interfaces;

import com.coppel.rhconecta.dev.business.models.DetailRequest;

import java.util.List;

public interface ITotalAmounts {
    public void setTotalColaborator(List<DetailRequest> values);
    public void setTotalGte(double total);
    public void setValueGte(int id ,double total);
}
