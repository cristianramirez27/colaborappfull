package com.coppel.rhconecta.dev.presentation.release_detail;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.release.DaggerReleaseComponent;
import com.coppel.rhconecta.dev.domain.release.entity.Release;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

/**
 *
 *
 */
public class ReleaseDetailActivity extends AppCompatActivity {

    /* */
    @Inject
    public ReleaseDetailViewModel releaseDetailViewModel;
    public static String RELEASE_ID = "RELEASE_ID";
    private int releaseId;
    /* VIEWS */
    private ImageView ivHeader;
    private TextView tvHeader;
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvContent;
    private ProgressBar loader;


    /**
     *
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_detail);
        DaggerReleaseComponent.create().inject(this);
        initValues();
        initViews();
        observeViewModel();
        execute();
    }

    /**
     *
     *
     */
    private void initValues(){
        releaseId = getIntent().getIntExtra(RELEASE_ID, -1);
        if(releaseId == -1)
            finish();
    }

    /**
     *
     *
     */
    private void initViews(){
        initToolbar();
        loader = (ProgressBar) findViewById(R.id.pbLoader);
        ivHeader = (ImageView) findViewById(R.id.ivHeader);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);

    }

    /**
     *
     *
     */
    private void observeViewModel(){
        releaseDetailViewModel.getLoadReleaseStatus().observe(this, this::getLoadReleaseObserver);
    }


    /**
     *
     *
     */
    private void getLoadReleaseObserver(ProcessStatus processStatus){
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Log.e(getClass().getName(), releaseDetailViewModel.getFailure().toString());
                Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                setReleaseInformation(releaseDetailViewModel.getRelease());
                break;
        }
    }

    /**
     *
     *
     */
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.release_detail_activity_title);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     *
     *
     */
    private void setReleaseInformation(Release release){
        // Images
        Glide.with(this)
                .load(release.getHeaderImage())
                .error(R.drawable.ic_image_grey_300_48dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivHeader);
        Glide.with(this)
                .load(release.getImage())
                .error(R.drawable.ic_image_grey_300_48dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivImage);
        // Texts
        tvHeader.setText(release.getHeader());
        tvTitle.setText(release.getTitle());
        Spanned content = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                Html.fromHtml(release.getContent(), Html.FROM_HTML_MODE_COMPACT) :
                Html.fromHtml(release.getContent());
        tvContent.setText(content);
    }

    /**
     *
     *
     */
    private void execute(){
        releaseDetailViewModel.loadRelease(releaseId);
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
