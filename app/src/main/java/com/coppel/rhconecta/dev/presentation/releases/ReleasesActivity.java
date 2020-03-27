package com.coppel.rhconecta.dev.presentation.releases;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.DaggerReleasesComponent;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.release_detail.ReleaseDetailActivity;
import com.coppel.rhconecta.dev.presentation.releases.adapter.ReleasePreviewAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class ReleasesActivity extends AppCompatActivity {

    /* */
    @Inject
    public ReleasesViewModel releasesViewModel;
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
        setContentView(R.layout.activity_releases);
        DaggerReleasesComponent.create().inject(this);
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
        rvReleases = (RecyclerView) findViewById(R.id.rvReleases);
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
        releasesViewModel.getLoadReleasesPreviewsStatus().observe(this, getLoadReleasesPreviewsObserver());
    }

    /**
     *
     * @return
     */
    private Observer<ProcessStatus> getLoadReleasesPreviewsObserver() {
        return processStatus -> {
            loader.setVisibility(View.GONE);
            if(processStatus != null) {
                switch (processStatus) {
                    case LOADING:
                        loader.setVisibility(View.VISIBLE);
                        break;
                    case FAILURE:
                        // TODO: Validate failure instance
                        String message = releasesViewModel.getFailure().toString();
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case COMPLETED:
                        setReleasesPreviews(releasesViewModel.getReleasesPreviews());
                        break;
                }
            }
        };
    }

    /**
     *
     * @param releasesPreviews
     */
    private void setReleasesPreviews(List<ReleasePreview> releasesPreviews){
        ReleasePreviewAdapter adapter = new ReleasePreviewAdapter(releasesPreviews, onReleasePreviewClickListener);
        rvReleases.setAdapter(adapter);
    }

    /* */
    private ReleasePreviewAdapter.OnReleasePreviewClickListener onReleasePreviewClickListener = releasePreview -> {
        Intent intent = new Intent(this, ReleaseDetailActivity.class);
        intent.putExtra(ReleaseDetailActivity.RELEASE_ID, releasePreview.getId());
        startActivity(intent);
    };

    /**
     *
     *
     */
    private void execute(){
        releasesViewModel.loadReleasesPreviews();
    }

}
