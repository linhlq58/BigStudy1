package com.bigbirds.bigstudy1.objects;

public class Task {
    private int id;
    private String title;
    private String content;
    private int subjectID;
    private String dateTime;

    public static String[] PROPERTIES =
            new String[] {"id", "title", "content", "subjectID", "dateTime"};

    public static String CREATE_TASK_ENTRY =
            "CREATE TABLE IF NOT EXISTS Task ( " +
                " id INTEGER PRIMARY KEY AUTO INCREAMENT," +
                " title TEXT NOT NULL," +
                " content TEXT," +
                " subjectID TEXT," +
                " dateTime TEXT," +
            " )";
    public static String DELETE_TASK_ENTRY = "DROP TABLE IF EXISTS Task";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
