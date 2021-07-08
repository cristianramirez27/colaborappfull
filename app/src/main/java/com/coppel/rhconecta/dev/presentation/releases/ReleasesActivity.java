package com.coppel.rhconecta.dev.presentation.releases;

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
import com.coppel.rhconecta.dev.di.release.DaggerReleasesComponent;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.dialog.SingleActionDialog;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.poll_toolbar.PollToolbarFragment;
import com.coppel.rhconecta.dev.presentation.release_detail.ReleaseDetailActivity;
import com.coppel.rhconecta.dev.presentation.releases.adapter.ReleasePreviewAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class ReleasesActivity extends AnalyticsTimeAppCompatActivity {

    /* */
    @Inject
    public ReleasesViewModel releasesViewModel;
    /* VIEWS */
    private RecyclerView rvReleases;
    private ProgressBar loader;
    /* VALUES */
    public static String ACCESS_OPTION = "ACCESS_OPTION";
    private AccessOption accessOption;
    private boolean alreadySendAccessOption = false;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releases);
        DaggerReleasesComponent.create().inject(this);
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
        accessOption = (AccessOption) IntentExtension.getSerializableExtra(getIntent(), ACCESS_OPTION);
    }

    /**
     *
     */
    private void initViews() {
        initToolbar();
        rvReleases = (RecyclerView) findViewById(R.id.rvReleases);
        loader = (ProgressBar) findViewById(R.id.pbLoader);
        rvReleases.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     *
     */
    private void initToolbar() {
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
     */
    private void observeViewModel() {
        releasesViewModel.getLoadReleasesPreviewsStatus().observe(this, this::getLoadReleasesPreviewsObserver);
    }

    /**
     *
     */
    private void getLoadReleasesPreviewsObserver(ProcessStatus processStatus) {
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                SingleActionDialog dialog = new SingleActionDialog(
                        this,
                        getString(R.string.releases_failure_default_title),
                        getString(R.string.releases_failure_default_content),
                        getString(R.string.releases_failure_default_action),
                        v -> finish()
                );
                dialog.setCancelable(false);
                dialog.show();
                break;
            case COMPLETED:
                setReleasesPreviews(releasesViewModel.getReleasesPreviews());
                break;
        }
    }

    /**
     *
     */
    private void setReleasesPreviews(List<ReleasePreview> releasesPreviews) {
        ReleasePreviewAdapter adapter = new ReleasePreviewAdapter(releasesPreviews, this::onReleasePreviewClickListener);
        rvReleases.setAdapter(adapter);
    }

    /**
     *
     */
    private void onReleasePreviewClickListener(ReleasePreview releasePreview) {
        Intent intent = new IntentBuilder(new Intent(this, ReleaseDetailActivity.class))
                .putIntExtra(ReleaseDetailActivity.RELEASE_ID, releasePreview.getId())
                .build();
        startActivity(intent);
    }

    /**
     *
     */
    private void execute() {
        if (alreadySendAccessOption)
            accessOption = null;
        alreadySendAccessOption = true;
        releasesViewModel.loadReleasesPreviews(accessOption);
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
