package com.bigbirds.bigstudy1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bigbirds.bigstudy1.objects.*;

import java.util.ArrayList;

public final class DatabaseClassHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "local_db.db";

    public DatabaseClassHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Subject.CREATE_SUBJECT_ENTRY);
        db.execSQL(Teacher.CREATE_TEACHER_ENTRY);
        db.execSQL(Task.CREATE_TASK_ENTRY);
        db.execSQL(Note.CREATE_NOTE_ENTRY);
        db.execSQL(Document.CREATE_DOC_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Subject.DELETE_SUBJECT_ENTRY);
        db.execSQL(Teacher.DELETE_TEACHER_ENTRY);
        db.execSQL(Task.DELETE_TASK_ENTRY);
        db.execSQL(Note.DELETE_NOTE_ENTRY);
        db.execSQL(Document.DELETE_DOC_ENTRY);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<Subject> getSubjects(int year, int semester){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> res = new ArrayList<Subject>();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "year=? and semester=?",
                new String[]{Integer.toString(year), Integer.toString(semester)}, null, null, null);
        cursor.moveToFirst();
        while (cursor.isLast()){
            Subject sub = new Subject();
            sub.setId(cursor.getInt(0));
            sub.setName(cursor.getString(1));
            sub.setPlace(cursor.getString(2));
            sub.setTeacherID(cursor.getInt(3));
            sub.setDayOfWeek(cursor.getInt(4));
            sub.setBeginningPeriod(cursor.getInt(5));
            sub.setEndingPeriod(cursor.getInt(6));

            res.add(sub);
            cursor.moveToNext();
        }

        return res;
    }

    public Subject getSubjectById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "id=?", new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        Subject res = new Subject();
        res.setId(cursor.getInt(0));
        res.setName(cursor.getString(1));
        res.setPlace(cursor.getString(2));
        res.setTeacherID(cursor.getInt(3));
        res.setDayOfWeek(cursor.getInt(4));
        res.setBeginningPeriod(cursor.getInt(5));
        res.setEndingPeriod(cursor.getInt(6));

        return res;
    }

    public Subject getSubjectByTime(int dayOfWeek, int beginningPeriod, int year, int semester){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "dayOfWeek=? and beginningPeriod=? and year=? and semester=?"
                , new String[]{Integer.toString(dayOfWeek), Integer.toString(beginningPeriod), Integer.toString(year), Integer.toString(semester)},
                null, null, null);
        cursor.moveToFirst();
        Subject res = new Subject();
        res.setId(cursor.getInt(0));
        res.setName(cursor.getString(1));
        res.setPlace(cursor.getString(2));
        res.setTeacherID(cursor.getInt(3));
        res.setDayOfWeek(cursor.getInt(4));
        res.setBeginningPeriod(cursor.getInt(5));
        res.setEndingPeriod(cursor.getInt(6));

        return res;
    }

    public Teacher getTeacherById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Subject.PROPERTIES, "id=?", new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        Teacher res = new Teacher();
        res.setId(cursor.getInt(0));
        res.setName(cursor.getString(1));
        res.setPhone(cursor.getString(2));
        res.setRoom(cursor.getString(3));
        res.setOtherInfo(cursor.getString(4));

        return res;
    }

    public ArrayList<Teacher> getTeachers(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Teacher.PROPERTIES, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Teacher> res = new ArrayList<Teacher>();
        while (cursor.isLast()){
            Teacher teacher = new Teacher();
            teacher.setId(cursor.getInt(0));
            teacher.setName(cursor.getString(1));
            teacher.setPhone(cursor.getString(2));
            teacher.setRoom(cursor.getString(3));
            teacher.setOtherInfo(cursor.getString(4));

            res.add(teacher);
            cursor.moveToNext();
        }

        return res;
    }

    public Teacher getTheLastTeacher(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Subject.PROPERTIES, null, null , null, null, null);
        cursor.moveToLast();
        Teacher res = new Teacher();
        res.setId(cursor.getInt(0));
        res.setName(cursor.getString(1));
        res.setPhone(cursor.getString(2));
        res.setRoom(cursor.getString(3));
        res.setOtherInfo(cursor.getString(4));

        return res;
    }

    public ArrayList<Note> getNotesBySubjectID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Note", Note.PROPERTIES, "subjectID=?", new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        ArrayList<Note> res = new ArrayList<Note>();
        while (cursor.isLast()){
            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setSubjectID(cursor.getInt(3));

            res.add(note);
            cursor.moveToNext();
        }

        return res;
    }
}