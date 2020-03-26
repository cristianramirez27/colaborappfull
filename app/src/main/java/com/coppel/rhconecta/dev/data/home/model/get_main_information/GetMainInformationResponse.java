package com.coppel.rhconecta.dev.data.home.model.get_main_information;

import com.coppel.rhconecta.dev.domain.home.entity.Banner;

import java.util.List;

public class GetMainInformationResponse {

    public Data data;

    public GetMainInformationResponse() {
    }

    public GetMainInformationResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        public Response response;

        public Data() {
        }

        public Data(Response response) {
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public static class Response {

        public List<BannerServer> Carrusel;

        public Response() { }

        public Response(List<BannerServer> carrusel) {
            Carrusel = carrusel;
        }

        public List<BannerServer> getCarrusel() {
            return Carrusel;
        }

        public void setCarrusel(List<BannerServer> carrusel) {
            Carrusel = carrusel;
        }
    }

    public static class BannerServer {

        public String idu_videos;
        public String src;
        public String img_caratula;
        public int clv_visionarios;

        public BannerServer(String idu_videos, String src, String img_caratula, int clv_visionarios) {
            this.idu_videos = idu_videos;
            this.src = src;
            this.img_caratula = img_caratula;
            this.clv_visionarios = clv_visionarios;
        }

        public BannerServer() { }

        public Banner toBanner() {
            return new Banner(idu_videos, src, img_caratula, clv_visionarios);
        }

        public String getIdu_videos() {
            return idu_videos;
        }

        public void setIdu_videos(String idu_videos) {
            this.idu_videos = idu_videos;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getImg_caratula() {
            return img_caratula;
        }

        public void setImg_caratula(String img_caratula) {
            this.img_caratula = img_caratula;
        }

        public int getClv_visionarios() {
            return clv_visionarios;
        }

        public void setClv_visionarios(int clv_visionarios) {
            this.clv_visionarios = clv_visionarios;
        }
    }

}
