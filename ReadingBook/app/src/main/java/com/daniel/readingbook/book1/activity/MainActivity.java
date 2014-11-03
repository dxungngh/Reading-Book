package com.daniel.readingbook.book1.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.daniel.readingbook.book1.R;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}