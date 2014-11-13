package com.daniel.readingbook.batramsaunamngayhonnhan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daniel.readingbook.batramsaunamngayhonnhan.database.table.Chapter;
import com.daniel.readingbook.batramsaunamngayhonnhan.holder.ChapterHolder;

import java.util.ArrayList;

public class ChapterAdapter extends BaseAdapter {
    public static final String TAG = ChapterAdapter.class.getSimpleName();

    private ArrayList<Chapter> mChapters;
    private Context mContext;
    private LayoutInflater mInflater;

    public ChapterAdapter(Context context, ArrayList<Chapter> chapters) {
        mContext = context;
        mChapters = chapters;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mChapters == null) {
            return 0;
        }
        return mChapters.size();
    }

    @Override
    public Chapter getItem(int position) {
        return mChapters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Chapter chapter = getItem(position);
        ChapterHolder holder;
        if (convertView == null) {
            holder = new ChapterHolder(mContext, mChapters, chapter);
            holder.initHolder(parent, convertView, position, mInflater);
        } else {
            holder = (ChapterHolder) convertView.getTag();
        }
        holder.setElements(chapter);
        convertView = holder.getConvertView();
        return convertView;
    }
}