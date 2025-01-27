package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class ExpenseAuthorizedResume {

    private double totalAutorizado;
    private double totalComprobado;
    private double imp_totalFaltante;
    private List<ExpenseAuthorizedDetail> lisExpenses;

    public double getTotalAutorizado() {
        return totalAutorizado;
    }

    public void setTotalAutorizado(double totalAutorizado) {
        this.totalAutorizado = totalAutorizado;
    }

    public double getTotalComprobado() {
        return totalComprobado;
    }

    public void setTotalComprobado(double totalComprobado) {
        this.totalComprobado = totalComprobado;
    }

    public double getImp_totalFaltante() {
        return imp_totalFaltante;
    }

    public void setImp_totalFaltante(double imp_totalFaltante) {
        this.imp_totalFaltante = imp_totalFaltante;
    }

    public List<ExpenseAuthorizedDetail> getLisExpenses() {
        return lisExpenses;
    }

    public void setLisExpenses(List<ExpenseAuthorizedDetail> lisExpenses) {
        this.lisExpenses = lisExpenses;
    }
}
