package com.daniel.readingbook.book.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daniel.readingbook.book.R;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mReadBookButton, mAddBookButton, mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setComponentViews();
        setListeners();
    }

    private void setComponentViews() {
        mReadBookButton = (Button) findViewById(R.id.main_read_book);
        mAddBookButton = (Button) findViewById(R.id.main_add_book);
        mExitButton = (Button) findViewById(R.id.main_exit);
    }

    private void setListeners() {
        setReadBookListener();
        setAddBookListener();
        setExitListener();
    }

    private void setAddBookListener() {
        mAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setReadBookListener() {
        mReadBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setExitListener() {
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}