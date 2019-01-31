package com.coppel.rhconecta.dev.business.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

/**
 * Created by faust on 1/24/18.
 */

public class ShareUtil {



    public static void shareString(Context context, String url) {
        try {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            //Alerts.showToast(this, "No es posible compartir el artículo", Toast.LENGTH_SHORT);
        }

    }
}
