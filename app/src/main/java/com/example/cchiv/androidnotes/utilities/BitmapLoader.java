package com.example.cchiv.androidnotes.utilities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

public class BitmapLoader extends AsyncTask<Void, Void, Bitmap> {

    private WeakReference<Activity> activityWeakReference;

    private URL url;
    private int id;

    public BitmapLoader(Activity activity) {
        super();

        this.activityWeakReference = new WeakReference<Activity>(activity);
    }

    public void loadBitmapInBackground(URL url, int id) {
        this.url = url;
        this.id = id;

        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            BufferedInputStream in = new BufferedInputStream(this.url.openStream());
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();

            final int MAX = 80000;
            int len;
            byte[] bytes = new byte[80000];

            len = in.read(bytes, 0, MAX);
            while(len != -1) {
                byteArrayInputStream.write(bytes, 0, len);
                len = in.read(bytes, 0, MAX);
            }

            return BitmapFactory.decodeByteArray(byteArrayInputStream.toByteArray(), 0, byteArrayInputStream.size());
        } catch(IOException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(bitmap != null) {
            Activity activity = this.activityWeakReference.get();
            if(activity != null) {
                ((ImageView) activity.findViewById(this.id)).setImageBitmap(bitmap);
            }
        }
    }
}
