package com.bigbirds.bigstudy1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bigbirds.bigstudy1.objects.Subject;

import java.util.Objects;

public final class DatabaseClassHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "local_db.db";

    public DatabaseClassHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Subject.CREATE_SUBJECT_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Subject.DELETE_SUBJECT_ENTRY);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Subject getSubjectById(int id){
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "id", new String[]{Integer.toString(id)}, null, null, null);
//        cursor.moveToFirst();
//        while (cursor.isLast()){
//            cursor.moveToNext();
//        }
        return null;
    }

    public Subject getSubjectByTime(int dayOfWeek, int beginningPeriod, int year, int semester){
        return null;
    }
}