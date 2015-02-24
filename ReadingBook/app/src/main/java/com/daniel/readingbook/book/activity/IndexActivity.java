package com.daniel.readingbook.book.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.daniel.readingbook.book.Config;
import com.daniel.readingbook.huongmattuakhoisuong.R;
import com.daniel.readingbook.book.adapter.ChapterAdapter;
import com.daniel.readingbook.book.database.MyDatabaseHelper;
import com.daniel.readingbook.book.database.table.Chapter;
import com.startapp.android.publish.SDKAdPreferences;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import java.util.ArrayList;

public class IndexActivity extends Activity {
    private static final String TAG = IndexActivity.class.getSimpleName();

    private ChapterAdapter mAdapter;
    private ArrayList<Chapter> mChapters;
    private ListView mChaptersListView;
    private Chapter mCurrentChapter;
    private Button mCurrentChapterButton;
    private StartAppAd mStartAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addStartApp();
        setContentView(R.layout.activity_index);
        StartAppAd.showSlider(this);

        initData();
        setComponentView();
        drawComponentView();
        setCurrentChapterListener();
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
        drawCurrentChapter();
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
        mAdapter = new ChapterAdapter(this, mChapters);
        mChaptersListView.setAdapter(mAdapter);
    }

    private void drawCurrentChapter() {
        getCurrentChapter();
        mCurrentChapterButton.setText(mCurrentChapter.getName());
    }

    private void getCurrentChapter() {
        SharedPreferences sharedPref = getSharedPreferences(getPackageName() + "." + getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
        long chapterId = sharedPref.getLong(getString(R.string.chapter_id), 1);
        mCurrentChapter = mChapters.get((int) chapterId - 1);
    }

    private void initData() {
        mChapters = MyDatabaseHelper.getMyDatabase(this).getAllChapters();
    }

    private void setComponentView() {
        mChaptersListView = (ListView) findViewById(R.id.index_list_of_chapters);
        mCurrentChapterButton = (Button) findViewById(R.id.index_current_chapter);
    }

    private void setCurrentChapterListener() {
        mCurrentChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, ChapterActivity.class);
                intent.putExtra(Config.Extras.CHAPTER, mCurrentChapter);
                intent.putExtra(Config.Extras.LIST_OF_CHAPTERS, mChapters);
                startActivity(intent);
            }
        });
    }
}