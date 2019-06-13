package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados;

import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

public interface ComunicadoVisto_Callback {
    void ComunicadoVistoMensaje(ArrayList<Comunicado> result);
}
