package com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id;

import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryRate;

import java.util.ArrayList;
import java.util.List;

import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.RateStatus.DISLIKED;
import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.RateStatus.EMPTY;
import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.RateStatus.LIKED;
import static com.coppel.rhconecta.dev.domain.visionary.entity.Visionary.RateStatus.UNKNOWN;

/* */
public class GetVisionaryByIdResponse {

    /* */
    public Data data;

    /**
     *
     */
    public static class Data {

        /* */
        public VisionaryServer response;

    }

    /**
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
        /* */
        public String des_like;
        /* */
        public List<OptionDto> opc_like;
        /* */
        public String des_dislike;
        /* */
        public List<OptionDto> opc_dislike;

        /**
         *
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
                    getRateStatus(),
                    getVisionaryRate()
            );
        }

        /**
         *
         */
        private Visionary.RateStatus getRateStatus(){
            switch (clv_tipoLog){
                case 0: return EMPTY;
                case 5: return LIKED;
                case 6: return DISLIKED;
                default: return UNKNOWN;
            }
        }

        /**
         *
         */
        private VisionaryRate getVisionaryRate(){
            if (getRateStatus() == EMPTY){
                return new VisionaryRate(
                        des_like,
                        getOptionsFromOptionsDto(opc_like),
                        des_dislike,
                        getOptionsFromOptionsDto(opc_dislike)
                );
            } else return null;
        }

        /**
         *
         */
        private List<VisionaryRate.Option> getOptionsFromOptionsDto(List<OptionDto> optionsDto) {
            ArrayList<VisionaryRate.Option> options = new ArrayList<>();
            if (optionsDto == null)
                return options;
            for (OptionDto optionDto : optionsDto)
                options.add(optionDto.toOption());
            return options;
        }

    }

    /**
     *
     */
    public static class OptionDto {

        /* */
        public String idu_calificar;

        /* */
        public String des_calificar;

        /**
         *
         */
        public VisionaryRate.Option toOption() {
            return new VisionaryRate.Option(idu_calificar, des_calificar);
        }

    }

}
