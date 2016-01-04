package com.bigbirds.bigstudy1.objects;

/**
 * Created by L-TP on 01/02/16.
 */
public class Teacher {
    private int id;
    private String name;
    private String phone;
    private String room;
    private String otherInfo;

    public static String[] PROPERTIES = new String[] {"id", "name", "phone", "room", "otherInfo"};
    public static String CREATE_TEACHER_ENTRY =
            "CREATE TABLE IF NOT EXISTS Teacher ( " +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL," +
                " phone TEXT," +
                " room TEXT," +
                " otherInfo TEXT" +
            " )";


    public static String DELETE_TEACHER_ENTRY = "DROP TABLE IF EXISTS Teacher";

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    @Override
    public String toString() {
        return name;
    }
}
