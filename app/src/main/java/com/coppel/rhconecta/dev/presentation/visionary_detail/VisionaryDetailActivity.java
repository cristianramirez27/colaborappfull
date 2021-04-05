package com.coppel.rhconecta.dev.presentation.visionary_detail;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.di.visionary.DaggerVisionaryComponent;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.custom_view.MyVideoView;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;
import com.coppel.rhconecta.dev.presentation.common.listener.OnMyVimeoExtractionListener;
import com.coppel.rhconecta.dev.presentation.common.view_model.ProcessStatus;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;
import com.coppel.rhconecta.dev.presentation.visionary_detail.visionary_rate.VisionaryRateActivity;
import com.coppel.rhconecta.dev.presentation.visionary_full_screen.VisionaryDetailFullScreenActivity;

import javax.inject.Inject;

import uk.breedrapps.vimeoextractor.OnVimeoExtractionListener;
import uk.breedrapps.vimeoextractor.VimeoExtractor;

/**
 *
 */
public class VisionaryDetailActivity extends AppCompatActivity {

    /* */
    public static String VISIONARY_ID = "VISIONARY_ID";
    public static String VISIONARY_IMAGE_PREVIEW = "VISIONARY_IMAGE_PREVIEW";
    public static String VISIONARY_TYPE = "VISIONARY_TYPE";
    public static String ACCESS_OPTION = "ACCESS_OPTION";
    private final int REQUEST_CODE_FULL_SCREEN = 1;
    /* */
    @Inject
    public VisionaryDetailViewModel viewModel;
    private String visionaryId;
    private String visionaryVideoPreview;
    private VisionaryType visionaryType;
    private AccessOption accessOption;
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
    private String videoThrowableMessage;
    private MutableLiveData<Boolean> isVideoPlaying;

