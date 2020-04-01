package com.coppel.rhconecta.dev.presentation.visionary_detail;

import android.arch.lifecycle.MutableLiveData;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionaryComponent;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.presentation.common.custom_view.MyVideoView;
import com.coppel.rhconecta.dev.presentation.common.listener.OnMyVimeoExtractionListener;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;

import javax.inject.Inject;

import uk.breedrapps.vimeoextractor.OnVimeoExtractionListener;
import uk.breedrapps.vimeoextractor.VimeoExtractor;

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
    private ProgressBar pbVideoLoader;
    private ImageView ivVideoPreview;
    private MyVideoView vvVideo;
    private ImageView ivPlayButton;
    private ImageView ivFullScreenButton;
    // Like and dislike buttons
    private ImageView ivLike;
    private ImageView ivDislike;
    // Visionary views
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvNumberOfViews;
    private TextView tvContent;
    private String videoStream;
    private MutableLiveData<Boolean> isVideoPlaying;

    /**
     *
     *
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
        pbVideoLoader = (ProgressBar) findViewById(R.id.pbVideoLoader);
        ivVideoPreview = (ImageView) findViewById(R.id.ivVideoPreview);
        vvVideo = (MyVideoView) findViewById(R.id.vvVideo);
        ivPlayButton = (ImageView) findViewById(R.id.ivPlayButton);
        ivFullScreenButton = (ImageView) findViewById(R.id.ivFullScreenButton);
        ivLike = (ImageView) findViewById(R.id.ivLike);
        ivDislike = (ImageView) findViewById(R.id.ivDislike);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvNumberOfViews = (TextView) findViewById(R.id.tvNumberOfViews);
        tvContent = (TextView) findViewById(R.id.tvContent);
        // Set simples values
        // TODO: Read title form firebase
        toolbar.setTitle(R.string.app_name);
        // Video view configuration
        initVideoView();
        // Set image preview
        Glide.with(this)
                .load(visionaryVideoPreview)
                .into(ivVideoPreview);
        // Set click listeners
        ivPlayButton.setOnClickListener(this::onPlayButtonClickListener);
        ivFullScreenButton.setOnClickListener(this::navigateToFullScreenVideo);
        // Observe live data
        isVideoPlaying = new MutableLiveData<>();
        isVideoPlaying.observe(this, this::setPlayingVideoViewsProperties);
    }

    /**
     *
     *
     */
    private void observeViewModel(){
        viewModel.getLoadVisionaryProcessStatus().observe(this, this::loadVisionaryObserver);
        viewModel.getUpdateVisionaryStatusProcessStatus().observe(this, this::updateVisionaryStatusObserver);
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
    private void initVideoView(){
        // Media controller settings
        MediaController mediaController = getMyMediaController();
        mediaController.setAnchorView(vvVideo);
        // Video view settings
        vvVideo.setMediaController(mediaController);
        // Video view listeners
        vvVideo.setOnErrorListener(this::onMediaPlayerErrorListener);
        vvVideo.setIsPlayingListener(this::onMediaPlayerIsPlayingListener);
        vvVideo.setOnCompletionListener(this::onMediaPlayerCompletionListener);
    }

    /**
     *
     *
     */
    private void onPlayButtonClickListener(View v){
        if(videoStream != null) {
            playVideo();
            ivVideoPreview.setVisibility(View.GONE);
            ivPlayButton.setVisibility(View.GONE);
            pbVideoLoader.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     *
     */
    private void loadVisionaryObserver(ProcessStatus processStatus) {
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
    }

    /**
     *
     *
     */
    private void updateVisionaryStatusObserver(ProcessStatus processStatus) {
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
                setVisionaryStatusViews(viewModel.getVisionary().getStatus());
                break;
        }
    }

    /**
     *
     *
     */
    private void setVisionaryViews(Visionary visionary){
        setVisionaryStatusViews(visionary.getStatus());
        // Vimeo video
        String[] split = visionary.getVideo().split("/");
        String vimeoId = split[split.length-1];
        extractVideoStream(vimeoId);
        // Fill views
        tvTitle.setText(visionary.getTitle());
        tvDate.setText(visionary.getDate());
        String numberOfViews = getString(R.string.number_of_views, visionary.getNumberOfViews());
        tvNumberOfViews.setText(numberOfViews);
        tvContent.setText(visionary.getContent());
    }

    /**
     *
     *
     */
    private void setVisionaryStatusViews(Visionary.Status status){
        switch (status){
            case UNKNOWN:
            case EMPTY:
                ivLike.setOnClickListener(this::onLikeButtonClickListener);
                ivDislike.setOnClickListener(this::onDislikeButtonClickListener);
                break;
            case LIKED:
                ivLike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_thumb_up_36dp));
                ivLike.setOnClickListener(null);
                ivDislike.setOnClickListener(null);
                break;
            case DISLIKED:
                ivDislike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_thumb_down_red_36dp));
                ivLike.setOnClickListener(null);
                ivDislike.setOnClickListener(null);
                break;
        }
    }

    /**
     *
     *
     */
    private void onLikeButtonClickListener(View v){
        viewModel.updateVisionaryStatus(Visionary.Status.LIKED);
    }

    /**
     *
     *
     */
    private void onDislikeButtonClickListener(View v){
        viewModel.updateVisionaryStatus(Visionary.Status.DISLIKED);
    }

    /**
     *
     *
     */
    private void extractVideoStream(String vimeoId){
        VimeoExtractor
                .getInstance()
                .fetchVideoWithIdentifier(
                        vimeoId,
                        null,
                        createOnVimeoExtractionCompleted()
                );
    }

    /**
     *
     *
     */
    private OnVimeoExtractionListener createOnVimeoExtractionCompleted() {
        return new OnMyVimeoExtractionListener((throwable, video) -> {
            if (video != null)
                videoStream = video.getStreams().get("360p");
            if (throwable != null)
                Toast.makeText(VisionaryDetailActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Called when media player expose an error
     */
    private boolean onMediaPlayerErrorListener(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, R.string.defautl_media_player_error, Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Called when media player is paused or played
     */
    private void onMediaPlayerIsPlayingListener(boolean isPlaying){
        isVideoPlaying.postValue(isPlaying);
    }

    /**
     * Called when media player resource are completed
     */
    private void onMediaPlayerCompletionListener(MediaPlayer mediaPlayer){
        // TODO: Implementation
    }

    /**
     *
     *
     */
    private void playVideo() {
        vvVideo.setVideoURI(Uri.parse(videoStream));
        vvVideo.setOnPreparedListener(this::onMediaPlayerPreparedListener);
    }

    /**
     * Called when media player are ready to play the video
     */
    private void onMediaPlayerPreparedListener(MediaPlayer mediaPlayer) {
        vvVideo.requestFocus();
        vvVideo.start();
    }

    /**
     *
     *
     */
    private void navigateToFullScreenVideo(View v) {
        vvVideo.pause();
        // TODO: Implementation
    }

    /**
     *
     *
     */
    private void setPlayingVideoViewsProperties(boolean isVideoPlaying){
        if(isVideoPlaying)
            pbVideoLoader.setVisibility(View.GONE);
    }

    /**
     *
     *
     */
    private MediaController getMyMediaController() {
        return new MediaController(VisionaryDetailActivity.this){
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    ((AppCompatActivity) getContext()).onBackPressed();
                    return true;
                }
                return super.dispatchKeyEvent(event);
            }
        };
    }

    /**
     *
     *
     */
    @Override
    public void onBackPressed() {
        vvVideo.pause();
        finish();
    }
}
