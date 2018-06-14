package com.example.cchiv.androidnotes.notes;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.cchiv.androidnotes.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPNetworkingActivity extends AppCompatActivity {

    private static final String SCHEME = "https";
    private static final String AUTHORITY = "eu.api.battle.net";
    private static final String PATH = "/sc2/profile/242/";
    private static final String QUERY_PARAMETER_LOCALE = "locale";
    private static final String QUERY_PARAMETER_API = "apikey";

    private static final String API_VALUE = "YOUR&API&KEY";
    private static final String LOCALE_VALUE = "en_GB";

    public HTTPNetworkingActivity() {
        super();
    }

    public HTTPNetworkingActivity(Activity activity) {
        super();

        TextView usernameTextView  = (TextView) activity.findViewById(R.id.profile_username);
        TextView regionTextView  = (TextView) activity.findViewById(R.id.profile_region);
        TextView idTextView  = (TextView) activity.findViewById(R.id.profile_id);

        String username = usernameTextView.getText().toString();
        String region = regionTextView.getText().toString();
        String identifier = idTextView.getText().toString();

        Http_Async http_async = new Http_Async();
        http_async.execute(username, region, identifier);
    }

    public class Http_Async extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            Uri.Builder builder = new Uri.Builder();
            builder
                    .scheme(SCHEME)
                    .authority(AUTHORITY)
                    .path(PATH)
                    .appendPath(params[2])
                    .appendPath(params[1])
                    .appendPath(params[0])
                    .appendQueryParameter(QUERY_PARAMETER_LOCALE, LOCALE_VALUE)
                    .appendQueryParameter(QUERY_PARAMETER_API, API_VALUE);

            Log.v(this.getClass().getSimpleName(), builder.build().toString());

            URL url = null;
            try {
                url = new URL(builder.build().toString());
            } catch(MalformedURLException m) {
                Log.v(this.getClass().getSimpleName(), m.toString());
            }

            if(url != null) {
                HttpURLConnection httpURLConnection;
                try {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(15000);
                    httpURLConnection.setReadTimeout(15000);
                    httpURLConnection.connect();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = bufferedReader.readLine();
                    while(line != null) {
                        Log.v(this.getClass().getSimpleName(), line);
                        line = bufferedReader.readLine();
                    }
                } catch(IOException io) {
                    Log.v(this.getClass().getSimpleName(), io.toString());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
