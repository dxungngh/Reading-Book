package com.daniel.readingbook.batramsaunamngayhonnhan.database;

import android.content.Context;

public class MyDatabaseHelper {
    private static final String TAG = MyDatabaseHelper.class.getSimpleName();

    private static MyDatabase myDatabase;

    public static MyDatabase getMyDatabase(Context context) {
        if (myDatabase == null) {
            myDatabase = new MyDatabase(context);
        }
        return myDatabase;
    }
}
