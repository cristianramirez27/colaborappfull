package com.coppel.rhconecta.dev.business.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * Created by noe_3 on 08/06/2017.
 */

public class ImageLoaderUtil {


    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;        // min pixels
    private static final int DEFAULT_MIN_HEIGHT_QUALITY = 400;        // min pixels
    private static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;
    private static int minHeightQuality = DEFAULT_MIN_HEIGHT_QUALITY;

    public static void loadPictureFromURL(Context context, String path, final ImageView ivImg) {

        if (path != null && !path.isEmpty()) {

            if (path.startsWith("https://"))
                path = path.replace("https://", "http://");

            Glide.with(context)
                    .load(path)
                    .into(ivImg);
        }


    }

    public static void loadPictureFromURLPlaceholder(Context context, String path, ImageView ivImg, int idResourcePlaceHolder) {

        //Picasso.with(context).load(path).placeholder(idResourcePlaceHolder).error(idResourcePlaceHolder).into(ivImg);
        if (path != null && !path.isEmpty() && path.startsWith("https://"))
            path = path.replace("https://", "http://");

        Glide.with(context)
                .load(path).apply(new RequestOptions().placeholder(idResourcePlaceHolder).error(idResourcePlaceHolder)
                .fitCenter())
                .into(ivImg);
    }


    public static void loadPictureFromURLWithPlaceholder(Context context, String path, ImageView ivImg, int ic_placeholder) {


        if (path != null && !path.isEmpty()) {
            Glide.with(context)
                    .load(path).apply(new RequestOptions().placeholder(ic_placeholder).error(ic_placeholder)
                    .fitCenter())
                    .into(ivImg);
        } else {
            Glide.with(context)
                    .load(ic_placeholder).into(ivImg);
        }

    }

    public static void loadPictureFromLocal(Context context, int resource, ImageView ivImg) {
        //Picasso.with(context).load(resource).into(ivImg);
        Glide.with(context)
                .load(resource)
                .into(ivImg);
    }

    public static byte[] getBytesFromFileByPath(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {

            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            bytes = null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            bytes = null;
        }

        return bytes;
    }


    public static byte[] extractBytesFromImageView(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        Bitmap bmap = imageView.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /**
     * Rota una Fotografia
     *
     * @param Url
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotar(String Url, Bitmap bitmap) {
        try {
            ExifInterface exifInterface = new ExifInterface(Url);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();

            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true); // rotating bitmap
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     **/
    public static Bitmap getImageResized(Context context, Uri selectedImage) {
        Bitmap bm;
        int[] sampleSizes = new int[]{5, 3, 2, 1};
        int i = 0;
        do {
            bm = decodeBitmap(context, selectedImage, sampleSizes[i]);
            i++;
        } while (bm != null
                && (bm.getWidth() < minWidthQuality || bm.getHeight() < minHeightQuality)
                && i < sampleSizes.length);
        return bm;
    }


    public static Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize) {
        Bitmap actuallyUsableBitmap = null;
        AssetFileDescriptor fileDescriptor = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");
            actuallyUsableBitmap = BitmapFactory
                    .decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
            fileDescriptor.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actuallyUsableBitmap;
    }

    public static void setMinQuality(int minWidthQuality, int minHeightQuality) {
        minWidthQuality = minWidthQuality;
        minHeightQuality = minHeightQuality;
    }

    /*
    public static boolean deleteImageProfile(){
        File file = new File(BSMedicalApp.getInstance().getPrefs().loadData(PREF_PATH_PICTURE));
        return file.delete();
    }
    */

    public static byte[] getBytesFromFile(String someFileName) {

        File file = new File(someFileName);
        byte[] bFile = null;
        if (file.exists() && !file.isDirectory()) {
            FileInputStream fileInputStream = null;
            try {
                bFile = new byte[(int) file.length()];
                //convert file into array of bytes
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
                for (int i = 0; i < bFile.length; i++) {
                    System.out.print((char) bFile[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return bFile;
    }


}
