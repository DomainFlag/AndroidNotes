package com.example.cchiv.androidnotes;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ComponentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        String noteString = getIntent().getStringExtra("component");
        setTitle(noteString);

        InputStream is = getResources().openRawResource(R.raw.components);
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder jsonData = new StringBuilder("");

        try {
            String line = bufferedReader.readLine();
            while(line != null) {
                jsonData.append(line);
                line = bufferedReader.readLine();
            }
        } catch(IOException io) {
            Log.v(this.getClass().getSimpleName(), io.toString());
        }


        ArrayList<Component> components = new ArrayList<>();

        try {
            JSONArray noteArray = new JSONArray(jsonData.toString());

            JSONObject note;
            int it = 0;
            do {
                note = noteArray.getJSONObject(it);
            } while(note.getString("noteName").equals("HTTP Networking Activity") && ++it < noteArray.length());

            JSONArray jsonArray = note.getJSONArray("noteComponents");

            for(it = 0; it < jsonArray.length(); it++) {
                JSONObject component = jsonArray.getJSONObject(it);

                components.add(new Component(
                        component.getString("label"),
                        component.getString("content"),
                        component.getString("snippet")
                ));
            }
        } catch(JSONException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
        }

        ListView listView = (ListView) findViewById(R.id.component_list);
        Resources resources = getResources();

        /* Snippet Code Content */
        if(resources.getIdentifier(noteString, "layout", getPackageName()) != 0) {
            View dividerSnippetView = LayoutInflater.from(listView.getContext()).inflate(R.layout.divider_layout, listView, false);
            ((TextView) dividerSnippetView.findViewById(R.id.divider_text)).setText(R.string.component_snippet);
            listView.addHeaderView(dividerSnippetView);

            View featureSnippetView = LayoutInflater.from(listView.getContext()).inflate(R.layout.feature_layout, listView, false);
            listView.addHeaderView(featureSnippetView);
        }

        /* Demo Content */
        View dividerDemoView = LayoutInflater.from(listView.getContext()).inflate(R.layout.divider_layout, listView, false);
        ((TextView) dividerDemoView.findViewById(R.id.divider_text)).setText(R.string.component_demo);
        listView.addHeaderView(dividerDemoView);

        LinearLayout featureDemoView = (LinearLayout) LayoutInflater.from(listView.getContext()).inflate(R.layout.feature_layout, listView, false);
        ((LinearLayout) featureDemoView.findViewById(R.id.feature_layout)).addView(
                LayoutInflater
                        .from(listView.getContext())
                        .inflate(R.layout.note_content_user_input, featureDemoView, false)
        );
        listView.addHeaderView(featureDemoView);

        /* Notes Content */
        View dividerNotesView = LayoutInflater.from(listView.getContext()).inflate(R.layout.divider_layout, listView, false);
        ((TextView) dividerNotesView.findViewById(R.id.divider_text)).setText(R.string.component_notes);
        listView.addHeaderView(dividerNotesView);

        ComponentAdapter componentAdapter = new ComponentAdapter(this, components);

        listView.setAdapter(componentAdapter);
    }
}
