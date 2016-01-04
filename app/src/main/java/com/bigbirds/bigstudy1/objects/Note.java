package com.bigbirds.bigstudy1.objects;

/**
 * Created by Admin on 20/12/2015.
 */
public class Note {
    private int id;
    private String title;
    private String content;
    private int subjectID;

    public static String[] PROPERTIES =
            new String[] {"id", "title", "content", "subjectID"};

    public static String CREATE_NOTE_ENTRY =
            "CREATE TABLE IF NOT EXISTS Note ( " +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " title TEXT NOT NULL," +
                " content TEXT," +
                " subjectID INTEGER" +
            " )";

    public static String DELETE_NOTE_ENTRY = "DROP TABLE IF EXISTS Note";

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
}
