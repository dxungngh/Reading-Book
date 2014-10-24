package com.daniel.readingbook.app.database.table;

import java.io.Serializable;

public class Book implements Serializable {
    private static final String TAG = Book.class.getSimpleName();

    public static final String TABLE_NAME = "book";

    public static class Fields {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String AUTHOR = "author";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String OVERVIEW = "overview";
    }

    private long mId;
    private String mName;
    private String mAuthor;
    private String mType;
    private String mStatus;
    private String mOverview;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String toString() {
        return "[" + mId + "--" + mName + "--" + mAuthor + "--" + mType + "--" + mStatus + "--" + mOverview + "]";
    }
}