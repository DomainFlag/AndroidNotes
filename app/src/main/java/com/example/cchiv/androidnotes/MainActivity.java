package com.example.cchiv.androidnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] objects = {
                "User Input",
                "Intents",
                "Activity Lifecycle",
                "Media",
                "Fragments",
                "HTTP Networking",
                "Responsive Design",
                "OpenGL ES",
                "Permissions",
                "Menu",
                "Preferences",
                "SQLite",
                "Content Provider",
                "Cursor Loader",
                "Recycler View",
                "ListView",
                "Loader",
                "Service",
                "Notifications",
                "Job Scheduler",
                "Broadcast Receiver",
                "Constraint Layout"
        };

        ListView activityList = findViewById(R.id.activity_list);
        NoteListAdapter arrayAdapter = new NoteListAdapter(this, objects);

        activityList.setAdapter(arrayAdapter);

        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String trimmedClassName = objects[i].replaceAll("(\\s[\\s&]*\\s?)", "") + "Activity";
                String trimmedSnippetName = objects[i].toLowerCase().replaceAll("(\\s[\\s&]*\\s?)", "_") + "_activity";

                try {
                    Class<?> desiredClass = Class
                            .forName("com.example.cchiv.androidnotes.notes." + trimmedClassName);

                    Intent intent = new Intent(MainActivity.this, desiredClass);
                    intent.putExtra("className", trimmedClassName);
                    intent.putExtra("snippetName", trimmedSnippetName);

                    startActivity(intent);
                } catch(ClassNotFoundException e) {
                    Log.v(MainActivity.class.getSimpleName(), e.toString());
                }
            }
        });
    }
}
