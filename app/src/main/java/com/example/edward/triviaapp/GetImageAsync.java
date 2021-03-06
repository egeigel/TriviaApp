package com.example.edward.triviaapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by edward on 10/5/17.
 */

public class GetImageAsync extends AsyncTask<String, Void, Void> {

    ImageView imageView;
    Bitmap bitmap = null;
    Activity activity;

    public GetImageAsync(Activity activity, ImageView iv) {
        imageView = iv;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute(){
        imageView.setVisibility(View.INVISIBLE);
        ProgressBar b = activity.findViewById(R.id.imageProgress);
        b.setVisibility(View.VISIBLE);
    }

    // "Void" meets AsyncTask type requirements
    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection connection = null;
        bitmap = null;
        try {
            // establish connection
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            // get resulting bitmap from url
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (bitmap != null && imageView != null) {
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
        }
        ((ProgressBar)activity.findViewById(R.id.imageProgress)).setVisibility(View.INVISIBLE);
    }
}
