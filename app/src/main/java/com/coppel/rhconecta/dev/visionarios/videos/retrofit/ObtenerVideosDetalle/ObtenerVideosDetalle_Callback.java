package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle;

import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

public interface ObtenerVideosDetalle_Callback {

    void onSuccessObtenerVideosDetalle(ResponseObtenerVideosDetalle result);

    void onFailObtenerVideosDetalle(String result);
}
