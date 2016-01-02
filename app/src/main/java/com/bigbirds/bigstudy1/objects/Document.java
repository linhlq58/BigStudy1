package com.bigbirds.bigstudy1.objects;

/**
 * Created by Admin on 27/12/2015.
 */
public class Document {
    private int id;
    private String title;
    private String description;
    private int subjectID;
    private String link;

    public static String[] PROPERTIES =
            new String[] {"id", "title", "description", "subjectID", "link"};

    public static String CREATE_DOC_ENTRY =
            "CREATE TABLE IF NOT EXISTS Document ( " +
                " id INTEGER PRIMARY KEY AUTO INCREAMENT," +
                " title TEXT NOT NULL," +
                " description TEXT," +
                " subjectID TEXT," +
                " link TEXT" +
            " )";

    public static String DELETE_DOC_ENTRY = "DROP TABLE IF EXISTS Document";
}
