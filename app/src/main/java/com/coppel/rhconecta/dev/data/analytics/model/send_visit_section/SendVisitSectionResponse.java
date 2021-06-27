package com.coppel.rhconecta.dev.data.analytics.model.send_visit_section;

import androidx.annotation.Keep;

/* */
@Keep
public class SendVisitSectionResponse {

    /* */
    public Data data;

    /**
     *
     */
    public static class Data {

        /* */
        public Response response;

    }

    /**
     *
     */
    public static class Response {

        /* */
        public int errorCode;

        /* */
        public String userMessage;

        /**
         *
         */
        public boolean wasFailed() {
            return errorCode != 0;
        }

    }
    
}