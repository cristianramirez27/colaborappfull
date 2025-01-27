package com.coppel.rhconecta.dev.data.release.model.get_releases_previews;

import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;

import java.util.List;

/**
 *
 */
public class GetReleasesPreviewsResponse {

    /* */
    public Data data;

    /**
     *
     */
    public static class Data {

        /* */
        public List<ReleasePreviewServer> response;

    }

    /**
     *
     */
    public static class ReleasePreviewServer {

        /* */
        public int idu_avisos;
        /* */
        public String des_titulo;
        /* */
        public String fec_comunicado;
        /* */
        public int opc_visto;
        /* */
        public int clv_edito;

        /**
         *
         */
        public ReleasePreview toReleasePreview() {
            return new ReleasePreview(
                    idu_avisos,
                    des_titulo,
                    fec_comunicado,
                    opc_visto == 1,
                    opc_visto == 1 && clv_edito == 1
            );
        }

    }

}
