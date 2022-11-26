package com.peaceinfotech.sqlitepractise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By BASIL AYYUBI on 26-11-2022.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    static final String DATABASE_NAME = "SqlitePractice.db";
    static final int DATABASE_VERSION = 1;

    static final String TABLE_NAME = "my_library";
    static final String ID = "id";
    static final String COLUMN_TITLE = "book_title";
    static final String COLUMN_AUTHOR = "book_author";
    static final String BOOK_PAGES = "book_pages";
    static final String CONTENT = "content";
    SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + BOOK_PAGES + " INTEGER, "
                + CONTENT + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    long addBook(BookModel model) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, model.bookTitle);
        cv.put(COLUMN_AUTHOR, model.authorName);
        cv.put(BOOK_PAGES, model.pages);
        cv.put(CONTENT, model.content);

        long result = db.insert(TABLE_NAME, null, cv);

        return result;
    }

    List<BookModel> getBooks() {
        db = this.getReadableDatabase();
        List<BookModel> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                int pages = cursor.getInt(3);
                String content = cursor.getString(4);
                list.add(new BookModel(id, title, author, pages , content));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return list;
    }

    List<BookModel> getSingleBook(Integer id1){
        db  = this.getReadableDatabase();
        List<BookModel> list = new ArrayList<>();
        String query = "select * from "+TABLE_NAME+" where id =?";
        Cursor cursor = db.rawQuery(query , new String[] {String.valueOf(id1)});
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                int pages = cursor.getInt(3);
                String content = cursor.getString(4);
                list.add(new BookModel(id, title, author, pages , content));
            }while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

    long updateBook(BookModel model){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE , model.bookTitle);
        cv.put(COLUMN_AUTHOR , model.authorName);
        cv.put(BOOK_PAGES , model.pages);
        cv.put(CONTENT , model.content);

        long  result = db.update(TABLE_NAME , cv , "id=?" ,new String[]{String.valueOf(model.id)});
        db.close();
        return  result;
    }

    long deleteBook(Integer id){
        db = this.getWritableDatabase();
        long result  = db.delete(TABLE_NAME , "id=?" , new String[]{String.valueOf(id)});
        db.close();
        return result;

    }
}
