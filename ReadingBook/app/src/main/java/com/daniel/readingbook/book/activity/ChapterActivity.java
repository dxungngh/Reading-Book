package com.daniel.readingbook.book.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel.readingbook.book.Config;
import com.daniel.readingbook.book.database.MyDatabaseHelper;
import com.daniel.readingbook.book.database.table.Chapter;
import com.daniel.readingbook.book.widget.ConfirmDialog;
import com.daniel.readingbook.book.widget.ErrorDialog;
import com.daniel.readingbook.huongmattuakhoisuong.R;
import com.startapp.android.publish.SDKAdPreferences;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import java.util.ArrayList;

public class ChapterActivity extends Activity {
    private static final String TAG = ChapterActivity.class.getSimpleName();

    private TextView mTitleTextView;
    private WebView mContentWebView;
    private Chapter mChapter;
    private ArrayList<Chapter> mChapters;
    private ImageView mNextButton, mBackButton;
    private int mTextSize;
    private int mSmallestTextSize;
    private ConfirmDialog mConfirmDialog;
    private ErrorDialog mErrorDialog;
    private StartAppAd mStartAppAd = new StartAppAd(this);

    @Override
    public void onBackPressed() {
        mConfirmDialog = ConfirmDialog.getInstance(this,
            getString(R.string.app_name),
            getString(R.string.chapter_notification),
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePositionOfChapter();
                    mConfirmDialog.dismiss();
                    finish();
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
        addStartApp();
        setContentView(R.layout.activity_chapter);
        StartAppAd.showSlider(this);

        initData();
        setComponentViews();
        drawComponentView();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chapter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chapter_larger:
                mTextSize += 2;
                setTextSize();
                return true;
            case R.id.chapter_smaller:
                mTextSize -= 2;
                mTextSize = Math.max(mTextSize, mSmallestTextSize);
                setTextSize();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mStartAppAd.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStartAppAd.onResume();
    }

    private void addStartApp() {
        StartAppSDK.init(
            this,
            Config.App.DEVELOPER_ID,
            Config.App.APP_ID,
            new SDKAdPreferences()
                .setGender(SDKAdPreferences.Gender.FEMALE));
    }

    private void drawComponentView() {
        mTitleTextView.setText(mChapter.getName());
        if (isOnline()) {
            mContentWebView.setVisibility(View.VISIBLE);
            mChapter.setContent(MyDatabaseHelper.getMyDatabase(this).getContentOfChapter(mChapter.getId()));
            mContentWebView.getSettings().setJavaScriptEnabled(false);
            mContentWebView.loadDataWithBaseURL(null, mChapter.getContent(), "text/html", "UTF-8", null);
            mContentWebView.scrollTo(0, 0);
            mContentWebView.getSettings().setDefaultFontSize(mTextSize);
        } else {
            mContentWebView.setVisibility(View.INVISIBLE);
            mErrorDialog = ErrorDialog.getInstance(this,
                getString(R.string.app_name),
                getString(R.string.error_no_internet),
                getString(R.string.close),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mErrorDialog.dismiss();
                    }
                });
            mErrorDialog.show();
        }

        hideOrShowBackAndNextButton();
    }

    private void hideOrShowBackAndNextButton() {
        if (mChapter.getId() == mChapters.size()) {
            mNextButton.setVisibility(View.INVISIBLE);
        } else {
            mNextButton.setVisibility(View.VISIBLE);
        }
        if (mChapter.getId() == 1) {
            mBackButton.setVisibility(View.INVISIBLE);
        } else {
            mBackButton.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        mChapter = (Chapter) getIntent().getSerializableExtra(Config.Extras.CHAPTER);
        mChapters = (ArrayList<Chapter>) getIntent().getSerializableExtra(Config.Extras.LIST_OF_CHAPTERS);
        mTextSize = (int) getResources().getDimension(R.dimen.default_text);
        mSmallestTextSize = (int) getResources().getDimension(R.dimen.smallest_text);
    }

    private void setBackListener() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int backPosition = (int) mChapter.getId() - 2;
                if (backPosition >= 0) {
                    mChapter = mChapters.get(backPosition);
                    drawComponentView();
                }
            }
        });
    }

    private void setComponentViews() {
        mTitleTextView = (TextView) findViewById(R.id.chapter_title);
        mContentWebView = (WebView) findViewById(R.id.chapter_content);
        mNextButton = (ImageView) findViewById(R.id.chapter_next);
        mBackButton = (ImageView) findViewById(R.id.chapter_back);
    }

    private void setListeners() {
        setNextListener();
        setBackListener();
    }

    private void setNextListener() {
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPosition = (int) mChapter.getId();
                if (mChapters.size() > nextPosition) {
                    mChapter = mChapters.get(nextPosition);
                    drawComponentView();
                    mStartAppAd.showAd(); // show the ad
                    mStartAppAd.loadAd(); // load the next ad
                }
            }
        });
    }

    private void setTextSize() {
        final WebSettings webSettings = mContentWebView.getSettings();
        webSettings.setDefaultFontSize(mTextSize);
    }

    private void savePositionOfChapter() {
        SharedPreferences sharedPref = getSharedPreferences(getPackageName() + "." + getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(getString(R.string.chapter_id), mChapter.getId());
        editor.commit();
    }

    private boolean isOnline() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}