package com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews;

import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;

import java.util.List;

/**
 *
 *
 */
public class GetVisionariesPreviewsResponse {

    /* */
    public Data data;

    /**
     *
     *
     */
    public static class Data {

        /* */
        public List<VisionaryPreviewServer> response;

    }

    /**
     *
     *
     */
    public static class VisionaryPreviewServer {

        /* */
        public String idu_videos;
        /* */
        public String des_titulo;
        /* */
        public String fec_video;
        /* */
        public String img_videoPreview;
        /* */
        public int num_vistas;
        /* */
        public int opc_visto;

        /**
         *
         *
         * @return
         */
        public VisionaryPreview toVisionaryPreview() {
            return new VisionaryPreview(
                    idu_videos,
                    des_titulo,
                    fec_video,
                    img_videoPreview,
                    num_vistas,
                    opc_visto == 1
            );
        }

    }

}
