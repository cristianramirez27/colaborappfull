package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class ImportsList implements Serializable {

        private List<DetailRequest> importes;
        private int status;
        private int type;
        private boolean isGte;
        private boolean hasComplement;


        private boolean showEdit;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isGte() {
        return isGte;
    }

    public boolean isHasComplement() {
        return hasComplement;
    }

    public void setHasComplement(boolean hasComplement) {
        this.hasComplement = hasComplement;
    }

    public void setGte(boolean gte) {
        isGte = gte;
    }

    public List<DetailRequest> getImportes() {
        return importes;
    }

    public void setImportes(List<DetailRequest> importes) {
        this.importes = importes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isShowEdit() {
        return showEdit;
    }

    public void setShowEdit(boolean showEdit) {
        this.showEdit = showEdit;
    }
}
