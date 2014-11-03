package com.daniel.readingbook.book1.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.daniel.readingbook.book1.Config;
import com.daniel.readingbook.book1.R;
import com.daniel.readingbook.book1.database.table.Chapter;

public class ChapterActivity extends ActionBarActivity {
    private static final String TAG = ChapterActivity.class.getSimpleName();

    private TextView mTitleTextView;
    private WebView mContentWebView;
    private Chapter mChapter;

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
        mContentWebView.loadData(mChapter.getContent(), "text/html", "UTF-8");
    }

    private void initData() {
        mChapter = (Chapter) getIntent().getSerializableExtra(Config.Extras.CHAPTER);
    }

    private void setComponentViews() {
        mTitleTextView = (TextView) findViewById(R.id.chapter_title);
        mContentWebView = (WebView) findViewById(R.id.chapter_content);
    }

    private void setListeners() {
    }
}