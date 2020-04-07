package com.coppel.rhconecta.dev.data.home.model.get_main_information;

import com.coppel.rhconecta.dev.domain.home.entity.Badge;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;

import java.util.List;

/**
 *
 *
 */
public class GetMainInformationResponse {

    /* */
    public Data data;

    /**
     *
     *
     */
    public static class Data {

        /* */
        public Response response;

    }

    /**
     *
     *
     */
    public static class Response {

        /* */
        public List<BannerServer> Carrusel;
        /* */
        public Badges Badges;

    }

    /**
     *
     *
     */
    public static class BannerServer {

        /* */
        public String idu_videos;
        /* */
        public String src;
        /* */
        public String img_caratula;
        /* */
        public int clv_visionarios;

        /**
         *
         *
         */
        public BannerServer(String idu_videos, String src, String img_caratula, int clv_visionarios) {
            this.idu_videos = idu_videos;
            this.src = src;
            this.img_caratula = img_caratula;
            this.clv_visionarios = clv_visionarios;
        }

        /**
         *
         *
         */
        public Banner toBanner() {
            return new Banner(
                    idu_videos,
                    src,
                    img_caratula,
                    clv_visionarios
            );
        }

    }

    /**
     *
     *
     */
    public static class Badges {

        /* */
        public int num_noVistosComunicados;
        /* */
        public int num_noVistosVideos;
        /* */
        public int opc_encuesta;

        /**
         *
         *
         */
        public Badge getReleaseBagde() {
            return new Badge(num_noVistosComunicados, Badge.Type.RELEASE);
        }

        /**
         *
         *
         */
        public Badge getVisionaryBagde() {
            return new Badge(num_noVistosVideos, Badge.Type.VISIONARY);
        }

        /**
         *
         *
         */
        public Badge getPollBagde() {
            return new Badge(opc_encuesta, Badge.Type.POLL);
        }

    }

}
