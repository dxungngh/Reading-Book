package com.daniel.readingbook.book1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel.readingbook.book1.Config;
import com.daniel.readingbook.book1.R;
import com.daniel.readingbook.book1.database.table.Chapter;
import com.daniel.readingbook.book1.widget.ConfirmDialog;

import java.util.ArrayList;

public class ChapterActivity extends ActionBarActivity {
    private static final String TAG = ChapterActivity.class.getSimpleName();

    private TextView mTitleTextView;
    private WebView mContentWebView;
    private Chapter mChapter;
    private ArrayList<Chapter> mChapters;
    private ImageView mNextButton, mBackButton;
    private int mTextSize;
    private int mSmallestTextSize;
    private ConfirmDialog mConfirmDialog;

    @Override
    public void onBackPressed() {
        mConfirmDialog = ConfirmDialog.getInstance(this,
            getString(R.string.app_name),
            getString(R.string.chapter_notification),
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        setContentView(R.layout.activity_chapter);

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

    private void drawComponentView() {
        mTitleTextView.setText(mChapter.getName());
        mContentWebView.getSettings().setJavaScriptEnabled(false);
        mContentWebView.loadDataWithBaseURL(null, mChapter.getContent(), "text/html", "UTF-8", null);
        mContentWebView.scrollTo(0, 0);
        mContentWebView.getSettings().setDefaultFontSize(mTextSize);

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
                }
            }
        });
    }

    private void setTextSize() {
        final WebSettings webSettings = mContentWebView.getSettings();
        webSettings.setDefaultFontSize(mTextSize);
    }
}