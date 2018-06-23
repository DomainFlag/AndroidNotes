package com.example.cchiv.androidnotes;

import java.util.ArrayList;

public class Note {

    private String label, content, snippet;
    private ArrayList<Snippet> snippets = null;

    public Note(String label, String content, ArrayList<Snippet> snippets) {
        this.label = label;
        this.content = content;
        this.snippets = snippets;
    }

    public Note(String label, String content, String snippet) {
        this.label = label;
        this.content = content;
        this.snippet = snippet;
    }

    void setLabel() {
        this.label = label;
    }

    void setContent() {
        this.content = content;
    }

    void setSnippet() {
        this.snippet = snippet;
    }

    void setSnippets(ArrayList<Snippet> snippets) {
        this.snippets = snippets;
    }

    String getLabel() {
        return this.label;
    }

    String getContent() { return this.content; }

    String getSnippet() {
        return this.snippet;
    }

    ArrayList<Snippet> getSnippets() {
        return this.snippets;
    }
}
