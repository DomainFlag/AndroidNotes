package com.example.cchiv.androidnotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cchiv.androidnotes.utilities.ActivityReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ComponentActivity extends AppCompatActivity {

    private String component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        this.component = getIntent().getStringExtra("component");
        setTitle(this.component);

        ListView listView = (ListView) findViewById(R.id.component_list);

        ComponentAdapter componentAdapter = new ComponentAdapter(this, fetchNotes());

        updateDemoContent(listView);
        updateNotesContent(listView);
        updateSnippetContent(listView);

        listView.setAdapter(componentAdapter);

        runActivityComponent();
    }

    StringBuilder fetchNotesString() {
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

        return jsonData;
    }

    ArrayList<Note> fetchNotes() {
        StringBuilder jsonData = fetchNotesString();

        ArrayList<Note> notes = new ArrayList<>();

        try {
            JSONArray noteArray = new JSONArray(jsonData.toString());

            JSONObject note;
            int it = 0;
            do {
                note = noteArray.getJSONObject(it);
                it++;
            } while(!note.getString("noteName").equals(this.component) && it < noteArray.length());

            JSONArray jsonArray = note.getJSONArray("noteComponents");

            for(it = 0; it < jsonArray.length(); it++) {
                JSONObject component = jsonArray.getJSONObject(it);

                notes.add(new Note(
                        component.getString("label"),
                        component.getString("content"),
                        component.getString("snippets")
                ));
            }
        } catch(JSONException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
        }

        return notes;
    }

    public void runActivityComponent() {
        try {
            Class
                    .forName("com.example.cchiv.androidnotes.notes." + this.component)
                    .getConstructor(Activity.class)
                    .newInstance(this);
        } catch(ClassNotFoundException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
        } catch(NoSuchMethodException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
        } catch(InstantiationException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
        } catch(IllegalAccessException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
        } catch(InvocationTargetException e) {
            Log.v(this.getClass().getSimpleName(), e.getCause().toString());
        }
    }

    public void updateDemoContent(ListView listView) {
        View dividerDemoView = LayoutInflater.from(listView.getContext()).inflate(R.layout.divider_layout, listView, false);
        ((TextView) dividerDemoView.findViewById(R.id.divider_text)).setText(R.string.component_demo);
        listView.addHeaderView(dividerDemoView);

        LinearLayout featureDemoView = (LinearLayout) LayoutInflater.from(listView.getContext()).inflate(R.layout.feature_layout, listView, false);
        ((LinearLayout) featureDemoView.findViewById(R.id.feature_layout)).addView(
                LayoutInflater
                        .from(listView.getContext())
                        .inflate(R.layout.note_content_http_networking, featureDemoView, false)
        );
        listView.addHeaderView(featureDemoView);
    }


    public void updateNotesContent(ListView listView) {
        View dividerNotesView = LayoutInflater.from(listView.getContext()).inflate(R.layout.divider_layout, listView, false);
        ((TextView) dividerNotesView.findViewById(R.id.divider_text)).setText(R.string.component_notes);
        listView.addHeaderView(dividerNotesView);
    }

    public void updateSnippetContent(ListView listView) {
        ActivityReader activityReader = new ActivityReader(this);

        View dividerSnippetView = LayoutInflater.from(listView.getContext()).inflate(R.layout.divider_layout, listView, false);
        ((TextView) dividerSnippetView.findViewById(R.id.divider_text)).setText(R.string.component_snippet);
        listView.addHeaderView(dividerSnippetView);

        LinearLayout featureSnippetView = (LinearLayout) LayoutInflater.from(listView.getContext()).inflate(R.layout.feature_layout, listView, false);
        ((LinearLayout) featureSnippetView.findViewById(R.id.feature_layout)).addView(
                LayoutInflater
                        .from(listView.getContext())
                        .inflate(R.layout.snippet_layout, featureSnippetView, false)
        );

        ((TextView) featureSnippetView.findViewById(R.id.snippet_content)).setText(activityReader.readSnippet(this.component));
    }
}
