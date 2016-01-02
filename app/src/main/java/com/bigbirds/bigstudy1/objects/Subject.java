package com.bigbirds.bigstudy1.objects;

/**
 * Created by L-TP on 01/02/2016.
 */
public class Subject {
    private int id;
    private String name;
    private String place;
    private int teacherID;
    private int dayOfWeek;
    private int beginningPeriod;
    private int endingPeriod;
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

    @Override
    public String toString() {
        return name;
    }
}
