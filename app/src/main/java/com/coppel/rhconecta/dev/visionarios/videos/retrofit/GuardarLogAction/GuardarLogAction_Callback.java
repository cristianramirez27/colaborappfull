package com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Response.ResponseGuardarLogAction;

public interface GuardarLogAction_Callback {

    void onSuccessGuardarLogAction(ResponseGuardarLogAction result);

    void onFailGuardarLogAction(String result);
}
