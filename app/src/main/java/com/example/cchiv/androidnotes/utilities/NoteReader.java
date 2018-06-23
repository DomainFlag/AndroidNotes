package com.example.cchiv.androidnotes.utilities;

import android.content.Context;
import android.util.Log;

import com.example.cchiv.androidnotes.Note;
import com.example.cchiv.androidnotes.R;
import com.example.cchiv.androidnotes.Snippet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NoteReader {

    private Context context;

    public NoteReader(Context context) {
        this.context = context;
    }

    private JSONObject fetchNote(String noteName) {
        InputStream is = this.context.getResources().openRawResource(R.raw.components);
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

        String data = jsonData.toString();
        if(data.isEmpty())
            return null;

        try {
            JSONArray noteArray = new JSONArray(data);

            JSONObject note;
            for(int it = 0; it < noteArray.length(); it++) {
                note = noteArray.getJSONObject(it);
                if(note.getString("noteName").equals(noteName))
                    return note;
            }
        } catch(JSONException e) {
            Log.v(NoteReader.this.getClass().getCanonicalName(), e.toString());
        }

        return null;
    }

    public ArrayList<Note> fetchNotes(String noteName) {
        JSONObject note = fetchNote(noteName);

        if(note == null)
            return null;

        ArrayList<Note> notes = null;
        try {
            JSONArray jsonArray = note.getJSONArray("noteComponents");
            notes = new ArrayList<>();

            for(int it = 0; it < jsonArray.length(); it++) {
                JSONObject component = jsonArray.getJSONObject(it);

                String label = component.getString("label");
                String content = component.getString("content");

                if(component.has("snippets")) {
                    Object object = component.get("snippets");

                    if(object != null) {
                        if(object instanceof JSONArray) {
                            ArrayList<Snippet> snippets = new ArrayList<>();

                            JSONArray jsonArraySnippets = (JSONArray) object;
                            for(int it1 = 0; it1 < jsonArraySnippets.length(); it1++) {
                                JSONObject jsonObject = jsonArraySnippets.getJSONObject(it1);

                                if(jsonObject.has("snippet") && jsonObject.has("detailed"))
                                    snippets.add(new Snippet(
                                            jsonObject.getString("snippet"),
                                            jsonObject.getString("detailed")
                                    ));
                            }

                            notes.add(new Note(
                                    label, content,
                                    snippets
                            ));
                        } else if(object instanceof String) {
                            notes.add(new Note(
                                    label, content,
                                    (String) object
                            ));
                        }
                    } else {
                        notes.add(new Note(
                                label, content,
                                ""
                        ));
                    }
                }
            }
        } catch(JSONException e) {
            Log.v(this.getClass().getSimpleName(), e.toString());
            return null;
        }

        return notes;
    }
}
