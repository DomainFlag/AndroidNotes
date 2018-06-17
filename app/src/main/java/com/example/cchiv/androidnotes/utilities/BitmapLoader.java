package com.example.cchiv.androidnotes.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class BitmapLoader {
    private final String TAG = this.getClass().getCanonicalName();

    private URL url;
    private View view;

    public BitmapLoader(URL url, View view) {
        this.url = url;
        this.view = view;
    }

    public void loadImage() {
        LoadBitmap loadBitmap = new LoadBitmap();
        loadBitmap.execute(this.url, this.view);
    }

    public class LoadBitmap extends AsyncTask<Object, Void, Bitmap> {

        public LoadBitmap() {
            super();
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            Log.v(TAG, String.valueOf(params.length));
            if(params.length < 2)
                return null;

            try {
                BufferedInputStream in = new BufferedInputStream(url.openStream());
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
                Log.v(TAG, e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }
        }
    }
}
