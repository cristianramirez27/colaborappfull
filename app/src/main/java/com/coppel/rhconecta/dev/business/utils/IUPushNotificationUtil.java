package com.coppel.rhconecta.dev.business.utils;

import android.os.Message;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.coppel.rhconecta.dev.business.Configuration.PushSnackSingleton;
import com.coppel.rhconecta.dev.business.Enums.PushType;
import com.coppel.rhconecta.dev.business.models.PushData;
import com.coppel.rhconecta.dev.views.customviews.SlideAnimationView;

import static android.view.View.GONE;

/**
 * Created by faust on 1/26/18.
 */

public class IUPushNotificationUtil {

    // slide the view from below itself to the current position
    public static void slideUp(SlideAnimationView view){
        if(view.isVisible()){
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    0,  // fromYDelta
                    -view.getHeight());                // toYDelta
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.setClickable(false);
        view.setEnabled(false);
        view.setVisibility(GONE);
        view.setVisible(false);
    }

    // slide the view from its current position to below itself
    public static void slideDown(SlideAnimationView view){

        if(!view.isVisible()){
            view.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    -view.getHeight(),                 // fromYDelta
                    0); // toYDelta
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
        }

        view.setClickable(true);
        view.setEnabled(true);
        view.setVisible(true);

    }


    public static void showPushNotificationIU(PushType pushType, PushData pushData){
        Message message = PushSnackSingleton.getInstance().getmHandler().obtainMessage(pushType.getId(), pushData);
        message.sendToTarget();
    }


}
