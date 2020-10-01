package com.coppel.rhconecta.dev.visionarios.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.models.NotificationEvent;
import com.coppel.rhconecta.dev.business.utils.NotificationCreator;
import com.coppel.rhconecta.dev.business.utils.NotificationHelper;
import com.coppel.rhconecta.dev.resources.db.RealmTransactions;
import com.coppel.rhconecta.dev.resources.db.models.NotificationsUser;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;
import com.coppel.rhconecta.dev.views.activities.SplashScreenActivity;
import com.coppel.rhconecta.dev.views.utils.AppConstants;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_GOTO_SECTION;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ID_PANTALLA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.ID_SISTEMA;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.NOTIFICATION_TITLE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.NOTIFICATION_BODY;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_EXPENSES;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_HOLIDAYS;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_NOTIFICATION_EXPENSES_AUTHORIZE;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.OPTION_SAVING_FUND;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        EventBus.getDefault().post(new NotificationEvent(""));

        if (AppUtilities.getBooleanFromSharedPreferences(getApplicationContext(), AppConstants.SHARED_PREFERENCES_IS_LOGGED_IN)) {
            Random rand = new Random();
            int idNotification = rand.nextInt(999);
            if(remoteMessage.getNotification() != null){
                Map<String, String> params = new HashMap<>();
                createNotification(
                        Objects.requireNonNull(remoteMessage.getNotification().getTitle()),
                        remoteMessage.getNotification().getBody(),params,idNotification
                );

            }else {
                Map<String, String> paramsData = remoteMessage.getData();
                JSONObject object = new JSONObject(paramsData);
                if (paramsData != null) {
                    try {
                        processNotification(idNotification, remoteMessage);
                        Map<String, String> params = getParamsIntent(remoteMessage);
                        createNotification(Objects.requireNonNull(paramsData.get(NOTIFICATION_TITLE)), paramsData.get(NOTIFICATION_BODY),params,idNotification);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    private void createNotification(String title, String body, Map<String, String> params, int idNotification){

        if(title.isEmpty()){
            title = getString(R.string.app_name);
        }
        Notification notification = NotificationCreator.buildLocalNotification(CoppelApp.getContext(),
                title,body ,NotificationCreator.getPendindIntentSection(CoppelApp.getContext(),SplashScreenActivity.class,params )).build();
        NotificationHelper.getNotificationManager(CoppelApp.getContext()).notify(idNotification, notification);
    }

    private Map<String,String> getParamsIntent(RemoteMessage remoteMessage){
        Map<String,String> content = remoteMessage.getData();
        Map<String,String> params = new HashMap<>();
        if(content.containsKey(ID_SISTEMA)){
            int idSistema = Integer.parseInt(Objects.requireNonNull(content.get(ID_SISTEMA)));
            int idPantalla = Integer.parseInt(Objects.requireNonNull(content.get(ID_PANTALLA)));
            if(idSistema == 9){
                params.put(BUNDLE_GOTO_SECTION,OPTION_SAVING_FUND);
            }else if(idSistema == 10){
                params.put(BUNDLE_GOTO_SECTION, OPTION_HOLIDAYS);
            }else if(idSistema == 11) {
                params.put(BUNDLE_GOTO_SECTION, idPantalla == 2 ? OPTION_NOTIFICATION_EXPENSES_AUTHORIZE : OPTION_EXPENSES);
            }
        }

        return params;
    }


    private void processNotification(int idNotification,RemoteMessage remoteMessage){
        Map<String,String> content = remoteMessage.getData();
        if(content.containsKey("id_sistema")){
            String uuid = UUID.randomUUID().toString();
            String idSistema = content.get("id_sistema");
            String numgColaborator = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(),SHARED_PREFERENCES_NUM_COLABORADOR);
            NotificationsUser notificationsUser = new NotificationsUser(uuid,numgColaborator,Integer.parseInt(idSistema),idNotification);
            RealmTransactions.insertInto(notificationsUser);
        }
    }

    @SuppressLint("WrongConstant")
    private void sendNotification(String title,String messageBody) {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.addFlags(Notification.FLAG_ONGOING_EVENT);
        intent.addFlags(Notification.FLAG_NO_CLEAR);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icn_notificaciones_blanco)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null)
            return;

        Notification notification = notificationBuilder.build();

        notificationManager.notify(0 ,notification);
    }

}


