package com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id;

import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;

import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.Status.DISLIKED;
import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.Status.EMPTY;
import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.Status.LIKED;
import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.Status.UNKNOWN;

/**
 *
 *
 */
public class GetVisionaryByIdResponse {

    /* */
    public Data data;

    /**
     *
     *
     */
    public static class Data {

        /* */
        public VisionaryServer response;

    }

    /**
     *
     *
     */
    public static class VisionaryServer {

        /* */
        public String idu_videos;
        /* */
        public String des_titulo;
        /* */
        public String fec_video;
        /* */
        public String des_contenido;
        /* */
        public String url_videoPreview;
        /* */
        public int num_vistas;
        /* */
        public int opc_visto;
        /* */
        public int clv_tipoLog;


        /**
         *
         *
         * @return
         */
        public Visionary toVisionary(){
            return new Visionary(
                    idu_videos,
                    des_titulo,
                    fec_video,
                    des_contenido,
                    url_videoPreview,
                    num_vistas,
                    opc_visto == 1,
                    visionaryStatusFromIntValue(clv_tipoLog)
            );
        }

        /**
         *
         *
         * @param value
         * @return
         */
        private Visionary.Status visionaryStatusFromIntValue(int value){
            switch (value){
                case 0: return EMPTY;
                case 5: return LIKED;
                case 6: return DISLIKED;
                default: return UNKNOWN;
            }
        }

    }

}
