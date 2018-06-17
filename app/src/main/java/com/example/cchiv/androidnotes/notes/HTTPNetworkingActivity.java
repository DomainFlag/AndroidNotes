package com.example.cchiv.androidnotes.notes;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cchiv.androidnotes.R;
import com.example.cchiv.androidnotes.utilities.BitmapLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPNetworkingActivity extends AppCompatActivity {

    private Activity activityContext;

    private static final String SCHEME = "https";
    private static final String AUTHORITY = "eu.api.battle.net";
    private static final String PATH = "/sc2/profile/";
    private static final String QUERY_PARAMETER_LOCALE = "locale";
    private static final String QUERY_PARAMETER_API = "apikey";

    private static final String API_VALUE = "YOUR&API&KEY";
    private static final String LOCALE_VALUE = "en_GB";

    private TextView usernameTextView;
    private TextView regionTextView;
    private TextView idTextView;

    private String TAG;

    public HTTPNetworkingActivity() {
        super();
    }

    public HTTPNetworkingActivity(Activity activity) {
        super();

        this.TAG = activity.getClass().getCanonicalName();

        activityContext = activity;

        usernameTextView  = (TextView) activity.findViewById(R.id.profile_username);
        regionTextView  = (TextView) activity.findViewById(R.id.profile_region);
        idTextView  = (TextView) activity.findViewById(R.id.profile_id);

        ((Button) activity.findViewById(R.id.api_request_submit))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameTextView.getText().toString();
                String region = regionTextView.getText().toString();
                String identifier = idTextView.getText().toString();

                if(!username.isEmpty() && !region.isEmpty() && !identifier.isEmpty()) {
                    Http_Async http_async = new Http_Async();
                    http_async.execute(username, region, identifier);
                }
            }
        });
    }

    public void parseJSONResponse(StringBuilder stringBuilder) {
        String data = stringBuilder.toString();
        if(data.isEmpty()) {
            Log.v(TAG, "Empty response");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            if(jsonObject.has("displayName")) {
                String displayName = jsonObject.getString("displayName");

                ((TextView) activityContext.findViewById(R.id.response_profile_displayName)).setText(displayName);
            }

            if(jsonObject.has("portrait")) {
                JSONObject portrait = jsonObject.getJSONObject("portrait");
                if(portrait.has("url")) {
                    URL url = null;
                    try {
                        url = new URL(portrait.getString("url"));
                        BitmapLoader bitmapLoader = new BitmapLoader(url, activityContext.findViewById(R.id.response_profile_portrait));
                        bitmapLoader.loadImage();
                    } catch(MalformedURLException e) {
                        Log.v(TAG, e.toString());
                    }
                }
            }

        } catch(JSONException e) {
            Log.v(TAG, e.toString());
        }
    }

    public class Http_Async extends AsyncTask<String, Void, StringBuilder> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected StringBuilder doInBackground(String... params) {

            Uri.Builder builder = new Uri.Builder();
            builder
                    .scheme(SCHEME)
                    .authority(AUTHORITY)
                    .path(PATH)
                    .appendPath(params[2])
                    .appendPath(params[1])
                    .appendPath(params[0])
                    .appendPath("")
                    .appendQueryParameter(QUERY_PARAMETER_LOCALE, LOCALE_VALUE)
                    .appendQueryParameter(QUERY_PARAMETER_API, API_VALUE);

            URL url = null;
            try {
                url = new URL(builder.build().toString());
            } catch(MalformedURLException m) {
                Log.v(TAG, m.toString());
            }

            StringBuilder stringBuilder = new StringBuilder();
            if(url != null) {
                HttpURLConnection httpURLConnection;
                try {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(15000);
                    httpURLConnection.setReadTimeout(15000);
                    httpURLConnection.connect();

                    if(httpURLConnection.getResponseCode() >= 300) {
                        Log.v(TAG, "Unsuccessful request: " + httpURLConnection.getResponseMessage() );
                        return stringBuilder;
                    }

                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = bufferedReader.readLine();
                    while(line != null) {
                        stringBuilder.append(line);
                        line = bufferedReader.readLine();
                    }
                } catch(IOException io) {
                    Log.v(TAG, io.toString());
                }
            }

            return stringBuilder;
        }

        @Override
        protected void onPostExecute(StringBuilder stringBuilder) {
            super.onPostExecute(stringBuilder);

            parseJSONResponse(stringBuilder);
        }
    }
}
