package com.example.cchiv.androidnotes.notes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class IntentsActivitiesActivity extends AppCompatActivity {

    public IntentsActivitiesActivity() {
        super();
    }

    public IntentsActivitiesActivity(Activity activity) {
        super();
        doSomething();
    }

    public void doSomething() {
        Log.v(this.getClass().getSimpleName(), "Yeahhhhhhhhhhhhhhhhhhhhhhh!");
    }
}
