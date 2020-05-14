package com.coppel.rhconecta.dev.presentation.releases;

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
import com.coppel.rhconecta.dev.di.release.DaggerReleasesComponent;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.poll_toolbar.PollToolbarFragment;
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
    private RecyclerView rvReleases;
    private ProgressBar loader;


    /**
     *
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releases);
        DaggerReleasesComponent.create().inject(this);
        initViews();
        observeViewModel();
    }

    /**
     *
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        execute();
    }

    /**
     *
     *
     */
    private void initViews(){
        initToolbar();
        rvReleases = (RecyclerView) findViewById(R.id.rvReleases);
        loader = (ProgressBar) findViewById(R.id.pbLoader);
        rvReleases.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     *
     *
     */
    private void initToolbar(){
        PollToolbarFragment pollToolbarFragment = (PollToolbarFragment)
                getSupportFragmentManager().findFragmentById(R.id.pollToolbarFragment);
        assert pollToolbarFragment != null;
        pollToolbarFragment.toolbar.setTitle(R.string.releases_activity_title);
        setSupportActionBar(pollToolbarFragment.toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        releasesViewModel.getLoadReleasesPreviewsStatus().observe(this, this::getLoadReleasesPreviewsObserver);
    }

    /**
     *
     *
     */
    private void getLoadReleasesPreviewsObserver(ProcessStatus processStatus) {
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Log.e(getClass().getName(), releasesViewModel.getFailure().toString());
                Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                setReleasesPreviews(releasesViewModel.getReleasesPreviews());
                break;
        }
    }

    /**
     *
     *
     */
    private void setReleasesPreviews(List<ReleasePreview> releasesPreviews){
        ReleasePreviewAdapter adapter = new ReleasePreviewAdapter(releasesPreviews, this::onReleasePreviewClickListener);
        rvReleases.setAdapter(adapter);
    }

    /**
     *
     *
     */
    private void onReleasePreviewClickListener(ReleasePreview releasePreview) {
        Intent intent = new Intent(this, ReleaseDetailActivity.class);
        intent.putExtra(ReleaseDetailActivity.RELEASE_ID, releasePreview.getId());
        startActivity(intent);
    }

    /**
     *
     *
     */
    private void execute(){
        releasesViewModel.loadReleasesPreviews();
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
