package com.daniel.readingbook.book.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daniel.readingbook.book.Config;
import com.daniel.readingbook.book.R;
import com.daniel.readingbook.book.widget.ConfirmDialog;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mIntroductionButton, mReadBookButton, mAddBookButton, mExitButton;
    private ConfirmDialog mConfirmDialog;

    @Override
    public void onBackPressed() {
        mConfirmDialog = ConfirmDialog.getInstance(this,
                getString(R.string.app_name),
                getString(R.string.exit_notification),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link = "market://details?id=" + getPackageName();
                        openStore(link);
                        mConfirmDialog.dismiss();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mConfirmDialog.dismiss();
                        finish();
                    }
                }
        );
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    mConfirmDialog.show();
                } catch (Exception e) {
                    Log.e(TAG, "onBackPressed", e);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setComponentViews();
        setListeners();
    }

    private void openStore(String link) {
        Log.i(TAG, link);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    private void setComponentViews() {
        mIntroductionButton = (Button) findViewById(R.id.main_introduction);
        mReadBookButton = (Button) findViewById(R.id.main_read_book);
        mAddBookButton = (Button) findViewById(R.id.main_add_book);
        mExitButton = (Button) findViewById(R.id.main_exit);
    }

    private void setListeners() {
        setIntroductionListener();
        setReadBookListener();
        setAddBookListener();
        setExitListener();
    }

    private void setAddBookListener() {
        mAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStore(Config.App.MORE_APP);
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

    private void setIntroductionListener() {
        mIntroductionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IntroductionActivity.class);
                startActivity(intent);
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
}