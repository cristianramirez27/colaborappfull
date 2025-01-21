package com.coppel.rhconecta.dev.views.utils;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.coppel.rhconecta.dev.BuildConfig;
import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.presentation.common.extension.SharedPreferencesExtension;
import com.coppel.rhconecta.dev.resources.db.RealmHelper;
import com.coppel.rhconecta.dev.resources.db.models.UserPreference;
import com.coppel.rhconecta.dev.views.activities.LoginActivity;
import com.coppel.rhconecta.dev.views.activities.LoginMicrosoftActivity;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentWarning;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ENDPOINT_ZENDESK_CONFIG;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN;

/**
 *
 */
public class AppUtilities {

    /**
     *
     */
    public static SharedPreferences getSharedPreferences(Context context) {
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
        return SharedPreferencesExtension.getString(sharedPreferences, key, null);
    }

    /**
     * sharedPreferences is injected and in this way we avoid having in many classes the instance of context
     *
     * @param sharedPreferences
     * @param key
     * @return
     */
    public static String getStringFromSharedPreferences(SharedPreferences sharedPreferences, String key) {
        return SharedPreferencesExtension.getString(sharedPreferences, key, null);
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
    public static void saveJsonObjectInSharedPreferences(
            Context context,
            String key,
            String value
    ) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferencesExtension.putJsonObject(sharedPreferences, key, value);
    }

    public static JsonObject getJsonObjectFromSharedPreferences(
            Context context,
            String key
    ) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return SharedPreferencesExtension.getJsonObject(sharedPreferences, key);
    }

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
        try{
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (String key : sharedPreferences.getAll().keySet()) {
                if (!key.equals(SHARED_PREFERENCES_FIREBASE_TOKEN)) {
                    editor.remove(key).commit();
                }
            }
            // editor.clear();
            // editor.apply();
            editor.apply();
        } catch (Exception e) {
            Log.i("Exception","Exception: " + e);
        }

    }

    /**
     *
     */
    public static void closeApp(Activity activity) {
        deleteSharedPreferencesWithoutFirebase(activity.getApplicationContext());
        //activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.startActivity(new Intent(activity, LoginMicrosoftActivity.class));
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
    public static float pxToDp(Resources resource, float px) {
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
    public static File savePDFFile(Context context, String name, String base64) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return savePdfFileQ(context, name, base64);
        return savePdfFile(name, base64);
    }

    /**
     *
     */
    private static File savePdfFile(String name, String base64) {
        try {
            String downloadPath =
                    Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;
            File path = new File(downloadPath + "/" + AppConstants.APP_FOLDER);
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
            //ioe.printStackTrace();
            return null;
        }
    }

    /**
     *
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private static File savePdfFileQ(Context context, String name, String base64) {
        try {
            String directoryPath = Environment.DIRECTORY_DOWNLOADS + "/" + AppConstants.APP_FOLDER;

            ContentResolver contentResolver = context.getContentResolver();
            ContentValues values = new ContentValues();

            String timeStamp = TextUtilities
                    .dateFormatter(new Date(), AppConstants.DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS);
            String fileName = name + "_" + timeStamp + ".pdf";

            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");

            values.put(MediaStore.MediaColumns.RELATIVE_PATH, directoryPath);

            Uri uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), values);
            byte[] pdfAsBytes = Base64.decode(base64, Base64.DEFAULT);
            OutputStream outputStream = contentResolver.openOutputStream(uri);
            outputStream.write(pdfAsBytes);
            outputStream.flush();
            outputStream.close();
            return new File(directoryPath, fileName);
        } catch (IOException ioe) {
            //ioe.printStackTrace();
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
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static double toDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static void showBlockDialog(String message, String title, String rightOptionText, FragmentManager fragmentManager) {
        DialogFragmentWarning dialogFragmentWarning = new DialogFragmentWarning();
        dialogFragmentWarning.setSinlgeOptionData(title, message, rightOptionText);
        dialogFragmentWarning.setOnOptionClick(new DialogFragmentWarning.OnOptionClick() {
            @Override
            public void onLeftOptionClick() {
                dialogFragmentWarning.close();
            }

            @Override
            public void onRightOptionClick() {
                dialogFragmentWarning.close();
            }
        });
        dialogFragmentWarning.show(fragmentManager, DialogFragmentWarning.TAG);
    }

    public static String getDeviceName() {
        StringBuilder str = new StringBuilder();
        str.append(Build.BRAND);
        str.append(" ");
        str.append(Build.MODEL);
        str.append(" ");
        str.append(Build.DEVICE);


        return str.toString();
    }

    public static String getAndroidVersion() {
        StringBuilder str = new StringBuilder();
        str.append("Android ");
        str.append(Build.VERSION.RELEASE);
        str.append(" SDK ");
        str.append(Build.VERSION.SDK_INT);
        return str.toString();
    }

    public static String getVersionApp() {
        return BuildConfig.VERSION_NAME;
    }

    public static JsonObject getZendeskConfiguration(Context context){

        JsonObject zendeskConfiguration = AppUtilities.getJsonObjectFromSharedPreferences(context, ENDPOINT_ZENDESK_CONFIG);

        Calendar calendar = Calendar.getInstance();
        // Obtiene el nombre del día
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());

        zendeskConfiguration = zendeskConfiguration.getAsJsonObject("dia_"+dayOfWeek);

        return zendeskConfiguration;
    }

    public static String getAuthHeader(Context context){
        return getStringFromSharedPreferences(
                context,
                AppConstants.SHARED_PREFERENCES_TOKEN
        );
    }
}
