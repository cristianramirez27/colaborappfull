package com.coppel.rhconecta.dev.visionarios.firebase;

import android.app.Notification;
import android.util.Log;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.business.models.PushData;
import com.coppel.rhconecta.dev.business.utils.Foreground;
import com.coppel.rhconecta.dev.business.utils.IUPushNotificationUtil;
import com.coppel.rhconecta.dev.business.utils.JsonManager;
import com.coppel.rhconecta.dev.business.utils.NotificationCreator;
import com.coppel.rhconecta.dev.business.utils.NotificationHelper;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

import static com.coppel.rhconecta.dev.business.Enums.PushType.INVASIVE;
import static com.coppel.rhconecta.dev.business.Enums.PushType.NOINVASIVE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);
        Log.e("JSON_OBJECT", object.toString());
        PushData pushData = (PushData) JsonManager.jsonToObject(object.toString(),PushData.class);

        boolean hasSesion = AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN);

        if((pushData.getLoginRequired().equals("1") && hasSesion) ||
                pushData.getLoginRequired().equals("0")){

            if(!Foreground.get().isForeground()){
                Notification notification = NotificationCreator.buildLocalNotification(CoppelApp.getContext(),pushData.getTitle(),pushData.getSubtitle(),
                        NotificationCreator.getPendindIntent(CoppelApp.getContext(),
                                hasSesion ? MainActivity.class : MainActivity.class,
                                pushData)).build();
                NotificationHelper.getNotificationManager(CoppelApp.getContext()).notify(778, notification);
            }else {
                if(pushData.getType().equals(String.valueOf(INVASIVE.getId()))){
                    IUPushNotificationUtil.showPushNotificationIU(INVASIVE,pushData);
                }else if(pushData.getType().equals(String.valueOf(NOINVASIVE.getId()))){
                    IUPushNotificationUtil.showPushNotificationIU(NOINVASIVE,pushData);
                }
            }
        }
    }
}


