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
        while (!cursor.isAfterLast()){
            Subject sub = new Subject();
            sub.setId(cursor.getInt(cursor.getColumnIndex("id")));
            sub.setName(cursor.getString(cursor.getColumnIndex("name")));
            sub.setPlace(cursor.getString(cursor.getColumnIndex("place")));
            sub.setTeacherID(cursor.getInt(cursor.getColumnIndex("teacherID")));
            sub.setDayOfWeek(cursor.getInt(cursor.getColumnIndex("dayOfWeek")));
            sub.setBeginningPeriod(cursor.getInt(cursor.getColumnIndex("beginningPeriod")));
            sub.setEndingPeriod(cursor.getInt(cursor.getColumnIndex("endingPeriod")));

            res.add(sub);
            cursor.moveToNext();
        }

        return res;
    }

    public Subject getSubjectById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "id=?", new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        Subject sub = new Subject();
        sub.setId(cursor.getInt(cursor.getColumnIndex("id")));
        sub.setName(cursor.getString(cursor.getColumnIndex("name")));
        sub.setPlace(cursor.getString(cursor.getColumnIndex("place")));
        sub.setTeacherID(cursor.getInt(cursor.getColumnIndex("teacherID")));
        sub.setDayOfWeek(cursor.getInt(cursor.getColumnIndex("dayOfWeek")));
        sub.setBeginningPeriod(cursor.getInt(cursor.getColumnIndex("beginningPeriod")));
        sub.setEndingPeriod(cursor.getInt(cursor.getColumnIndex("endingPeriod")));

        return sub;
    }

    public Subject getSubjectByTime(int dayOfWeek, int beginningPeriod, int year, int semester){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "dayOfWeek=? and beginningPeriod=? and year=? and semester=?"
                , new String[]{Integer.toString(dayOfWeek), Integer.toString(beginningPeriod), Integer.toString(year), Integer.toString(semester)},
                null, null, null);
        cursor.moveToFirst();
        Subject sub = new Subject();
        sub.setId(cursor.getInt(cursor.getColumnIndex("id")));
        sub.setName(cursor.getString(cursor.getColumnIndex("name")));
        sub.setPlace(cursor.getString(cursor.getColumnIndex("place")));
        sub.setTeacherID(cursor.getInt(cursor.getColumnIndex("teacherID")));
        sub.setDayOfWeek(cursor.getInt(cursor.getColumnIndex("dayOfWeek")));
        sub.setBeginningPeriod(cursor.getInt(cursor.getColumnIndex("beginningPeriod")));
        sub.setEndingPeriod(cursor.getInt(cursor.getColumnIndex("endingPeriod")));

        return sub;
    }

    public Teacher getTeacherById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Subject.PROPERTIES, "id=?", new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        Teacher teacher = new Teacher();
        teacher.setId(cursor.getInt(cursor.getColumnIndex("id")));
        teacher.setName(cursor.getString(cursor.getColumnIndex("name")));
        teacher.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        teacher.setRoom(cursor.getString(cursor.getColumnIndex("room")));
        teacher.setOtherInfo(cursor.getString(cursor.getColumnIndex("otherInfo")));

        return teacher;
    }

    public ArrayList<Teacher> getTeachers(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Teacher.PROPERTIES, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Teacher> res = new ArrayList<Teacher>();
        while (!cursor.isAfterLast()){
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
        while (!cursor.isAfterLast()){
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex("id")));
            note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            note.setContent(cursor.getString(cursor.getColumnIndex("content")));
            note.setSubjectID(cursor.getInt(cursor.getColumnIndex("subjectID")));

            res.add(note);
            cursor.moveToNext();
        }

        return res;
    }

    public ArrayList<Task> getTasksBySubjectID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Task", Note.PROPERTIES, "subjectID=?", new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        ArrayList<Task> res = new ArrayList<Task>();
        while (!cursor.isAfterLast()){
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex("id")));
            task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            task.setContent(cursor.getString(cursor.getColumnIndex("content")));
            task.setSubjectID(cursor.getInt(cursor.getColumnIndex("subjectID")));
            task.setDateTime(cursor.getString(cursor.getColumnIndex("dateTime")));

            res.add(task);
            cursor.moveToNext();
        }

        return res;
    }

    public ArrayList<Document> getDocumentsBySubjectID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Document", Note.PROPERTIES, "subjectID=?", new String[]{Integer.toString(id)}, null, null, null);
        cursor.moveToFirst();
        ArrayList<Document> res = new ArrayList<Document>();
        while (!cursor.isAfterLast()){
            Document document = new Document();
            document.setId(cursor.getInt(cursor.getColumnIndex("id")));
            document.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            document.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            document.setSubjectID(cursor.getInt(cursor.getColumnIndex("subjectID")));
            document.setLink(cursor.getString(cursor.getColumnIndex("link")));

            res.add(document);
            cursor.moveToNext();
        }

        return res;
    }
}