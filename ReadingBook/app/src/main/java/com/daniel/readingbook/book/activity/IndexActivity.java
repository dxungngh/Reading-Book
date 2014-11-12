package com.daniel.readingbook.book.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.daniel.readingbook.book.R;
import com.daniel.readingbook.book.adapter.ChapterAdapter;
import com.daniel.readingbook.book.database.MyDatabaseHelper;
import com.daniel.readingbook.book.database.table.Chapter;

import java.util.ArrayList;

public class IndexActivity extends Activity {
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

    @Override
    protected void onResume() {
        super.onResume();
        drawCurrentChapter();
    }

    private void drawComponentView() {
        mAdapter = new ChapterAdapter(this, mChapters);
        mChaptersListView.setAdapter(mAdapter);
    }

    private void drawCurrentChapter() {
        getCurrentChapter();
        mCurrentChapterTextView.setText(mCurrentChapter.getName());
    }

    private void getCurrentChapter() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        long chapterId = sharedPref.getLong(getString(R.string.chapter_id), 1);
        mCurrentChapter = mChapters.get((int) chapterId - 1);
    }

    private void initData() {
        mChapters = MyDatabaseHelper.getMyDatabase(this).getAllChapters();
    }

    private void setComponentView() {
        mChaptersListView = (ListView) findViewById(R.id.index_list_of_chapters);
        mCurrentChapterTextView = (TextView) findViewById(R.id.index_current_chapter);
    }
}