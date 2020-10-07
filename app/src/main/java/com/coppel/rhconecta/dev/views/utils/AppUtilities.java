package com.coppel.rhconecta.dev.views.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.presentation.common.extension.SharedPreferencesExtension;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN;

/**
 *
 */
public class AppUtilities {

    /**
     *
     */
    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(
                AppConstants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
    }

    /**
     *
     */
    public static void saveStringInSharedPreferences(
            Context context,
            String key,
            String value
    ) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferencesExtension.putString(sharedPreferences, key, value);
    }

    /**
     *
     */
    public static String getStringFromSharedPreferences(
            Context context,
            String key
    ) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return SharedPreferencesExtension.getString(sharedPreferences, key,null);
    }

    /**
     *
     */
    public static void saveBooleanInSharedPreferences(
            Context context,
            String key,
            boolean value
    ) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferencesExtension.putBoolean(sharedPreferences, key, value);
    }

    /**
     *
     */
    public static boolean getBooleanFromSharedPreferences(
            Context context,
            String key
    ) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return SharedPreferencesExtension.getBoolean(sharedPreferences, key, false);
    }

    /**
     *
     */
    public static void deleteSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     *
     */
    public static void deleteSharedPreferencesWithoutFirebase(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(String key : sharedPreferences.getAll().keySet()){
            if(!key.equals(SHARED_PREFERENCES_FIREBASE_TOKEN)){
                editor.remove(key).commit();
            }
        }
        // editor.clear();
        // editor.apply();
        editor.apply();
    }

    /**
     *
     */
    public static void closeApp(Activity activity) {
        deleteSharedPreferencesWithoutFirebase(activity.getApplicationContext());
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    /**
     *
     */
    public static void openAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

    /**
     *
     */
    public static boolean validatePermissions(int permissionsCount, int[] grantResults) {
        boolean allGranted = false;
        for (int i = 0; i < permissionsCount; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                allGranted = true;
            } else {
                allGranted = false;
                break;
            }
        }
        return allGranted;
    }

    /**
     *
     */
    public static int dpToPx(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resource.getDisplayMetrics()
        );
    }

    /**
     *
     */
    public static float pxToDp(Resources resource, float px)  {
        return (float) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX,
                px,
                resource.getDisplayMetrics()
        );
    }

    /**
     *
     */
    public static void setProfileImage(
            Context context,
            String email,
            String serviceImage,
            ImageView imageView
    ) {
        UserPreference userPreferences = RealmHelper.getUserPreferences(email);
        if (userPreferences != null && userPreferences.getImage() != null && userPreferences.getImage().length > 0) {
            AppUtilities.loadLocalProfileImage(
                    context,
                    BitmapFactory.decodeByteArray(
                            userPreferences.getImage(),
                            0,
                            userPreferences.getImage().length
                    ),
                    imageView
            );
        } else {
            AppUtilities.loadServiceProfileImage(context, serviceImage, imageView);
        }
    }

    /**
     *
     */
    public static void loadLocalProfileImage(Context context, Bitmap image, ImageView imageView) {
        GlideApp.with(context)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .error(R.drawable.ic_account_white)
                .into(imageView);
    }

    /**
     *
     */
    public static void loadServiceProfileImage(Context context, String image, ImageView imageView) {
        GlideApp.with(context)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .error(R.drawable.ic_account_white)
                .into(imageView);
    }

    /**
     *
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File savePDFFile(String name, String base64) {
        try {
            File path = new File(Environment.getExternalStorageDirectory() + "/" + AppConstants.APP_FOLDER);
            path.mkdirs();
            File pdf = new File(path, name + "_" + TextUtilities.dateFormatter(new Date(), AppConstants.DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS) + ".pdf");
            byte[] pdfAsBytes = Base64.decode(base64, Base64.DEFAULT);
            FileOutputStream os;
            os = new FileOutputStream(pdf);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
            return pdf;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    /**
     *
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File savePDFFileLetter(String name, String base64) {
        try {
            File path = new File(Environment.getExternalStorageDirectory() + "/" + AppConstants.APP_FOLDER);
            path.mkdirs();
            File pdf = new File(path, name + ".pdf");
            byte[] pdfAsBytes = Base64.decode(base64, Base64.DEFAULT);
            FileOutputStream os;
            os = new FileOutputStream(pdf);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
            return pdf;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    /**
     *
     */
    public static void sharePDF(Context context, File pdf) {
        Uri uri = FileProvider.getUriForFile(context, AppConstants.FILEPROVIDER, pdf);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setDataAndType(uri, AppConstants.CONTENT_TYPE_PDF);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }

    /**
     *
     */
    public static void openPDF(Context context, File pdf) {
        try {
            Uri uriFile = FileProvider.getUriForFile(context, AppConstants.FILEPROVIDER, pdf);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uriFile, AppConstants.CONTENT_TYPE_PDF);
            intent.putExtra(Intent.EXTRA_STREAM, uriFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
