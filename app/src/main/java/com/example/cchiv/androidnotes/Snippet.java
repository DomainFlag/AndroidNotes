package com.example.cchiv.androidnotes;

public class Snippet {

    private String snippet;
    private String detailed;

    public Snippet(String snippet, String detailed) {
        this.snippet = snippet;
        this.detailed = detailed;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getSnippet() {
        return this.snippet;
    }

    public String getDetailed() {
        return this.detailed;
    }
}
