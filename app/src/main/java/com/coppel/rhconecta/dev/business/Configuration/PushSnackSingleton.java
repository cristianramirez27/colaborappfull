package com.coppel.rhconecta.dev.business.Configuration;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.Enums.PushType;
import com.coppel.rhconecta.dev.business.models.PushData;
import com.coppel.rhconecta.dev.business.utils.NavigationUtil;
import com.coppel.rhconecta.dev.views.activities.PushInvasiveActivity;
import com.coppel.rhconecta.dev.views.customviews.PushSnackBarView;

import static com.coppel.rhconecta.dev.business.utils.IUPushNotificationUtil.slideDown;
import static com.coppel.rhconecta.dev.business.utils.IUPushNotificationUtil.slideUp;
import static com.coppel.rhconecta.dev.business.utils.NotificationCreator.KEY_PUSH_DATA;


/**
 * Created by faust on 1/26/18.
 */

public class PushSnackSingleton {

    private static PushSnackSingleton pushSnackSingleton;
    private PushSnackBarView pushSnackbar;
    private Handler mHandler;
    private Activity currentActivity;

    public static PushSnackSingleton getInstance() {

        if(pushSnackSingleton == null){
            pushSnackSingleton  = new PushSnackSingleton();
        }

        return pushSnackSingleton;
    }

    private PushSnackSingleton() {

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                if(message.what == PushType.INVASIVE.getId()){
                    NavigationUtil.openActivityWithSerializable(CoppelApp.getContext(), PushInvasiveActivity.class,KEY_PUSH_DATA,(PushData)message.obj);
                }else if(message.what == PushType.NOINVASIVE.getId()){
                    if (pushSnackbar != null) {
                        pushSnackbar.setPushData((PushData)message.obj);
                        pushSnackbar.setVisibility(View.VISIBLE);
                        slideDown(pushSnackbar);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                slideUp(pushSnackbar);
                            }
                        }, 3000);
                    }
                }
            }
        };

    }

    public static PushSnackSingleton getPushSnackSingleton() {
        return pushSnackSingleton;
    }

    public static void setPushSnackSingleton(PushSnackSingleton pushSnackSingleton) {
        PushSnackSingleton.pushSnackSingleton = pushSnackSingleton;
    }

    public void setPushSnackbar(Activity activity, PushSnackBarView pushSnackbar) {
        this.pushSnackbar = pushSnackbar;
        this.pushSnackbar.setActivity(activity);
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }
}
