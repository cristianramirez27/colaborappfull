package com.coppel.rhconecta.dev.presentation.visionary_full_screen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.MediaController;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.presentation.common.builder.IntentBuilder;
import com.coppel.rhconecta.dev.presentation.common.custom_view.MyVideoView;
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension;

/**
 *
 *
 */
public class VisionaryDetailFullScreenActivity extends AppCompatActivity {

    /* */
    public static final String VIDEO_STREAM = "VIDEO_STREAM";
    /* */
    public static final String POSITION = "POSITION";
    /* */
    private String videoStream;
    /* */
    private int position;
    /* */
    private MyVideoView vvVideo;


    /**
     *
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visionary_detail_full_screen);
        initValues();
        initViews();
    }

    /**
     *
     *
     */
    private void initValues(){
        videoStream = IntentExtension.getStringExtra(getIntent(), VIDEO_STREAM);
        if(videoStream == null)
            finish();
        position = IntentExtension.getIntExtra(getIntent(), POSITION);
    }

    /**
     *
     *
     */
    private void initViews(){
        initVideoView();
        playVideo();
        findViewById(R.id.ivFullScreenBack).setOnClickListener(v -> onBackPressed());
    }


    /**
     *
     *
     */
    private void initVideoView(){
        vvVideo = findViewById(R.id.vvVideo);
        // Media controller settings
        MediaController mediaController = getMyMediaController();
        mediaController.setAnchorView(vvVideo);
        // Video view settings
        vvVideo.setMediaController(mediaController);
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
        vvVideo.seekTo(position);
        vvVideo.start();
    }

    /**
     *
     *
     */
    private MediaController getMyMediaController() {
        return new MediaController(this){
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
        Intent intent = new IntentBuilder(new Intent())
                .putIntExtra(POSITION, vvVideo.getCurrentPosition())
                .build();
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }

}
