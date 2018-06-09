package com.example.cchiv.androidnotes;

public class Component {

    private String label, content, snippet;

    Component(String label, String content, String snippet) {
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

    String getLabel() {
        return this.label;
    }

    String getContent() {
        return this.content;
    }

    String getSnippet() {
        return this.snippet;
    }
}
