package com.example.cchiv.androidnotes;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Locale;

public class NoteListAdapter extends ArrayAdapter<String> {

    private Typeface typeface;

    public NoteListAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, 0, objects);

        AssetManager am = this.getContext().getAssets();

        typeface = Typeface.createFromAsset(am,
                String.format(Locale.US, "font/%s", "brandon_bld.otf"));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String noteTitle = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.component_layout, parent,false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.note_info);
        textView.setText(noteTitle);
        textView.setTypeface(typeface);

        return convertView;
    }
}
