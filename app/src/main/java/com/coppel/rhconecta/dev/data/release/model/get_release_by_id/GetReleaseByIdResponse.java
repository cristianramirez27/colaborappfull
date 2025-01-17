package com.coppel.rhconecta.dev.data.release.model.get_release_by_id;

import com.coppel.rhconecta.dev.domain.release.entity.Release;

/**
 *
 *
 */
public class GetReleaseByIdResponse {

    /* */
    public Data data;

    /**
     *
     *
     */
    public static class Data {

        public ReleaseServer response;

    }

    /**
     *
     *
     */
    public static class ReleaseServer {

        public String idu_avisos;
        public String des_encabezado;
        public String des_titulo;
        public String des_contenido;
        public String img_avisoPreview;
        public String img_coppel;
        public String url_pdf;



        /**
         *
         *
         * @param idu_avisos
         * @param des_encabezado
         * @param des_titulo
         * @param des_contenido
         * @param img_avisoPreview
         * @param img_coppel
         */
        public ReleaseServer(String idu_avisos, String des_encabezado, String des_titulo, String des_contenido, String img_avisoPreview, String img_coppel, String url_pd) {
            this.idu_avisos = idu_avisos;
            this.des_encabezado = des_encabezado;
            this.des_titulo = des_titulo;
            this.des_contenido = des_contenido;
            this.img_avisoPreview = img_avisoPreview;
            this.img_coppel = img_coppel;
            this.url_pdf = url_pdf;

        }

        /**
         *
         *
         * @return
         */
        public Release toRelease(){
            return new Release(
                    idu_avisos,
                    des_encabezado,
                    img_coppel,
                    des_titulo,
                    des_contenido,
                    img_avisoPreview,
                    url_pdf
            );
        }

    }

}
