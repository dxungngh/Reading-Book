package com.daniel.readingbook.batramsaunamngayhonnhan.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daniel.readingbook.batramsaunamngayhonnhan.Config;
import com.daniel.readingbook.batramsaunamngayhonnhan.R;
import com.daniel.readingbook.batramsaunamngayhonnhan.widget.ConfirmDialog;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mIntroductionButton, mReadBookButton, mAddBookButton, mExitButton;
    private ConfirmDialog mConfirmDialog;
    private StartAppAd mStartAppAd = new StartAppAd(this);

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
                        mStartAppAd.onBackPressed();
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
        addStartApp();
        setContentView(R.layout.activity_main);
        setComponentViews();
        setListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
        mStartAppAd.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mStartAppAd.onResume();
    }

    private void addStartApp() {
        StartAppSDK.init(this, Config.App.DEVELOPER_ID, Config.App.APP_ID, true);
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
                mStartAppAd.onBackPressed();
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