    /**
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
    private void initValues() {
        visionaryId = IntentExtension.getStringExtra(getIntent(), VISIONARY_ID);
        visionaryVideoPreview = IntentExtension.getStringExtra(getIntent(), VISIONARY_IMAGE_PREVIEW);
        visionaryType = (VisionaryType) IntentExtension.getSerializableExtra(getIntent(), VISIONARY_TYPE);
        accessOption = (AccessOption) IntentExtension.getSerializableExtra(getIntent(), ACCESS_OPTION);
    }

    /**
     *
     */
    private void initViews() {
        initToolbar();
        pbLoader = findViewById(R.id.pbLoader);
        pbVideoLoader = findViewById(R.id.pbVideoLoader);
        ivVideoPreview = findViewById(R.id.ivVideoPreview);
        vvVideo = findViewById(R.id.vvVideo);
        ivPlayButton = findViewById(R.id.ivPlayButton);
        ivFullScreenButton = findViewById(R.id.ivFullScreenButton);
        ivLike = findViewById(R.id.ivLike);
        ivDislike = findViewById(R.id.ivDislike);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvNumberOfViews = findViewById(R.id.tvNumberOfViews);
        tvContent = findViewById(R.id.tvContent);
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
     */
    private void observeViewModel() {
        viewModel.getLoadVisionaryProcessStatus().observe(this, this::loadVisionaryObserver);
        viewModel.getUpdateVisionaryStatusProcessStatus().observe(this, this::updateVisionaryStatusObserver);
        viewModel.getSendVisionaryActionProcessStatus().observe(this, this::sendVisionaryActionObserver);
    }

    /**
     *
     */
    private void execute() {
        viewModel.loadVisionary(visionaryType, visionaryId, accessOption);
    }

    /**
     *
     */
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        int titleResource = visionaryType == VisionaryType.VISIONARIES ?
                R.string.visionary_details_activity_title : R.string.visionary_at_home_details_activity_title;
        toolbar.setTitle(titleResource);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     */
    private void initVideoView() {
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
     */
    private void onPlayButtonClickListener(View v) {
        if (videoStream != null) {
            playVideo();
            viewModel.sendVisionaryAction(visionaryType, 1);
            ivVideoPreview.setVisibility(View.GONE);
            ivPlayButton.setVisibility(View.GONE);
            pbVideoLoader.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     */
    private void loadVisionaryObserver(ProcessStatus processStatus) {
        pbLoader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                pbLoader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                setVisionaryViews(viewModel.getVisionary());
                break;
        }
    }

    /**
     *
     */
    private void updateVisionaryStatusObserver(ProcessStatus processStatus) {
        pbLoader.setVisibility(View.GONE);
        switch (processStatus) {
            case LOADING:
                pbLoader.setVisibility(View.VISIBLE);
                break;
            case FAILURE:
                Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
                break;
            case COMPLETED:
                setVisionaryStatusViews(viewModel.getVisionary().getRateStatus());
                break;
        }
    }

    /**
     *
     */
    private void setVisionaryViews(Visionary visionary) {
        if (visionary.getId() == null) {
            String invalidData = getString(R.string.invalid_data_loaded);
            Toast.makeText(this, invalidData, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setVisionaryStatusViews(visionary.getRateStatus());
        // Vimeo video
        if (visionary.getVideo() != null) {
            String[] split = visionary.getVideo().split("/");
            String vimeoId = split[split.length - 1];
            extractVideoStream(vimeoId);
        }
        // Fill views
        tvTitle.setText(visionary.getTitle());
        tvDate.setText(visionary.getDate());
        String numberOfViews = getString(R.string.number_of_views, visionary.getNumberOfViews());
        tvNumberOfViews.setText(numberOfViews);
        tvContent.setText(visionary.getContent());
    }

    /**
     *
     */
    private void setVisionaryStatusViews(Visionary.RateStatus status) {
        switch (status) {
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
     */
    private void onLikeButtonClickListener(View v) {
        if (viewModel.getVisionary().getVisionaryRate().getOptionsWhenLike().isEmpty())
            viewModel.updateVisionaryStatus(visionaryType, Visionary.RateStatus.LIKED);
        else {
            Intent intent = buildIntentToRateActivityFromRateStatus(Visionary.RateStatus.LIKED);
            startActivity(intent);
        }
    }

    /**
     *
     */
    private void onDislikeButtonClickListener(View v) {
        if (viewModel.getVisionary().getVisionaryRate().getOptionsWhenDislike().isEmpty())
            viewModel.updateVisionaryStatus(visionaryType, Visionary.RateStatus.DISLIKED);
        else {
            Intent intent = buildIntentToRateActivityFromRateStatus(Visionary.RateStatus.DISLIKED);
            startActivity(intent);
        }
    }

    /* */
    private Intent buildIntentToRateActivityFromRateStatus(Visionary.RateStatus rateStatus) {
        return new IntentBuilder(new Intent(this, VisionaryRateActivity.class))
                .putSerializableExtra(VisionaryRateActivity.VISIONARY, viewModel.getVisionary())
                .putSerializableExtra(VisionaryRateActivity.VISIONARY_TYPE, visionaryType)
                .putSerializableExtra(VisionaryRateActivity.VISIONARY_RATE_STATUS, rateStatus)
                .build();
    }

    /**
     *
     */
    private void extractVideoStream(String vimeoId) {
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
     */
    private OnVimeoExtractionListener createOnVimeoExtractionCompleted() {
        return new OnMyVimeoExtractionListener((throwable, video) -> {
            if (video != null)
                videoStream = video.getStreams().get("360p");
            if (throwable != null) {
                String cause = throwable.getLocalizedMessage();
                if (cause == null)
                    videoThrowableMessage = getString(R.string.default_video_decoder_failure);
                else
                    videoThrowableMessage = getString(R.string.video_decoder_failure, cause);
                manageVideoDecoderFailure();
            }
        });
    }

    /**
     *
     */
    private void manageVideoDecoderFailure() {
        if (videoThrowableMessage != null) {
            runOnUiThread(() -> {
                Toast.makeText(this, videoThrowableMessage, Toast.LENGTH_LONG).show();
                ivPlayButton.setVisibility(View.GONE);
                ivFullScreenButton.setVisibility(View.GONE);
            });
        }
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
    private void onMediaPlayerIsPlayingListener(boolean isPlaying) {
        isVideoPlaying.postValue(isPlaying);
    }

    /**
     * Called when media player resource are completed
     */
    private void onMediaPlayerCompletionListener(MediaPlayer mediaPlayer) {
        // TODO: Implementation
    }

    /**
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

    /** */
    private void sendVisionaryActionObserver(ProcessStatus processStatus) {
        if (processStatus == ProcessStatus.FAILURE)
            Toast.makeText(this, R.string.default_server_error, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     */
    private void navigateToFullScreenVideo(View v) {
        vvVideo.pause();
        Intent intent = new IntentBuilder(new Intent(this, VisionaryDetailFullScreenActivity.class))
                .putStringExtra(VisionaryDetailFullScreenActivity.VIDEO_STREAM, videoStream)
                .putIntExtra(VisionaryDetailFullScreenActivity.POSITION, vvVideo.getCurrentPosition())
                .build();
        startActivityForResult(intent, REQUEST_CODE_FULL_SCREEN);
    }

    /**
     *
     */
    private void setPlayingVideoViewsProperties(boolean isVideoPlaying) {
        if (isVideoPlaying)
            pbVideoLoader.setVisibility(View.GONE);
    }

    /**
     *
     */
    private MediaController getMyMediaController() {
        return new MediaController(this) {
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
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        vvVideo.pause();
        finish();
    }

    /**
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FULL_SCREEN && resultCode == RESULT_OK) {
            if (data != null) {
                int position = IntentExtension.getIntExtra(data, VisionaryDetailFullScreenActivity.POSITION);
                playVideo();
                vvVideo.pause();
                vvVideo.seekTo(position);
                ivVideoPreview.setVisibility(View.GONE);
                ivPlayButton.setVisibility(View.GONE);
            }
        }
    }

}
