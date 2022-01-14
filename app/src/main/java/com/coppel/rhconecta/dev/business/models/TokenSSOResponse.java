package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class TokenSSOResponse  implements Serializable {
    private String token_user;

    public String getToken_user() {
        return token_user;
    }

    public void setToken_user(String token_user) {
        this.token_user = token_user;
    }
}
