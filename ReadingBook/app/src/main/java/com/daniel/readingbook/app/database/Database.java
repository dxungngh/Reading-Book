package com.daniel.readingbook.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.daniel.readingbook.app.database.table.Book;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {
    private static final String TAG = Database.class.getSimpleName();

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Book getBook() {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {
            Book.Fields.ID,
            Book.Fields.AUTHOR,
            Book.Fields.NAME,
            Book.Fields.OVERVIEW,
            Book.Fields.STATUS,
            Book.Fields.TYPE};
        String sqlTables = Book.TABLE_NAME;

        queryBuilder.setTables(sqlTables);
        Cursor cursor = queryBuilder.query(database, sqlSelect, null, null, null, null, null);
        cursor.moveToFirst();

        Book book = new Book();
        book.setId(cursor.getLong(0));
        book.setAuthor(cursor.getString(1));
        book.setName(cursor.getString(2));
        book.setOverview(cursor.getString(3));
        book.setStatus(cursor.getString(4));
        book.setType(cursor.getString(5));

        return book;
    }
}