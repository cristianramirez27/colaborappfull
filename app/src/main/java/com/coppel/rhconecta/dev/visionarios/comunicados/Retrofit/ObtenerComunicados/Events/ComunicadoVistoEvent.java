package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Events;

import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.ComunicadoVisto_Callback;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;

import java.util.ArrayList;

public class ComunicadoVistoEvent {
    private String sMensaje;
    private ArrayList<Comunicado> serverResponse;

    public ComunicadoVistoEvent(ArrayList<Comunicado> serverResponse, final ComunicadoVisto_Callback callback){

        callback.ComunicadoVistoMensaje(serverResponse);
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public ArrayList<Comunicado> getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ArrayList<Comunicado> serverResponse) {
        this.serverResponse = serverResponse;
    }
}
