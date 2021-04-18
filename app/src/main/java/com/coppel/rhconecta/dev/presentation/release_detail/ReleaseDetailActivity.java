package com.coppel.rhconecta.dev.presentation.release_detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.di.release.DaggerReleaseComponent;
import com.coppel.rhconecta.dev.domain.release.entity.Release;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

import io.noties.markwon.Markwon;
import io.noties.markwon.html.HtmlEmptyTagReplacement;
import io.noties.markwon.html.HtmlPlugin;
import io.noties.markwon.html.TagHandlerNoOp;

/**
 *
 */
public class ReleaseDetailActivity extends AppCompatActivity {

    /* */
    @Inject
    public ReleaseDetailViewModel releaseDetailViewModel;
    public static String RELEASE_ID = "RELEASE_ID";
    public static String ACCESS_OPTION = "ACCESS_OPTION";
    private int releaseId;
    private AccessOption accessOption;
    /* VIEWS */
    private ImageView ivHeader;
    private TextView tvHeader;
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvContent;
    private ProgressBar loader;


    /**
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
     */
    private void initValues() {
        releaseId = IntentExtension.getIntExtra(getIntent(), RELEASE_ID);
        accessOption = (AccessOption) IntentExtension.getSerializableExtra(getIntent(), ACCESS_OPTION);
    }

    /**
     *
     */
    private void initViews() {
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
     */
    private void observeViewModel() {
        releaseDetailViewModel.getLoadReleaseStatus().observe(this, this::getLoadReleaseObserver);
    }


    /**
     *
     */
    private void getLoadReleaseObserver(ProcessStatus processStatus) {
        loader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                loader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                setReleaseInformation(releaseDetailViewModel.getRelease());
                break;
        }
    }

    /**
     *
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.release_detail_activity_title);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     */
    private void setReleaseInformation(Release release) {
        Glide.with(this)
                .load(release.getImage())
                .error(R.drawable.ic_image_grey_300_48dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivImage);
        tvTitle.setText(release.getTitle());

        String body = release.getContent();
        Spanned spanned = Html.fromHtml(body);
        tvContent.setText(spanned);
    }

    /**
     *
     */
    private void execute() {
        releaseDetailViewModel.loadRelease(releaseId, accessOption);
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
