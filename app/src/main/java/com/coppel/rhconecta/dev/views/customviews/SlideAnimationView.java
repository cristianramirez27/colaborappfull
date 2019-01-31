package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by faust on 1/29/18.
 */

public class SlideAnimationView extends LinearLayout {

    public SlideAnimationView(Context context) {
        super(context);
    }

    public SlideAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isVisible = false;


    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
