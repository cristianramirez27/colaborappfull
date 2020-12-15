package com.coppel.rhconecta.dev.presentation.poll_toolbar;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.poll.DaggerPollToolbarComponent;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.poll.PollActivity;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import javax.inject.Inject;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.MESSAGE_FOR_BLOCK;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;

/**
 *
 *
 */
public class PollToolbarFragment extends Fragment implements DialogFragmentWarning.OnOptionClick {

    @Inject
    public PollToolbarViewModel viewModel;
    /* */
    public Toolbar toolbar;
    private SurveyInboxView surveyInboxView;
    /* */
    private DialogFragmentWarning dialogFragmentWarning;

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
        if (isOptionDisabled()) {
            showWarningDialog(
                    AppUtilities.getStringFromSharedPreferences(requireContext(), MESSAGE_FOR_BLOCK)
            );
            return;
        }
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

    /* */
    private boolean isOptionDisabled() {
        return AppUtilities
                .getStringFromSharedPreferences(requireContext(), BLOCK_ENCUESTAS).equals(YES);
    }

    /**
     *
     */
    private void showWarningDialog(String message) {
        dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(getString(R.string.attention), message, getString(R.string.accept));
        dialogFragmentWarning.setOnOptionClick(this);
        dialogFragmentWarning.show(getChildFragmentManager(), DialogFragmentWarning.TAG);
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

    /**
     *
     */
    @Override
    public void onLeftOptionClick() { /* IGNORE */ }

    /**
     *
     */
    @Override
    public void onRightOptionClick() {
        dialogFragmentWarning.close();
    }

}
