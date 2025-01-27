package com.coppel.rhconecta.dev.data.poll.model.get_available_poll_count;

/**
 *
 *
 */
public class GetAvailablePollCountResponse {

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
        public PollCountServer Badges;

    }

    /**
     *
     *
     */
    public static class PollCountServer {

        /* */
        public int opc_encuesta;

    }

}
