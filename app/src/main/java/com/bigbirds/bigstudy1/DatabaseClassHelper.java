package com.bigbirds.bigstudy1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bigbirds.bigstudy1.objects.*;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Calendar;

public final class DatabaseClassHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "local_db.db";

    private DatabaseClassHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseClassHelper instance;

    public static void createInstance(Context context){
        if (instance == null) instance = new DatabaseClassHelper(context);
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

    //Get references of type class
    ////////////////////////////////////////////////////////////////
    public ArrayList<Subject> getSubjects(int year, int semester){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> res = new ArrayList<Subject>();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "year=? and semester=?",
                new String[]{Integer.toString(year), Integer.toString(semester)}, null, null, null);
        try{
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
        }
        catch(Exception e){}

        db.close();
        return res;
    }

    public Subject getSubjectById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "id=?", new String[]{Integer.toString(id)}, null, null, null);
        Subject sub = new Subject();
        try{
            cursor.moveToFirst();
        
            sub.setId(cursor.getInt(cursor.getColumnIndex("id")));
            sub.setName(cursor.getString(cursor.getColumnIndex("name")));
            sub.setPlace(cursor.getString(cursor.getColumnIndex("place")));
            sub.setTeacherID(cursor.getInt(cursor.getColumnIndex("teacherID")));
            sub.setDayOfWeek(cursor.getInt(cursor.getColumnIndex("dayOfWeek")));
            sub.setBeginningPeriod(cursor.getInt(cursor.getColumnIndex("beginningPeriod")));
            sub.setEndingPeriod(cursor.getInt(cursor.getColumnIndex("endingPeriod")));
        }
        catch(Exception e){}
        

        db.close();
        return sub;
    }

    public Subject getSubjectByTime(int dayOfWeek, int beginningPeriod, int year, int semester){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "dayOfWeek=? and beginningPeriod=? and year=? and semester=?"
                , new String[]{Integer.toString(dayOfWeek), Integer.toString(beginningPeriod), Integer.toString(year), Integer.toString(semester)},
                null, null, null);
        Subject sub = new Subject();
        try{
            cursor.moveToFirst();
            
            sub.setId(cursor.getInt(cursor.getColumnIndex("id")));
            sub.setName(cursor.getString(cursor.getColumnIndex("name")));
            sub.setPlace(cursor.getString(cursor.getColumnIndex("place")));
            sub.setTeacherID(cursor.getInt(cursor.getColumnIndex("teacherID")));
            sub.setDayOfWeek(cursor.getInt(cursor.getColumnIndex("dayOfWeek")));
            sub.setBeginningPeriod(cursor.getInt(cursor.getColumnIndex("beginningPeriod")));
            sub.setEndingPeriod(cursor.getInt(cursor.getColumnIndex("endingPeriod")));
        }
        catch (Exception e){}

        db.close();
        return sub;
    }

    public ArrayList<Subject> getSubjectsByTime(int dayOfWeek, int year, int semester){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Subject> res = new ArrayList<Subject>();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "dayOfWeek=? and year=? and semester=?",
                new String[]{Integer.toString(dayOfWeek), Integer.toString(year), Integer.toString(semester)}, null, null, null);
        
        try{
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
        }
        catch(Exception e) {}

        db.close();
        return res;
    }

    public Teacher getTeacherById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Teacher.PROPERTIES, "id=?", new String[]{Integer.toString(id)}, null, null, null);
        Teacher teacher = new Teacher();
        try{
            cursor.moveToFirst();
            teacher.setId(cursor.getInt(cursor.getColumnIndex("id")));
            teacher.setName(cursor.getString(cursor.getColumnIndex("name")));
            teacher.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            teacher.setRoom(cursor.getString(cursor.getColumnIndex("room")));
            teacher.setOtherInfo(cursor.getString(cursor.getColumnIndex("otherInfo")));
        }
        catch(Exception e){}

        db.close();
        return teacher;
    }

    public ArrayList<Teacher> getTeachers(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Teacher.PROPERTIES, null, null, null, null, null);
        ArrayList<Teacher> res = new ArrayList<Teacher>();

        try{
            cursor.moveToFirst();
        
            while (!cursor.isAfterLast()){
                Teacher teacher = new Teacher();
                teacher.setId(cursor.getInt(cursor.getColumnIndex("id")));
                teacher.setName(cursor.getString(cursor.getColumnIndex("name")));
                teacher.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                teacher.setRoom(cursor.getString(cursor.getColumnIndex("room")));
                teacher.setOtherInfo(cursor.getString(cursor.getColumnIndex("otherInfo")));

                res.add(teacher);
                cursor.moveToNext();
            }
        }
        catch(Exception e){}

        db.close();
        return res;
    }

    public Teacher getTheLastTeacher(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Teacher.PROPERTIES, null, null , null, null, null);
        Teacher teacher = new Teacher();

        try{
            cursor.moveToLast();
        
            teacher.setId(cursor.getInt(cursor.getColumnIndex("id")));
            teacher.setName(cursor.getString(cursor.getColumnIndex("name")));
            teacher.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            teacher.setRoom(cursor.getString(cursor.getColumnIndex("room")));
            teacher.setOtherInfo(cursor.getString(cursor.getColumnIndex("otherInfo")));
        }
        catch(Exception e){}

        db.close();
        return teacher;
    }

    public Integer getLastInsertedTeacherRowID(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Teacher", Teacher.PROPERTIES, null, null, null, null, null);
        Integer i = null;
        try{
            cursor.moveToLast();
            i = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
        }
        catch (Exception e){}

        db.close();

        return i;
    }

    public ArrayList<Note> getNotesBySubjectID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Note", Note.PROPERTIES, "subjectID=?", new String[]{Integer.toString(id)}, null, null, null);
        ArrayList<Note> res = new ArrayList<Note>();

        try{
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                note.setContent(cursor.getString(cursor.getColumnIndex("content")));
                note.setSubjectID(cursor.getInt(cursor.getColumnIndex("subjectID")));

                res.add(note);
                cursor.moveToNext();
            }
        }
        catch(Exception e){}

        db.close();
        return res;
    }

    public ArrayList<Note> getNotesByDay(int dayOfWeek){
        ArrayList<Note> listNote = new ArrayList<>();

        String query = "SELECT * FROM Note n JOIN Subject s WHERE n.subjectId = s.id AND s.dayOfWeek = " + dayOfWeek;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setSubjectID(cursor.getInt(3));

                listNote.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listNote;
    }

    public ArrayList<Task> getTasksByDay(int dayOfWeek){
        ArrayList<Task> listTask = new ArrayList<>();

        String query = "SELECT * FROM Task n JOIN Subject s WHERE n.subjectId = s.id AND s.dayOfWeek = " + dayOfWeek;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setTitle(cursor.getString(1));
                task.setContent(cursor.getString(2));
                task.setSubjectID(cursor.getInt(3));
                task.setDateTime(cursor.getString(4));

                listTask.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listTask;
    }

    public ArrayList<Task> getTasksBySubjectID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Task", Task.PROPERTIES, "subjectID=?", new String[]{Integer.toString(id)}, null, null, "id desc");
        ArrayList<Task> res = new ArrayList<Task>();

        try{
            cursor.moveToFirst();
        
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
        }
        catch(Exception e){}

        db.close();
        return res;
    }

    public ArrayList<Task> getTasksInAWeek(int year, int semester){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Subject", Subject.PROPERTIES, "year=? and semester=?",
                new String[]{Integer.toString(year), Integer.toString(semester)}, null, null, null);
        ArrayList<Task> res = new ArrayList<Task>();

        try{
            cursor.moveToFirst();
            ArrayList<Integer> subjectIDs = new ArrayList<Integer>();
            while (!cursor.isAfterLast()){
                Integer i = cursor.getInt(cursor.getColumnIndex("id"));
                try{
                    subjectIDs.add(i);
                }
                catch (Exception e){};
            }
            cursor.close();
            
            for (Integer i : subjectIDs){
                cursor = db.query("Task", Task.PROPERTIES, "subjectID=?", new String[]{i.toString()}, null, null, null);

                try{
                    while (!cursor.isAfterLast()){
                        long value = Long.parseLong(cursor.getString(cursor.getColumnIndex("dateTime")));
                        long oneWeek = 1000*60*60*24;
                        if (value > Calendar.getInstance().getTimeInMillis() && value <= Calendar.getInstance().getTimeInMillis() + oneWeek){
                            Task task = new Task();
                            task.setId(cursor.getInt(cursor.getColumnIndex("id")));
                            task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                            task.setContent(cursor.getString(cursor.getColumnIndex("content")));
                            task.setSubjectID(cursor.getInt(cursor.getColumnIndex("subjectID")));
                            task.setDateTime(cursor.getString(cursor.getColumnIndex("dateTime")));

                            res.add(task);
                        }

                        cursor.moveToNext();
                    }
                }
                catch(Exception e){}
            }
            Task.sortByTime(res);
        }
        catch(Exception e){}

        db.close();
        return res;
    }

    public ArrayList<Document> getDocumentsBySubjectID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Document", Document.PROPERTIES, "subjectID=?", new String[]{Integer.toString(id)}, null, null, null);
        
        ArrayList<Document> res = new ArrayList<Document>();

        try{
            cursor.moveToFirst();
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
        }
        catch(Exception e){}

        db.close();
        return res;
    }


    //Insertion
    //////////////////////////////////////////////////////////////////
    public void insert(Subject subject) throws SQLDataException {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Subject.PROPERTIES.length);
            values.put("name", subject.getName());
            values.put("place", subject.getPlace());
            values.put("teacherID", subject.getTeacherID());
            values.put("dayOfWeek", subject.getDayOfWeek());
            values.put("beginningPeriod", subject.getBeginningPeriod());
            values.put("endingPeriod", subject.getEndingPeriod());
            values.put("year", subject.getYear());
            values.put("semester", subject.getSemester());

            db.insert("Subject", null, values);
            db.close();
        }
        else throw new SQLDataException();
    }

    public void insert(Teacher teacher) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Teacher.PROPERTIES.length);
            values.put("name", teacher.getName());
            values.put("phone", teacher.getPhone());
            values.put("room", teacher.getRoom());
            values.put("otherInfo", teacher.getOtherInfo());

            db.insert("Teacher", null, values);
            db.close();
        }
        else throw new SQLDataException();
    }

    public void insert(Note note) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Note.PROPERTIES.length);
            values.put("title", note.getTitle());
            values.put("content", note.getContent());
            values.put("subjectID", note.getSubjectID());

            db.insert("Note", null, values);
            db.close();
        }
        else throw new SQLDataException();
    }

    public void insert(Task task) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Task.PROPERTIES.length);
            values.put("title", task.getTitle());
            values.put("content", task.getContent());
            values.put("subjectID", task.getSubjectID());
            values.put("dateTime", task.getDateTime());

            db.insert("Task", null, values);
            db.close();
        }
        else throw new SQLDataException();
    }

    public void insert(Document document) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Document.PROPERTIES.length);
            values.put("title", document.getTitle());
            values.put("description", document.getDescription());
            values.put("subjectID", document.getSubjectID());
            values.put("link", document.getLink());

            db.insert("Document", null, values);
            db.close();
        }
        else throw new SQLDataException();
    }


    //Deletion
    ///////////////////////////////////////////////////////////////////
    public void delete(Subject subject) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            db.delete("Subject", "id=?", new String[]{Integer.toString(subject.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void delete(Teacher teacher) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            db.delete("Teacher", "id=?", new String[]{Integer.toString(teacher.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void delete(Note note) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.delete("Note", "id=?", new String[]{Integer.toString(note.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void delete(Task task) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.delete("Task", "id=?", new String[]{Integer.toString(task.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void delete(Document document) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            db.delete("Document", "id=?", new String[]{Integer.toString(document.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void deleteByID(String type, int id) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.delete(type, "id=?", new String[]{Integer.toString(id)});
            db.close();
        }
        else throw new SQLDataException();
    }


    //Update
    ///////////////////////////////////////////////////////////////////
    public void update(Subject subject) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Subject.PROPERTIES.length);
            values.put("name", subject.getName());
            values.put("place", subject.getPlace());
            values.put("teacherID", subject.getTeacherID());
            values.put("dayOfWeek", subject.getDayOfWeek());
            values.put("beginningPeriod", subject.getBeginningPeriod());
            values.put("endingPeriod", subject.getEndingPeriod());
            values.put("year", subject.getYear());
            values.put("semester", subject.getSemester());

            db.update("Subject", values, "id=?", new String[]{Integer.toString(subject.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void update(Teacher teacher) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Teacher.PROPERTIES.length);
            values.put("name", teacher.getName());
            values.put("phone", teacher.getPhone());
            values.put("room", teacher.getRoom());
            values.put("otherInfo", teacher.getOtherInfo());

            db.update("Teacher", values, "id=?", new String[]{Integer.toString(teacher.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void update(Note note) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Note.PROPERTIES.length);
            values.put("title", note.getTitle());
            values.put("content", note.getContent());
            values.put("subjectID", note.getSubjectID());

            db.update("Note", values, "id=?", new String[]{Integer.toString(note.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void update(Task task) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Task.PROPERTIES.length);
            values.put("title", task.getTitle());
            values.put("content", task.getContent());
            values.put("subjectID", task.getSubjectID());
            values.put("dateTime", task.getDateTime());

            db.update("Task", values, "id=?", new String[]{Integer.toString(task.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }

    public void update(Document document) throws SQLDataException{
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            ContentValues values = new ContentValues(Document.PROPERTIES.length);
            values.put("title", document.getTitle());
            values.put("description", document.getDescription());
            values.put("subjectID", document.getSubjectID());
            values.put("link", document.getLink());

            db.update("Document", values, "id=?", new String[]{Integer.toString(document.getId())});
            db.close();
        }
        else throw new SQLDataException();
    }
}