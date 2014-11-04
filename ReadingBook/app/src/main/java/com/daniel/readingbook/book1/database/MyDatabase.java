package com.daniel.readingbook.book1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.daniel.readingbook.book1.database.table.Book;
import com.daniel.readingbook.book1.database.table.Chapter;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteAssetHelper {
    private static final String TAG = MyDatabase.class.getSimpleName();

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
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

    public ArrayList<Chapter> getAllChapters() {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {
            Chapter.Fields.ID,
            Chapter.Fields.NAME};
        String sqlTables = Chapter.TABLE_NAME;

        queryBuilder.setTables(sqlTables);
        Cursor cursor = queryBuilder.query(database, sqlSelect, null, null, null, null, Chapter.Fields.ID);

        ArrayList<Chapter> chapters = new ArrayList<Chapter>();
        while (cursor.moveToNext()) {
            Chapter chapter = new Chapter();
            chapter.setId(cursor.getLong(0));
            chapter.setName(cursor.getString(1));
            chapters.add(chapter);
        }

        return chapters;
    }

    public String getContentOfChapter(long id) {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {Chapter.Fields.CONTENT};
        String sqlTables = Chapter.TABLE_NAME;

        queryBuilder.setTables(sqlTables);
        String whereClause = Chapter.Fields.ID + "=" + id;
        queryBuilder.appendWhere(whereClause);
        Cursor cursor = queryBuilder.query(database, sqlSelect, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor != null) {
            return cursor.getString(0);
        }
        return null;
    }
}