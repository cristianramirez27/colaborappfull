package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

public interface ObtenerComunicados_Callback {

    void onSuccessObtenerComunicados(ArrayList<Comunicado> result);

    void onFailObtenerComunicados(String result);
}

