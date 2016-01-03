package com.bigbirds.bigstudy1;

import com.bigbirds.bigstudy1.objects.Document;
import com.bigbirds.bigstudy1.objects.Note;
import com.bigbirds.bigstudy1.objects.Subject;
import com.bigbirds.bigstudy1.objects.Task;
import com.bigbirds.bigstudy1.objects.Teacher;
import java.util.ArrayList;
import java.util.Calendar;

public class DataHandler {
    public static boolean saveSubjectTeacher(Subject subject, Teacher teacher, int indexOfTeacherSelection, boolean isEdited){
        if (subject == null)
            return false;
        boolean isCollided = false;
        ArrayList<Subject> subjects = DatabaseClassHelper.instance.getSubjectsByTime(subject.getDayOfWeek(), subject.getYear(), subject.getSemester());
        for (Subject sub : subjects)
        {
            isCollided = subject.isCollided(sub);
            if (isCollided)
                break;
        }
        if (!isCollided)
        {
            //create new mode
            if (!isEdited)
            {
                try{
                    DatabaseClassHelper.instance.insert(subject);
                }
                catch (Exception e){
                    return false;
                }
                if (indexOfTeacherSelection < 0)
                {
                    if (teacher != null && teacher.getName() != null && teacher.getName().length() > 0)
                    {
                        try{
                            DatabaseClassHelper.instance.insert(teacher);
                            subject.setTeacherID(DatabaseClassHelper.instance.getIDOfTheLastTeacher());
                            try {
                                DatabaseClassHelper.instance.update(subject);
                            }
                            catch (Exception e){
                                return false;
                            }
                        }
                        catch (Exception e){
                            return false;
                        }
                    }
                }
                else
                {
                    subject.setTeacherID(teacher.getId());
                    try {
                        DatabaseClassHelper.instance.update(subject);
                    }
                    catch (Exception e){
                        return false;
                    }
                }

                subject.setId(0);
            }
            //edit mode
            else {
                if (indexOfTeacherSelection < 0)
                {
                    if (teacher != null && teacher.getName() != null && teacher.getName().length() > 0)
                    {
                        if (subject.getTeacherID() <= 0)
                        {
                            try{
                                DatabaseClassHelper.instance.insert(teacher);
                                subject.setTeacherID(DatabaseClassHelper.instance.getIDOfTheLastTeacher());
                                try {
                                    DatabaseClassHelper.instance.update(subject);
                                }
                                catch (Exception e){
                                    return false;
                                }
                            }
                            catch (Exception e){
                                return false;
                            }
                        }
                        else
                            try {
                                DatabaseClassHelper.instance.update(teacher);
                            }
                            catch (Exception e){
                                return false;
                            }
                    }
                    else{
                        subject.setTeacherID(null);
                        try{
                            DatabaseClassHelper.instance.update(subject);
                        }
                        catch (Exception e){
                            return  false;
                        }
                    }
                }
                else
                {
                    if (teacher != null) subject.setTeacherID(teacher.getId());
                }

                try {
                    DatabaseClassHelper.instance.update(subject);
                }
                catch (Exception e){
                    return false;
                }
            }
            return true;
        }
        else return false;
    }

    public static boolean saveNote(Note note, boolean isEdited) {
        if (note == null)
            return false;
        if (isEdited){
            try{
                DatabaseClassHelper.instance.update(note);
            }
            catch (Exception e){
                return false;
            }
        }
        else {
            try{
                DatabaseClassHelper.instance.insert(note);
            }
            catch (Exception e){
                return false;
            }
        }
        return true;
    }

    public static boolean saveDocument(Document document, boolean isEdited) {
        if (document == null)
            return false;
        if (isEdited){
            try{
                DatabaseClassHelper.instance.update(document);
            }
            catch (Exception e){
                return false;
            }
        }
        else {
            try{
                DatabaseClassHelper.instance.insert(document);
            }
            catch (Exception e){
                return false;
            }
        }
        return true;
    }

    public static boolean saveTask(Task task, boolean isEdited){
        if (task == null || Long.parseLong(task.getDateTime()) <= Calendar.getInstance().getTimeInMillis())
            return false;
        if (isEdited){
            try{
                DatabaseClassHelper.instance.update(task);
            }
            catch (Exception e){
                return false;
            }
        }
        else {
            try{
                DatabaseClassHelper.instance.insert(task);
            }
            catch (Exception e){
                return false;
            }
        }
        return true;
    }
}