package com.coppel.rhconecta.dev.presentation.release_detail;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
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
    /* */
    private Toolbar toolbar;
    private ImageView ivHeader;
    private TextView tvHeader;
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvContent;
    private ProgressBar loader;


    /**
     *
     *
     * @param savedInstanceState
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loader = (ProgressBar) findViewById(R.id.pbLoader);
        ivHeader = (ImageView) findViewById(R.id.ivHeader);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);
        // TODO: Read title form firebase
        toolbar.setTitle(R.string.app_name);
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        releaseDetailViewModel.getLoadReleaseStatus().observe(this, getLoadReleaseObserver());
    }


    /**
     *
     * @return
     */
    private Observer<ProcessStatus> getLoadReleaseObserver() {
        return processStatus -> {
            loader.setVisibility(View.GONE);
            if(processStatus != null) {
                switch (processStatus) {
                    case LOADING:
                        loader.setVisibility(View.VISIBLE);
                        break;
                    case FAILURE:
                        // TODO: Validate failure instance
                        String message = releaseDetailViewModel.getFailure().toString();
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case COMPLETED:
                        setReleaseInformation(releaseDetailViewModel.getRelease());
                        break;
                }
            }
        };
    }

    /**
     *
     *
     * @param release
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

}
