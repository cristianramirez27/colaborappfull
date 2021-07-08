package com.coppel.rhconecta.dev.presentation.visionaries;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.analytics.time.AnalyticsTimeAppCompatActivity;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionariesComponent;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.poll_toolbar.PollToolbarFragment;
import com.coppel.rhconecta.dev.presentation.visionaries.adapter.VisionaryPreviewAdapter;
import com.coppel.rhconecta.dev.presentation.visionary_detail.VisionaryDetailActivity;

import java.util.List;

import javax.inject.Inject;

/* */
public class VisionariesActivity extends AnalyticsTimeAppCompatActivity {

    /* */
    @Inject
    public VisionariesViewModel visionariesViewModel;

    /* VIEWS */
    private RecyclerView rvReleases;
    private ProgressBar loader;

    /* */
    public static String TYPE = "TYPE";
    private VisionaryType visionaryType;

    /* */
    public static String ACCESS_OPTION = "ACCESS_OPTION";
    private AccessOption accessOption;
    private boolean alreadySendAccessOption = false;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionaries);
        DaggerVisionariesComponent.create().inject(this);
        initValues();
        initViews();
        observeViewModel();
    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        execute();
    }

    /**
     *
     */
    private void initValues(){
        visionaryType = (VisionaryType) IntentExtension.getSerializableExtra(getIntent(), TYPE);
        accessOption = (AccessOption) IntentExtension.getSerializableExtra(getIntent(), ACCESS_OPTION);
    }

    /**
     *
     */
    private void initViews(){
        initToolbar();
        rvReleases = findViewById(R.id.rvVisionaries);
        loader = findViewById(R.id.pbLoader);
        rvReleases.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     *
     */
    private void observeViewModel(){
        visionariesViewModel.getLoadVisionariesPreviewsStatus()
                .observe(this, this::getLoadVisionariesPreviewsObserver);
    }

    /**
     *
     */
    private void execute(){
        if (alreadySendAccessOption)
            accessOption = null;
        alreadySendAccessOption = true;
        visionariesViewModel.loadReleasesPreviews(visionaryType, accessOption);
    }

    /**
     *
     */
    private void getLoadVisionariesPreviewsObserver(ProcessStatus processStatus){
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                SingleActionDialog dialog = new SingleActionDialog(
                        this,
                        getString(R.string.visionaries_failure_default_title),
                        getString(R.string.visionaries_failure_default_content),
                        getString(R.string.visionaries_failure_default_action),
                        v -> finish()
                );
                dialog.setCancelable(false);
                dialog.show();
                break;
            case COMPLETED:
                setVisionariesPreviews(visionariesViewModel.getVisionariesPreviews());
                break;
        }
    }

    /**
     *
     */
    private void initToolbar(){
        PollToolbarFragment pollToolbarFragment = (PollToolbarFragment)
                getSupportFragmentManager().findFragmentById(R.id.pollToolbarFragment);
        assert pollToolbarFragment != null;
        int titleResource = visionaryType == VisionaryType.VISIONARIES ?
                R.string.visionaries_title : R.string.collaborator_at_home_title;
        pollToolbarFragment.toolbar.setTitle(titleResource);
        setSupportActionBar(pollToolbarFragment.toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     */
    private void setVisionariesPreviews(List<VisionaryPreview> visionariesPreviews){
        VisionaryPreviewAdapter adapter =
                new VisionaryPreviewAdapter(visionariesPreviews, this::onVisionaryPreviewClickListener);
        rvReleases.setAdapter(adapter);
    }

    /**
     *
     */
    private void onVisionaryPreviewClickListener(VisionaryPreview visionaryPreview){
        Intent intent = new IntentBuilder(new Intent(this, VisionaryDetailActivity.class))
                .putStringExtra(VisionaryDetailActivity.VISIONARY_ID, visionaryPreview.getId())
                .putStringExtra(VisionaryDetailActivity.VISIONARY_IMAGE_PREVIEW, visionaryPreview.getPreviewImage())
                .putSerializableExtra(VisionaryDetailActivity.VISIONARY_TYPE, visionaryType)
                .build();
        startActivity(intent);
    }

    /**
     *
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
