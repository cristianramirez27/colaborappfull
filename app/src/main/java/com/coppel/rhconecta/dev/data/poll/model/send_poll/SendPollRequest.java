package com.coppel.rhconecta.dev.data.poll.model.send_poll;

import com.coppel.rhconecta.dev.domain.poll.entity.Question;

import java.util.List;
import java.util.Locale;

/**
 *
 *
 */
public class SendPollRequest {

    /* */
    public Long num_empleado;
    /* */
    public List<AnswerServer> dataForm;
    /* */
    public int clv_opcion;

    /**
     *
     *
     */
    public SendPollRequest(Long num_empleado, List<AnswerServer> dataForm, int clv_opcion) {
        this.num_empleado = num_empleado;
        this.dataForm = dataForm;
        this.clv_opcion = clv_opcion;
    }

    /**
     *
     *
     */
    public static class AnswerServer {

        /* */
        public String id;
        /* */
        public int value;

        /**
         *
         *
         */
        private AnswerServer(String id, int value) {
            this.id = id;
            this.value = value;
        }

        /**
         *
         *
         */
        public static AnswerServer fromQuestion(Question question){
            Question.Answer selectedAnswer = question.getSelectedAnswer();
            int value = selectedAnswer.getValue();
            String id = String.format(
                    Locale.US,
                    "preg_%d_%d_%d",
                    question.getPollId(),
                    question.getId(),
                    value
            );
            return new AnswerServer(id, value);
        }

    }


}
