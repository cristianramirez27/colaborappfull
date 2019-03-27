package com.coppel.rhconecta.dev.visionarios.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.coppel.rhconecta.dev.visionarios.videos.interfaces.Videos;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.presenters.VideosPresenter;

import java.io.InputStream;
import java.util.ArrayList;

public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap[]> {
    private Videos.View mView;
    ArrayList<Video> videos;

    public DownloadImagesTask(Videos.View mview, ArrayList<Video> vvideos) {
        this.mView = mview;
        this.videos = vvideos;
    }

    protected Bitmap[] doInBackground(String[] urls) {
        Bitmap[] mIcon11 = new Bitmap[urls.length];
        try {
            for(int k=0; k<urls.length; k++){
                InputStream in = new java.net.URL(urls[k]).openStream();
                mIcon11[k] = BitmapFactory.decodeStream(in);
            }

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap[] result) {
        this.mView.cargarVideos(this.videos,result);
    }
}
