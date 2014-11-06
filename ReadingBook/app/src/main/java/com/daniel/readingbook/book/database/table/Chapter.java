package com.daniel.readingbook.book.database.table;

import java.io.Serializable;

public class Chapter implements Serializable {
    private static final String TAG = Chapter.class.getSimpleName();

    public static final String TABLE_NAME = "chapter";

    public static class Fields {
        public static final String ID = "id";
        public static final String CONTENT = "content";
        public static final String LINK = "link";
        public static final String NAME = "name";
    }

    private long mId;
    private String mContent;
    private String mLink;
    private String mName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String toString() {
        return "[" + mId + "--" + mName + "--" + mLink + "--" + mContent + "]";
    }
}