package com.coppel.rhconecta.dev.data.poll.model.get_poll;

import com.coppel.rhconecta.dev.domain.poll.entity.Poll;
import com.coppel.rhconecta.dev.domain.poll.entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class GetPollResponse {

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
        public Header Encabezado;
        /* */
        public List<QuestionServer> Encuesta;
        /* */
        public String mensaje;

        /**
         *
         *
         */
        public Poll toPoll(){
            return new Poll (
                    Encabezado.idu_encuesta,
                    Encabezado.des_encabezado,
                    Encabezado.img_encuestaLanding,
                    Encabezado.img_encuestaPreview,
                    getQuestions()
            );
        }

        /**
         *
         *
         */
        private List<Question> getQuestions(){
            ArrayList<Question> questions = new ArrayList<>();
            for(QuestionServer questionServer : Encuesta)
                questions.add(questionServer.toQuestion());
            return questions;
        }

        /**
         *
         *
         */
        public boolean wasFailed() {
            return mensaje != null;
        }

    }

    /**
     *
     *
     */
    public static class Header {

        /* */
        public int idu_encuesta;
        /* */
        public int clv_estatus;
        /* */
        public String des_encabezado;
        /* */
        public String img_encuestaLanding;
        /* */
        public String img_encuestaPreview;

    }

    /**
     *
     *
     */
    public static class QuestionServer {

        /* */
        public int idu_pregunta;
        /* */
        public int idu_preguntas;
        /* */
        public int idu_cuestionario;
        /* */
        public String des_contenido;
        /* */
        public String des_respuetascuestionario;

        private final String QUESTION_SEPARATOR = "\\|\\|";
        private final String ANSWER_SEPARATOR = "\\^";

        /**
         *
         *
         * @return
         */
        public Question toQuestion() {
            return new Question(
                    idu_pregunta,
                    idu_cuestionario,
                    des_contenido,
                    getAnswers()
            );
        }

        /**
         *
         *
         */
        private List<Question.Answer> getAnswers() {
            String[] fullServerAnswers = des_respuetascuestionario.split(QUESTION_SEPARATOR);
            ArrayList<Question.Answer> answers = new ArrayList<>(fullServerAnswers.length);
            for (String fullServerAnswer : fullServerAnswers) {
                int value = Integer.parseInt(fullServerAnswer.split(ANSWER_SEPARATOR)[0]);
                String content = fullServerAnswer.split(ANSWER_SEPARATOR)[1];
                answers.add(new Question.Answer(value, content));
            }
            return answers;
        }

    }

}
