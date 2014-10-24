package com.daniel.readingbook.book1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.daniel.readingbook.book1.R;
import com.daniel.readingbook.book1.database.MyDatabase;
import com.daniel.readingbook.book1.database.table.Book;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int SPLASH_TIMEOUT = 2000;
    private Handler mHandler;

    private void checkAccountAndLaunchActivity() {
        MyDatabase myDatabase = new MyDatabase(this);
        Book book = myDatabase.getBook();
        Log.i(TAG, book.toString());
    }

    private void launchSimplePrints() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAccountAndLaunchActivity();
            }
        }, SPLASH_TIMEOUT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launchSimplePrints();
    }
}