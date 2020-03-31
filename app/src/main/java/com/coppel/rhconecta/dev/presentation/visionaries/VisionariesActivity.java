package com.coppel.rhconecta.dev.presentation.visionaries;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionariesComponent;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.visionaries.adapter.VisionaryPreviewAdapter;
import com.coppel.rhconecta.dev.presentation.visionary_detail.VisionaryDetailActivity;

import java.util.List;

import javax.inject.Inject;

public class VisionariesActivity extends AppCompatActivity {

    /* */
    @Inject
    public VisionariesViewModel visionariesViewModel;
    /* VIEWS */
    /* */
    private Toolbar toolbar;
    private RecyclerView rvReleases;
    private ProgressBar loader;


    /**
     *
     * @param savedInstanceState
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvReleases = (RecyclerView) findViewById(R.id.rvVisionaries);
        loader = (ProgressBar) findViewById(R.id.pbLoader);
        // TODO: Read title form firebase
        toolbar.setTitle(R.string.app_name);
        rvReleases.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        visionariesViewModel.getLoadVisionariesPreviewsStatus().observe(this, getLoadVisionariesPreviewsObserver());
    }

    /**
     *
     * @return
     */
    private Observer<ProcessStatus> getLoadVisionariesPreviewsObserver() {
        return processStatus -> {
            loader.setVisibility(View.GONE);
            if(processStatus != null) {
                switch (processStatus) {
                    case LOADING:
                        loader.setVisibility(View.VISIBLE);
                        break;
                    case FAILURE:
                        // TODO: Validate failure instance
                        String message = visionariesViewModel.getFailure().toString();
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case COMPLETED:
                        setVisionariesPreviews(visionariesViewModel.getVisionariesPreviews());
                        break;
                }
            }
        };
    }

    /**
     *
     * @param visionariesPreviews
     */
    private void setVisionariesPreviews(List<VisionaryPreview> visionariesPreviews){
        VisionaryPreviewAdapter adapter = new VisionaryPreviewAdapter(visionariesPreviews, onVisionaryPreviewClickListener);
        rvReleases.setAdapter(adapter);
    }

    /* */
    private VisionaryPreviewAdapter.OnVisionaryPreviewClickListener onVisionaryPreviewClickListener = visionaryPreview -> {
        Intent intent = new Intent(this, VisionaryDetailActivity.class);
        intent.putExtra(VisionaryDetailActivity.VISIONARY_ID, visionaryPreview.getId());
        intent.putExtra(VisionaryDetailActivity.VISIONARY_IMAGE_PREVIEW, visionaryPreview.getPreviewImage());
        startActivity(intent);
    };

    /**
     *
     *
     */
    private void execute(){
        visionariesViewModel.loadReleasesPreviews();
    }

}
