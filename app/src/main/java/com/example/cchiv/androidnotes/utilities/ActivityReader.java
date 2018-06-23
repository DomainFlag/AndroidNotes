package com.example.cchiv.androidnotes.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActivityReader {
    private AssetManager assetManager;

    public ActivityReader(Context context) {
        this.assetManager = context.getAssets();
    }

    public StringBuilder readSnippet(String snippetName) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("snippets/" + snippetName + ".txt");
        } catch(IOException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());

            return null;
        }

        StringBuilder stringBuilder = null;
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            try {
                stringBuilder = new StringBuilder("");

                String line = bufferedReader.readLine();
                while(line != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");

                    line = bufferedReader.readLine();
                }
            } catch(IOException e) {
                Log.v(this.getClass().getSimpleName(), e.toString());

                return null;
            }
        }

        return stringBuilder;
    }
}
