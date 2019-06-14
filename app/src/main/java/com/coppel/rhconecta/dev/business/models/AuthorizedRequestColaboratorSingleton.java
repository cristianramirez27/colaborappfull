package com.coppel.rhconecta.dev.business.models;

public class AuthorizedRequestColaboratorSingleton {

    private  CoppelServicesAuthorizedRequest coppelServicesAuthorizedRequest;

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
    }

}
