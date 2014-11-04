package com.daniel.readingbook.book1.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel.readingbook.book1.Config;
import com.daniel.readingbook.book1.R;
import com.daniel.readingbook.book1.database.table.Chapter;

import java.util.ArrayList;

public class ChapterActivity extends ActionBarActivity {
    private static final String TAG = ChapterActivity.class.getSimpleName();

    private TextView mTitleTextView;
    private WebView mContentWebView;
    private Chapter mChapter;
    private ArrayList<Chapter> mChapters;
    private ImageView mNextButton, mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        initData();
        setComponentViews();
        drawComponentView();
        setListeners();
    }

    private void drawComponentView() {
        mTitleTextView.setText(mChapter.getName());
        mContentWebView.getSettings().setJavaScriptEnabled(false);
        mContentWebView.loadDataWithBaseURL(null, mChapter.getContent(), "text/html", "UTF-8", null);
        mContentWebView.scrollTo(0, 0);

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
}