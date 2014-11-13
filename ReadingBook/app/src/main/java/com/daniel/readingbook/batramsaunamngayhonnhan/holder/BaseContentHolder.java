package com.daniel.readingbook.batramsaunamngayhonnhan.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseContentHolder {
    private View mConvertView;

    public View getConvertView() {
        return mConvertView;
    }

    public void setConvertView(View convertView) {
        mConvertView = convertView;
    }

    public abstract void setElements(Object obj);

    public abstract void initHolder(ViewGroup parent, View rowView, int position,
                                    LayoutInflater layoutInflater);
}