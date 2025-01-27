package com.coppel.rhconecta.dev.business.models;

import java.util.ArrayList;
import java.util.List;

public class AuthorizedRequestColaboratorSingleton {

    private  CoppelServicesAuthorizedRequest coppelServicesAuthorizedRequest;

    private List<DetailRequest> DatosColaborador;

    private static final AuthorizedRequestColaboratorSingleton myInstance = new AuthorizedRequestColaboratorSingleton();

    public static AuthorizedRequestColaboratorSingleton getInstance() {
        return myInstance;
    }

    private AuthorizedRequestColaboratorSingleton() {

    }

    public CoppelServicesAuthorizedRequest getCoppelServicesAuthorizedRequest() {

        if(coppelServicesAuthorizedRequest == null)
            coppelServicesAuthorizedRequest = new CoppelServicesAuthorizedRequest();

        return coppelServicesAuthorizedRequest;
    }

    public void setCoppelServicesAuthorizedRequest(CoppelServicesAuthorizedRequest coppelServicesAuthorizedRequest) {
        this.coppelServicesAuthorizedRequest = coppelServicesAuthorizedRequest;
    }

    public void resetValues(){
        coppelServicesAuthorizedRequest = null;
        DatosColaborador = null;
    }

    public List<DetailRequest> getDatosColaborador() {

        if(DatosColaborador == null)
            DatosColaborador = new ArrayList<>();

        return DatosColaborador;
    }

    public void setDatosColaborador(List<DetailRequest> datosColaborador) {
        DatosColaborador = datosColaborador;
    }
}
