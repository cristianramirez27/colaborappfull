package com.coppel.rhconecta.dev.presentation.common.custom_view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyVideoView extends VideoView {

    private IsPlayingListener mListener;

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void pause() {
        super.pause();
        if(mListener != null)
            mListener.isPlaying(false);
    }

    @Override
    public void start() {
        super.start();
        if(mListener != null)
            mListener.isPlaying(true);
    }

    public void setIsPlayingListener(IsPlayingListener listener) {
        mListener = listener;
    }

    public interface IsPlayingListener {
        void isPlaying(boolean isPlaying);
    }


}
