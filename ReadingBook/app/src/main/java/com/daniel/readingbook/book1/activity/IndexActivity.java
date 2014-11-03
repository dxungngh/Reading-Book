package com.daniel.readingbook.book1.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.daniel.readingbook.book1.R;
import com.daniel.readingbook.book1.adapter.ChapterAdapter;
import com.daniel.readingbook.book1.database.MyDatabaseHelper;
import com.daniel.readingbook.book1.database.table.Chapter;

import java.util.ArrayList;

public class IndexActivity extends ActionBarActivity {
    private static final String TAG = IndexActivity.class.getSimpleName();

    private ListView mChaptersListView;
    private ArrayList<Chapter> mChapters;
    private ChapterAdapter mAdapter;

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
    }

    private void initData() {
        mChapters = MyDatabaseHelper.getMyDatabase(this).getAllChapters();
    }

    private void setComponentView() {
        mChaptersListView = (ListView) findViewById(R.id.index_list_of_chapters);
    }
}