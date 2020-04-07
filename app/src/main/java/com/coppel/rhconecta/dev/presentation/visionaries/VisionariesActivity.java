package com.coppel.rhconecta.dev.presentation.visionaries;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionariesComponent;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.poll_toolbar.PollToolbarFragment;
import com.coppel.rhconecta.dev.presentation.visionaries.adapter.VisionaryPreviewAdapter;
import com.coppel.rhconecta.dev.presentation.visionary_detail.VisionaryDetailActivity;

import java.util.List;

import javax.inject.Inject;

public class VisionariesActivity extends AppCompatActivity {

    /* */
    @Inject
    public VisionariesViewModel visionariesViewModel;
    /* VIEWS */
    private RecyclerView rvReleases;
    private ProgressBar loader;


    /**
     *
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionaries);
        DaggerVisionariesComponent.create().inject(this);
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
        rvReleases = (RecyclerView) findViewById(R.id.rvVisionaries);
        loader = (ProgressBar) findViewById(R.id.pbLoader);
        rvReleases.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        visionariesViewModel.getLoadVisionariesPreviewsStatus()
                .observe(this, this::getLoadVisionariesPreviewsObserver);
    }

    /**
     *
     *
     */
    private void getLoadVisionariesPreviewsObserver(ProcessStatus processStatus){
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                 Log.e(getClass().getName(), visionariesViewModel.getFailure().toString());
                 Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                 break;
            case COMPLETED:
                setVisionariesPreviews(visionariesViewModel.getVisionariesPreviews());
                break;
        }
    }

    /**
     *
     *
     */
    private void initToolbar(){
        PollToolbarFragment pollToolbarFragment = (PollToolbarFragment)
                getSupportFragmentManager().findFragmentById(R.id.pollToolbarFragment);
        assert pollToolbarFragment != null;
        pollToolbarFragment.toolbar.setTitle(R.string.visionaries_activity_title);
        setSupportActionBar(pollToolbarFragment.toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     *
     */
    private void setVisionariesPreviews(List<VisionaryPreview> visionariesPreviews){
        VisionaryPreviewAdapter adapter =
                new VisionaryPreviewAdapter(visionariesPreviews, this::onVisionaryPreviewClickListener);
        rvReleases.setAdapter(adapter);
        findViewById(R.id.tvNotAvailableVisionaries)
                .setVisibility(visionariesPreviews.isEmpty()? View.VISIBLE : View.GONE);
    }

    /**
     *
     *
     */
    private void onVisionaryPreviewClickListener(VisionaryPreview visionaryPreview){
        Intent intent = new Intent(this, VisionaryDetailActivity.class);
        intent.putExtra(VisionaryDetailActivity.VISIONARY_ID, visionaryPreview.getId());
        intent.putExtra(VisionaryDetailActivity.VISIONARY_IMAGE_PREVIEW, visionaryPreview.getPreviewImage());
        startActivity(intent);
    }

    /**
     *
     *
     */
    private void execute(){
        visionariesViewModel.loadReleasesPreviews();
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
