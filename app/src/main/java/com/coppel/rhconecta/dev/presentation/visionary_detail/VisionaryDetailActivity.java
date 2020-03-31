package com.coppel.rhconecta.dev.presentation.visionary_detail;

import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionaryComponent;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

public class VisionaryDetailActivity extends AppCompatActivity {

    /* */
    public static String VISIONARY_ID = "VISIONARY_ID";
    public static String VISIONARY_IMAGE_PREVIEW = "VISIONARY_IMAGE_PREVIEW";
    /* */
    @Inject
    public VisionaryDetailViewModel viewModel;
    private String visionaryId;
    private String visionaryVideoPreview;
    /* VIEWS */
    /* */
    private Toolbar toolbar;
    private ProgressBar pbLoader;
    private ImageView ivVideoPreview;
    private VideoView vvVideo;
    private ImageView ivPlayButton;
    private ImageView ivFullScreenButton;
    // Visionary views
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvNumberOfViews;
    private TextView tvContent;

    /**
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionary_detail);
        DaggerVisionaryComponent.create().inject(this);
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
        visionaryId = getIntent().getStringExtra(VISIONARY_ID);
        visionaryVideoPreview = getIntent().getStringExtra(VISIONARY_IMAGE_PREVIEW);
    }

    /**
     *
     *
     */
    private void initViews(){
        // Find views by id's.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pbLoader = (ProgressBar) findViewById(R.id.pbLoader);
        ivVideoPreview = (ImageView) findViewById(R.id.ivVideoPreview);
        vvVideo = (VideoView) findViewById(R.id.vvVideo);
        ivPlayButton = (ImageView) findViewById(R.id.ivPlayButton);
        ivFullScreenButton = (ImageView) findViewById(R.id.ivFullScreenButton);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvNumberOfViews = (TextView) findViewById(R.id.tvNumberOfViews);
        tvContent = (TextView) findViewById(R.id.tvContent);
        // Set simples values
        // TODO: Read title form firebase
        toolbar.setTitle(R.string.app_name);
        // Set image preview
        Glide.with(this)
                .load(visionaryVideoPreview)
                .into(ivVideoPreview);
        // Set click listeners
        vvVideo.setOnClickListener(onVideoClickListener);
        ivPlayButton.setOnClickListener(onVideoClickListener);
    }

    /* */
    private View.OnClickListener onVideoClickListener = view -> {
        vvVideo.start();
        ivVideoPreview.setVisibility(View.GONE);
    };

    /**
     *
     *
     */
    private void observeViewModel(){
        viewModel.getLoadVisionaryStatus().observe(this, loadVisionaryObserver);
    }

    /* */
    private Observer<ProcessStatus> loadVisionaryObserver = processStatus -> {
        assert processStatus != null;
        pbLoader.setVisibility(View.GONE);
        switch (processStatus){
            case LOADING:
                pbLoader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                // TODO: Validate failure instance
                String message = viewModel.getFailure().toString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                setVisionaryViews(viewModel.getVisionary());
                break;
        }
    };


    /**
     *
     * @param visionary
     */
    private void setVisionaryViews(Visionary visionary){
        initVideoView(visionary.getVideo());
        tvTitle.setText(visionary.getTitle());
        tvDate.setText(visionary.getDate());
        String numberOfViews = getString(R.string.number_of_views, visionary.getNumberOfViews());
        tvNumberOfViews.setText(numberOfViews);
        tvContent.setText(visionary.getContent());
    }

    /**
     *
     * @param videoURL
     */
    private void initVideoView(String videoURL){
        Uri videoUri = Uri.parse(videoURL);
        vvVideo.setVideoURI(videoUri);
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(vvVideo);
        vvVideo.setMediaController(controller);
    }

    /**
     *
     *
     */
    private void execute(){
        viewModel.loadVisionary(visionaryId);
    }

    /**
     *
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
        vvVideo.stopPlayback();
    }

    /**
     *
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            vvVideo.pause();
    }
}
