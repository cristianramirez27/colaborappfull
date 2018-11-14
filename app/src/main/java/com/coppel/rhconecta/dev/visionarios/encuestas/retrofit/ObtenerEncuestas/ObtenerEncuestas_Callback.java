package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas;

import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;

import java.util.ArrayList;

public interface ObtenerEncuestas_Callback {

    void onSuccessObtenerEncuestas(ArrayList<Encuesta> result);

    void onFailObtenerEncuestas(String result);
}
