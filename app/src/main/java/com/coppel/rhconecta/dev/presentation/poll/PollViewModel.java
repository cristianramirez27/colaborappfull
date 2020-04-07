package com.coppel.rhconecta.dev.presentation.poll;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.util.Log;

import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.entity.Poll;
import com.coppel.rhconecta.dev.domain.poll.entity.Question;
import com.coppel.rhconecta.dev.domain.poll.use_case.GetPollUseCase;
import com.coppel.rhconecta.dev.domain.poll.use_case.SendPollUseCase;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/**
 *
 *
 */
public class PollViewModel {

    /* */
    @Inject
    GetPollUseCase getPollUseCase;
    @Inject
    SendPollUseCase sendPollUseCase;
    // Observables
    private MutableLiveData<ProcessStatus> loadPollProcessStatus = new MutableLiveData<>();
    private MutableLiveData<ProcessStatus> sendPollProcessStatus = new MutableLiveData<>();
    // Values
    private Poll poll;
    private Failure failure;
    // Questions
    private MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
    private int currentQuestionIndex;
    private MutableLiveData<Integer> pollProgress = new MutableLiveData<>();


    /**
     *
     *
     */
    @Inject
    PollViewModel() { /* PASS */ }

    /**
     *
     *
     */
    void loadPoll() {
        loadPollProcessStatus.postValue(ProcessStatus.LOADING);
        getPollUseCase.run(UseCase.None.getInstance(), result ->
                result.fold(this::onLoadPollFailure, this::onLoadPollRight)
        );
    }

    /**
     *
     *
     */
    private void onLoadPollFailure(Failure failure){
        this.failure = failure;
        loadPollProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     *
     */
    private void onLoadPollRight(Poll poll){
        this.poll = poll;
        loadPollProcessStatus.postValue(ProcessStatus.COMPLETED);
        initQuestions();
    }

    /**
     *
     *
     */
    private void initQuestions(){
        currentQuestionIndex = 0;
        currentQuestion.postValue(poll.getQuestions().get(currentQuestionIndex));
        pollProgress.postValue(calculatePollProgress());
    }

    /**
     *
     *
     */
    private int calculatePollProgress(){
        return ((currentQuestionIndex+1)*100)/poll.getQuestions().size();
    }

    /**
     *
     *
     */
    void next(Question.Answer answer) {
        Question question = poll.getQuestions().get(
                poll.getQuestions().indexOf(currentQuestion.getValue())
        );
        question.setSelectedAnswer(answer);
        if(hasNext()) {
            currentQuestion.postValue(poll.getQuestions().get(++currentQuestionIndex));
            pollProgress.postValue(calculatePollProgress());
        } else {
            sendPollProcessStatus.postValue(ProcessStatus.LOADING);
            SendPollUseCase.Params params = new SendPollUseCase.Params(poll);
            sendPollUseCase.run(params, result ->
                    result.fold(this::onSendPollFailure, this::onSendPollRight)
            );
        }
    }

    /**
     *
     *
     */
    private void onSendPollFailure(Failure failure){
        this.failure = failure;
        sendPollProcessStatus.postValue(ProcessStatus.FAILURE);
    }

    /**
     *
     *
     */
    private void onSendPollRight(UseCase.None none){
        sendPollProcessStatus.postValue(ProcessStatus.COMPLETED);
    }

    /**
     *
     *
     */
    boolean hasNext() {
        return currentQuestionIndex != poll.getQuestions().size()-1;
    }

    /**
     *
     *
     */
    LiveData<ProcessStatus> getLoadPollProcessStatus() {
        return loadPollProcessStatus;
    }

    /**
     *
     *
     */
    LiveData<ProcessStatus> getSendPollProcessStatus() {
        return sendPollProcessStatus;
    }

    /**
     *
     *
     */
    LiveData<Question> getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     *
     *
     */
    LiveData<Integer> getPollProgress() {
        return pollProgress;
    }

    /**
     *
     *
     */
    int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     *
     *
     */
    Poll getPoll() {
        return poll;
    }

    /**
     *
     *
     */
    Failure getFailure() {
        return failure;
    }

}
