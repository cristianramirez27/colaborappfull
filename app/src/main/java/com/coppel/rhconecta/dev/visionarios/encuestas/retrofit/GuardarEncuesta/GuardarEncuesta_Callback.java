package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta;

import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Response.ResponseGuardarEncuesta;

public interface GuardarEncuesta_Callback {

    void onSuccessGuardarEncuesta(ResponseGuardarEncuesta result);

    void onFailGuardarEncuesta(String result);
}

