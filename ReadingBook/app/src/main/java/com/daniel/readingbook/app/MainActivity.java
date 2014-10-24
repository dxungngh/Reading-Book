package com.daniel.readingbook.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int SPLASH_TIMEOUT = 2000;
    private Handler mHandler;

    private void checkAccountAndLaunchActivity() {
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