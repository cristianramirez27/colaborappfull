package com.coppel.rhconecta.dev.presentation.poll_toolbar;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.BLOCK_ENCUESTAS;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.MESSAGE_FOR_BLOCK;
import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.YES;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.poll.DaggerPollToolbarComponent;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.poll.PollActivity;
import com.coppel.rhconecta.dev.views.customviews.SurveyInboxView;
import com.coppel.rhconecta.dev.views.customviews.ZendeskInboxView;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.views.utils.ZendeskStatusCallBack;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import javax.inject.Inject;

/**
 *
 *
 */
public class PollToolbarFragment extends Fragment implements DialogFragmentWarning.OnOptionClick, ZendeskStatusCallBack {

    @Inject
    public PollToolbarViewModel viewModel;
    /* */
    public Toolbar toolbar;
    public AppCompatTextView tvTitleToolbar;
    private SurveyInboxView surveyInboxView;
    private ZendeskInboxView zendeskInbox;
    /* */
    private DialogFragmentWarning dialogFragmentWarning;

    ToolbarFragmentCommunication parentCommunication;

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
        if (getActivity() instanceof ToolbarFragmentCommunication) {
            parentCommunication = (ToolbarFragmentCommunication) getContext();
        }
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

    @Override
    public void onResume() {
        super.onResume();
        try{
            CoppelApp.getZendesk().setCallBackAndRefreshStatus(this);
        }catch (Exception exception){
            saveToCrashLitics("onResume() ",exception);
        }
    }

    private void saveToCrashLitics(String someInfo , Exception e){
        FirebaseCrashlytics.getInstance().log(someInfo);
        FirebaseCrashlytics.getInstance().recordException(e);
    }

    /**
     *
     *
     */
    private void initViews(){
        assert getView() != null;
        toolbar = getView().findViewById(R.id.toolbar);
        surveyInboxView = getView().findViewById(R.id.surveyInbox);
        surveyInboxView.setVisibility(View.VISIBLE);
        surveyInboxView.setOnClickListener(this::onSurveyInboxViewClickListener);
        surveyInboxView.setCountMessages(0);

        tvTitleToolbar = getView().findViewById(R.id.titleToolbar);

        zendeskInbox = getView().findViewById(R.id.zendeskInbox);
        zendeskInbox.setOnClickListener(this::onZendeskInboxViewClickListener);
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        viewModel.getGetAvailablePollCountProcessStatus().observe(getViewLifecycleOwner(), this::getAvailablePollCountObserver);
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

    private void onZendeskInboxViewClickListener(View v){
        parentCommunication.clickZendesk();
    }

    /**
     * Zendesk methods
     */
    @Override
    public void enableZendeskFeature() {
        zendeskInbox.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableZendeskFeature() {
        zendeskInbox.setVisibility(View.GONE);
    }

    @Override
    public void zendeskChatOnLine() {
        zendeskInbox.setActive();
    }

    @Override
    public void zendeskChatOutLine() {
        zendeskInbox.setDisable();
    }

    @Override
    public void zendeskSetNotification() {
        zendeskInbox.setNotification();
    }

    @Override
    public void zendeskRemoveNotification() {
        zendeskInbox.removeNotification();
    }

    @Override
    public void zendeskErrorMessage(@NonNull String message) {
        showWarningDialog(message);
    }


    public interface ToolbarFragmentCommunication {
        void clickZendesk();
    }
}
