package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Events;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.ObtenerVideosDetalle_Callback;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

public class ObtenerVideosDetalleEvent {

    private ResponseObtenerVideosDetalle serverResponse;

    public ObtenerVideosDetalleEvent(ResponseObtenerVideosDetalle serverResponse, final ObtenerVideosDetalle_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerVideosDetalle(serverResponse);
    }

    /*public ObtenerVideosDetalleEvent(ArrayList<Video> serverResponse, final ObtenerVideosDetalle_Callback callback) {
        this.serverResponse = serverResponse;
        callback.onSuccessObtenerVideosDetalle(serverResponse);
    }*/

    public ResponseObtenerVideosDetalle getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ResponseObtenerVideosDetalle serverResponse) {
        this.serverResponse = serverResponse;
    }

}