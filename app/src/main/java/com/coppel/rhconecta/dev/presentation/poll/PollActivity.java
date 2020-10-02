package com.coppel.rhconecta.dev.presentation.poll;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.poll.DaggerPollComponent;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.entity.Poll;
import com.coppel.rhconecta.dev.domain.poll.entity.Question;
import com.coppel.rhconecta.dev.domain.poll.failure.NotPollAvailableFailure;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.visionarios.utils.DialogCustom;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class PollActivity extends AppCompatActivity {

    /* */
    @Inject
    public PollViewModel viewModel;
    private ProgressBar pbLoader;
    private ProgressBar pbPollProgress;
    private TextView tvPollProgress;
    private TextView tvHeader;
    private TextView tvQuestion;
    private RadioGroup rgAnswers;
    private Button btnNext;
    private Button btnFinish;
    // Aux values
    private int totalQuestions;
    private Question.Answer selectedAnswer;


    /**
     *
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        DaggerPollComponent.create().inject(this);
        initViews();
        observeViewModel();
        execute();
    }

    /**
     *
     *
     */
    private void initViews(){
        initToolbar();
        pbLoader = (ProgressBar) findViewById(R.id.pbLoader);
        pbPollProgress = (ProgressBar) findViewById(R.id.pbPollProgress);
        tvPollProgress = (TextView) findViewById(R.id.tvPollProgress);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        rgAnswers = (RadioGroup) findViewById(R.id.rgAnswers);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        // On click listeners
        btnNext.setOnClickListener(this::onAnswerQuestionButtonClickListener);
        btnFinish.setOnClickListener(this::onAnswerQuestionButtonClickListener);
        rgAnswers.setOnCheckedChangeListener(this::onRadioGroupCheckedListener);
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        viewModel.getLoadPollProcessStatus().observe(this, this::onLoadPollProcessStatusObserver);
        viewModel.getCurrentQuestion().observe(this, this::currentQuestionObserver);
        viewModel.getPollProgress().observe(this, this::pollProgressObserver);
        viewModel.getSendPollProcessStatus().observe(this, this::onSendPollProcessStatusObserver);
    }

    /**
     *
     *
     */
    private void execute(){
        viewModel.loadPoll();
    }

    /**
     *
     *
     */
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPoll);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     *
     */
    private void onAnswerQuestionButtonClickListener(View v){
        viewModel.next(selectedAnswer);
    }

    /**
     *
     *
     */
    private void onRadioGroupCheckedListener(RadioGroup group, @IdRes int checkedId){
        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        Question.Answer auxAnswer = new Question.Answer(0, radioButton.getText().toString().trim());
        assert viewModel.getCurrentQuestion().getValue() != null;
        List<Question.Answer> answers = viewModel.getCurrentQuestion().getValue().getAnswers();
        selectedAnswer = answers.get(answers.indexOf(auxAnswer));
        // Buttons visibility
        if(viewModel.hasNext()) btnNext.setVisibility(View.VISIBLE);
        else btnFinish.setVisibility(View.VISIBLE);
    }

    /**
     *
     *
     */
    private void onLoadPollProcessStatusObserver(ProcessStatus processStatus) {
        pbLoader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                pbLoader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Failure failure = viewModel.getFailure();
                String message = getString(R.string.not_available_service);
                SingleActionDialog dialog = new SingleActionDialog(
                        this,
                        getString(R.string.poll_activity_failure_title),
                        message,
                        getString(R.string.poll_activity_failure_action),
                        v -> finish()
                );
                dialog.setCancelable(false);
                dialog.show();
                break;
            case COMPLETED:
                setPoll(viewModel.getPoll());
                break;
        }
    }

    /**
     *
     *
     */
    private void onSendPollProcessStatusObserver(ProcessStatus processStatus) {
        pbLoader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                pbLoader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Failure failure = viewModel.getFailure();
                String message = getString(R.string.not_available_service);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                DialogCustom dialog = new DialogCustom(this, R.layout.dialog_custom_encuesta_success);
                dialog.showDialogActionNoButton(
                        getString(R.string.poll_sent_success_title),
                        getString(R.string.poll_sent_success_content),
                        ignore -> {
                            dialog.CloseDialog();
                            finish();
                        }
                );
                break;
        }
    }

    /**
     *
     *
     */
    private void setPoll(Poll poll){
        totalQuestions = poll.getQuestions().size();
        tvHeader.setText(poll.getTitle());
    }

    /**
     *
     *
     */
    private void currentQuestionObserver(Question question){
        if(question == null) return;
        btnNext.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        tvQuestion.setText(question.getQuestion());
        int steep = viewModel.getCurrentQuestionIndex()+1;
        String pollProgress = getString(R.string.poll_progress, steep, totalQuestions);
        tvPollProgress.setText(pollProgress);
        setAnswers(question.getAnswers());
    }

    /**
     *
     *
     */
    private void setAnswers(List<Question.Answer> answers){
        rgAnswers.removeAllViews();
        for (Question.Answer answer : answers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer.getContent());
            rgAnswers.addView(radioButton);
        }
    }

    /**
     *
     *
     */
    private void pollProgressObserver(Integer progress){
        pbPollProgress.setProgress(progress);
    }

    /**
     *
     *
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
