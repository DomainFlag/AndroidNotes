package com.example.cchiv.androidnotes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ComponentAdapter extends ArrayAdapter<Note> {

    public ComponentAdapter(@NonNull Context context, @NonNull ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.note_layout, parent, false);
        }

        Note note = getItem(position);

        ((TextView) view.findViewById(R.id.label_value)).setText(note.getLabel());
        ((TextView) view.findViewById(R.id.content_value)).setText(note.getContent());
        ((TextView) view.findViewById(R.id.snippet_value)).setText(note.getSnippet());

        return view;
    }
}
