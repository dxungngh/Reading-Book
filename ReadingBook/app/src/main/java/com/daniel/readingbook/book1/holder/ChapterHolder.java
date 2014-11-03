package com.daniel.readingbook.book1.holder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daniel.readingbook.book1.Config;
import com.daniel.readingbook.book1.R;
import com.daniel.readingbook.book1.activity.ChapterActivity;
import com.daniel.readingbook.book1.database.table.Chapter;

import java.util.ArrayList;

public class ChapterHolder extends BaseContentHolder {
    private Chapter mChapter;
    private ArrayList<Chapter> mChapters;
    private Context mContext;
    private ChapterHolder mHolder;
    private TextView mName;
    private RelativeLayout mRowAlbumLayout;
    private ImageView mThumbnail;

    private static final String TAG = ChapterHolder.class.getSimpleName();

    public ChapterHolder(Context context, ArrayList<Chapter> chapters, Chapter chapter) {
        mChapter = chapter;
        mChapters = chapters;
        mContext = context;
        mHolder = this;
    }

    private void assignComponentView(View rowView) {
        mThumbnail = (ImageView) rowView.findViewById(R.id.row_chapter_thumbnail);
        mName = (TextView) rowView.findViewById(R.id.row_chapter_name);
        mRowAlbumLayout = (RelativeLayout) rowView.findViewById(R.id.row_album);
    }

    private void drawComponentView() {
        mName.setText(mChapter.getName());
        mThumbnail.setImageResource(R.drawable.ic_launcher);
    }

    @Override
    public void initHolder(ViewGroup parent, View rowView, int position,
                           LayoutInflater layoutInflater) {
        rowView = layoutInflater.inflate(R.layout.row_chapter, parent, false);
        assignComponentView(rowView);
        setConvertView(rowView);
        setALlListeners();
        rowView.setTag(mHolder);
    }

    private void setALlListeners() {
        setAlbumClickListener();
    }

    @Override
    public void setElements(Object obj) {
        mChapter = (Chapter) obj;
        drawComponentView();
    }

    private void setAlbumClickListener() {
        mRowAlbumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChapterActivity.class);
                intent.putExtra(Config.Extras.CHAPTER, mChapter);
                intent.putExtra(Config.Extras.LIST_OF_CHAPTERS, mChapters);
                mContext.startActivity(intent);
            }
        });
    }
}