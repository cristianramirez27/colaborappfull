package com.coppel.rhconecta.dev.presentation.poll_toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.poll.DaggerPollToolbarComponent;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.poll.PollActivity;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;

import javax.inject.Inject;

/**
 *
 *
 */
public class PollToolbarFragment extends Fragment {

    @Inject
    public PollToolbarViewModel viewModel;
    /* */
    public Toolbar toolbar;
    private SurveyInboxView surveyInboxView;

    /**
     *
     *
     */
    public PollToolbarFragment() {
        // Required empty public constructor
    }

    /**
     *
     *
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_poll_toolbar, container, false);
    }

    /**
     *
     *
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerPollToolbarComponent.create().inject(this);
        initViews();
        observeViewModel();
    }

    /**
     *
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        execute();
    }

    /**
     *
     *
     */
    private void initViews(){
        assert getView() != null;
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        surveyInboxView = (SurveyInboxView) getView().findViewById(R.id.surveyInboxView);
        surveyInboxView.setOnClickListener(this::onSurveyInboxViewClickListener);
        surveyInboxView.setCountMessages(0);
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        viewModel.getGetAvailablePollCountProcessStatus().observe(this, this::getAvailablePollCountObserver);
    }

    /**
     *
     *
     */
    private void execute(){
        viewModel.loadAvailablePollCount();
    }

    /**
     *
     *
     */
    private void onSurveyInboxViewClickListener(View v){
        if(viewModel.getAvailableCount() > 0){
            Intent intent = new Intent(getContext(), PollActivity.class);
            startActivity(intent);
        } else {
            SingleActionDialog dialog = new SingleActionDialog(
                    getActivity(),
                    getString(R.string.not_available_poll_title),
                    getString(R.string.not_available_poll_content),
                    getString(R.string.not_available_poll_action),
                    null
            );
            dialog.show();
        }
    }

    /**
     *
     *
     */
    private void getAvailablePollCountObserver(ProcessStatus processStatus){
        switch (processStatus) {
            case LOADING:
                break;
            case FAILURE:
                break;
            case COMPLETED:
                surveyInboxView.setCountMessages(viewModel.getAvailableCount());
                break;
        }
    }

}
