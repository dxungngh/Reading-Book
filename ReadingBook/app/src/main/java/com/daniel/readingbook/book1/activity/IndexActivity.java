package com.daniel.readingbook.book1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.daniel.readingbook.book1.R;

public class IndexActivity extends ActionBarActivity {
    private static final String TAG = IndexActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }
}