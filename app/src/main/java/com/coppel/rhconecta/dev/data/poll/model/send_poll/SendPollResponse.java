package com.coppel.rhconecta.dev.data.poll.model.send_poll;

/**
 *
 *
 */
public class SendPollResponse {

    /* */
    public Data data;
    /* */
    public Meta meta;


    /**
     *
     *
     */
    public static class Data {

        /* */
        public String response;

        /**
         *
         *
         */
        public Data(String response) {
            this.response = response;
        }
    }

    /**
     *
     *
     */
    public static class Meta {

        /* */
        public String status;

        /**
         *
         *
         */
        public boolean isSuccess(){
            return status.equals(Status.SUCCESS.toString());
        }

        /**
         *
         *
         */
        public enum Status {
            SUCCESS,
            ERROR;
        }

    }

}
