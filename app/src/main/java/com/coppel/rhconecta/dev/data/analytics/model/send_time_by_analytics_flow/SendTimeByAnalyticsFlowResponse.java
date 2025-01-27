package com.coppel.rhconecta.dev.data.analytics.model.send_time_by_analytics_flow;

import androidx.annotation.Keep;

/* */
@Keep
public class SendTimeByAnalyticsFlowResponse {

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
