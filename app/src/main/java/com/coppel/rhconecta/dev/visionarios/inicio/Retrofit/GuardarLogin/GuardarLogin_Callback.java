package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin;

import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Response.ResponseGuardarLogin;

public interface GuardarLogin_Callback {

    void onSuccessGuardarLogin(ResponseGuardarLogin result);

    void onFailGuardarLogin(String result);
}
