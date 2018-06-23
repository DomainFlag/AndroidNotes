package com.example.cchiv.androidnotes.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cchiv.androidnotes.ComponentAdapter;
import com.example.cchiv.androidnotes.Note;
import com.example.cchiv.androidnotes.R;

import java.util.ArrayList;

public class ComponentRender {

    private Context context;

    public ComponentRender(Context context, int resource, String component, String demo) {
        this.context = context;

        ListView listView = ((Activity) context).findViewById(resource);

        updateDemoContent(listView, demo);
        updateSnippetContent(listView, component);
        updateNotesContent(listView, component);
    }


    private void inflateHeaderDivider(ListView listView, int textDividerResource) {
        View dividerNotesView = LayoutInflater.from(listView.getContext()).inflate(R.layout.divider_layout, listView, false);
        ((TextView) dividerNotesView.findViewById(R.id.divider_text)).setText(textDividerResource);
        listView.addHeaderView(dividerNotesView);
    }

    private View inflateHeaderFeature(ListView listView, int layoutResourceIdentifier) {
        LinearLayout featureView = (LinearLayout) LayoutInflater.from(listView.getContext()).inflate(R.layout.feature_layout, listView, false);
        ((LinearLayout) featureView.findViewById(R.id.feature_layout)).addView(
                LayoutInflater
                        .from(listView.getContext())
                        .inflate(layoutResourceIdentifier, featureView, false)
        );

        listView.addHeaderView(featureView);

        return featureView;
    }

    private void updateDemoContent(ListView listView, String demo) {
        int resourceLayoutIdentifier = context.getResources().getIdentifier(demo, "layout", this.context.getPackageName());

        if(resourceLayoutIdentifier != 0) {
            inflateHeaderDivider(listView, R.string.component_demo);

            inflateHeaderFeature(listView, resourceLayoutIdentifier);
        }
    }


    private void updateNotesContent(ListView listView, String component) {
        NoteReader noteReader = new NoteReader(this.context);
        ArrayList<Note> notes = noteReader.fetchNotes(component);

        if(notes != null && !notes.isEmpty()) {
            inflateHeaderDivider(listView, R.string.component_notes);

            ComponentAdapter componentAdapter = new ComponentAdapter(this.context, notes);
            listView.setAdapter(componentAdapter);
        }
    }

    private void updateSnippetContent(ListView listView, String component) {
        ActivityReader activityReader = new ActivityReader(this.context);
        StringBuilder snippetBuilder = activityReader.readSnippet(component);

        if(snippetBuilder != null) {
            String snippet = snippetBuilder.toString();

            inflateHeaderDivider(listView, R.string.component_snippet);
            ((TextView) inflateHeaderFeature(listView, R.layout.snippet_layout).findViewById(R.id.snippet_content)).setText(snippet);
        }
    }
}
