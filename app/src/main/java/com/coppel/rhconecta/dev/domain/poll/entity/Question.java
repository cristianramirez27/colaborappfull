package com.coppel.rhconecta.dev.domain.poll.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.List;

/**
 *
 *
 */
public class Question {

    /* */
    private int id;
    /* */
    private int pollId;
    /* */
    private String question;
    /* */
    private List<Answer> answers;
    /* */
    private Answer selectedAnswer;

    /**
     *
     *
     */
    public Question(int id, int pollId, String question, List<Answer> answers) {
        this.id = id;
        this.pollId = pollId;
        this.question = question;
        this.answers = answers;
    }

    /**
     *
     *
     */
    public int getId() {
        return id;
    }

    /**
     *
     *
     */
    public int getPollId() {
        return pollId;
    }

    /**
     *
     *
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     *
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     *
     *
     */
    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    /**
     *
     *
     */
    public void setSelectedAnswer(Answer selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    /**
     *
     *
     */
    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /**
     *
     *
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Question)
            return ((Question) obj).id == id;
        return super.equals(obj);
    }

    /**
     *
     *
     */
    public static class Answer {

        /* */
        private int value;
        /* */
        private String content;

        /**
         *
         *
         */
        public Answer(int value, String content) {
            this.value = value;
            this.content = content;
        }

        /**
         *
         *
         */
        public int getValue() {
            return value;
        }

        /**
         *
         *
         */
        public String getContent() {
            return content;
        }

        /**
         *
         *
         */
        @Override
        public boolean equals(@Nullable Object obj) {
            if(obj instanceof Question.Answer)
                return ((Answer) obj).content.equals(content);
            return super.equals(obj);
        }

        /**
         *
         *
         */
        @NonNull
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

}
