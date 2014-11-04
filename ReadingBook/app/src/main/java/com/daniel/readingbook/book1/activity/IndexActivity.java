package com.daniel.readingbook.book1.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.daniel.readingbook.book1.R;
import com.daniel.readingbook.book1.adapter.ChapterAdapter;
import com.daniel.readingbook.book1.database.MyDatabaseHelper;
import com.daniel.readingbook.book1.database.table.Chapter;

import java.util.ArrayList;

public class IndexActivity extends ActionBarActivity {
    private static final String TAG = IndexActivity.class.getSimpleName();

    private ChapterAdapter mAdapter;
    private ArrayList<Chapter> mChapters;
    private ListView mChaptersListView;
    private Chapter mCurrentChapter;
    private TextView mCurrentChapterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initData();
        setComponentView();
        drawComponentView();
    }

    private void drawComponentView() {
        mAdapter = new ChapterAdapter(this, mChapters);
        mChaptersListView.setAdapter(mAdapter);
        mCurrentChapterTextView.setText(mCurrentChapter.getName());
    }

    private void getCurrentChapter() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        long chapterId = sharedPref.getInt(getString(R.string.chapter_id), 1);
        mCurrentChapter = mChapters.get((int) chapterId - 1);
    }

    private void initData() {
        mChapters = MyDatabaseHelper.getMyDatabase(this).getAllChapters();
        getCurrentChapter();
    }

    private void setComponentView() {
        mChaptersListView = (ListView) findViewById(R.id.index_list_of_chapters);
        mCurrentChapterTextView = (TextView) findViewById(R.id.index_current_chapter);
    }
}