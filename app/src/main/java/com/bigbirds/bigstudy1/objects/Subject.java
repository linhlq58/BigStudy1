package com.bigbirds.bigstudy1.objects;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Subject {
    private int id;
    private String name;
    private String place;
    private int teacherID;
    private int dayOfWeek;
    private int beginningPeriod;
    private int endingPeriod;



    private int year;
    private int semester;

//    public static String STRING_ID_PROPERTY = "id";
//    public static String STRING_NAME_PROPERTY = "name";
//    public static String STRING_PLACE_PROPERTY = "place";
//    public static String STRING_TEACHERID_PROPERTY = "teacherID";
//    public static String STRING_DAYOFWEEK_PROPERTY = "dayOfWeek";
//    public static String STRING_BEGINNINGPERIOD_PROPERTY = "beginningPeriod";
//    public static String STRING_ENDINGPERIOD_PROPERTY = "endingPeriod";
    public static String[] PROPERTIES = new String[] {"id", "name", "place", "teacherID", "dayOfWeek", "beginningPeriod", "endingPeriod", "year", "semester"};
    public static String CREATE_SUBJECT_ENTRY =
            "CREATE TABLE IF NOT EXISTS Subject ( " +
                " id INTEGER PRIMARY KEY AUTO INCREAMENT," +
                " name TEXT NOT NULL," +
                " place TEXT NOT NULL," +
                " teacherID INTEGER," +
                " dayOfWeek INTEGER NOT NULL," +
                " beginningPeriod INTEGER NOT NULL," +
                " endingPeriod INTEGER NOT NULL," +
                " year INTEGER NOT NULL," +
                " semester INTEGER NOT NULL" +
            " )";

    public static String DELETE_SUBJECT_ENTRY = "DROP TABLE IF EXISTS Subject";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getBeginningPeriod() {
        return beginningPeriod;
    }

    public void setBeginningPeriod(int beginningPeriod) {
        this.beginningPeriod = beginningPeriod;
    }

    public int getEndingPeriod() {
        return endingPeriod;
    }

    public void setEndingPeriod(int endingPeriod) {
        this.endingPeriod = endingPeriod;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isCollided(Subject other){
        return (dayOfWeek == other.dayOfWeek &&
                !(endingPeriod < other.beginningPeriod || beginningPeriod > other.endingPeriod) && (id != other.id || id == 0));
    }

    public static int getMaxDayOfWeek(ArrayList<Subject> subjects) {
        int max = 2;
        for(Subject sub : subjects)
        {
            if (max < sub.dayOfWeek)
            {
                max = sub.dayOfWeek;
            }
        }

        return max;
    }

    public static int getMinDayOfWeek(ArrayList<Subject> subjects) {
        int min = 8;
        for (Subject sub : subjects)
        {
            if (min > sub.dayOfWeek)
            {
                min = sub.dayOfWeek;
            }
        }

        return min;
    }

    public static Subject getTheNearestSubject(ArrayList<Subject> subjects, int day, int period){
        Subject subject = null;
        int s = 10;

        for(Subject sub : subjects)
        {
            if (sub.dayOfWeek == day && s > sub.beginningPeriod && sub.beginningPeriod > period)
            {
                s = sub.beginningPeriod;
                subject = sub;
            }
        }

        return subject;
    }

    public static Subject getTheSoonestSubjectInWeek(ArrayList<Subject> subjects) {
        Subject subject = null;
        int minDay = getMinDayOfWeek(subjects);

        int minPeriod = 10;
        for (Subject sub : subjects)
        {
            if (sub.dayOfWeek == minDay && minPeriod > sub.beginningPeriod)
            {
                minPeriod = sub.beginningPeriod;
                subject = sub;
            }
        }

        return subject;
    }

    public static Subject getTheLatestSubjectInWeek(ArrayList<Subject> subjects) {
        Subject subject = null;
        int maxDay = getMaxDayOfWeek(subjects);

        int maxPeriod = 0;
        for(Subject sub : subjects)
        {
            if (sub.dayOfWeek == maxDay && maxPeriod < sub.beginningPeriod)
            {
                maxPeriod = sub.beginningPeriod;
                subject = sub;
            }
        }

        return subject;
    }

    public static Subject getTheLatestSubject(ArrayList<Subject> subjects, int day) {
        Subject subject = null;
        int maxPeriod = 0;
        for (Subject sub : subjects)
        {
            if (sub.dayOfWeek == day && maxPeriod < sub.beginningPeriod)
            {
                maxPeriod = sub.beginningPeriod;
                subject = sub;
            }
        }
        return subject;
    }

    public static Subject getTheSoonestSubject(ArrayList<Subject> subjects, int day) {
        Subject subject = null;
        int minPeriod = 10;
        for (Subject sub : subjects)
        {
            if (sub.dayOfWeek == day && minPeriod > sub.beginningPeriod)
            {
                minPeriod = sub.beginningPeriod;
                subject = sub;
            }
        }
        return subject;
    }
}
