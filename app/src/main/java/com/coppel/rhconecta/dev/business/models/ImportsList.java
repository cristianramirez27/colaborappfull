package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class ImportsList implements Serializable {

        private List<DetailRequest> importes;


    public List<DetailRequest> getImportes() {
        return importes;
    }

    public void setImportes(List<DetailRequest> importes) {
        this.importes = importes;
    }
}
