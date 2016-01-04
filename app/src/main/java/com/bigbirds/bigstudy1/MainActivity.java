package com.bigbirds.bigstudy1;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.bigbirds.bigstudy1.adapters.ListMenuAdapter;
import com.bigbirds.bigstudy1.fragments.ContentFragment;
import com.bigbirds.bigstudy1.objects.ItemMenu;
import com.bigbirds.bigstudy1.objects.Note;
import com.bigbirds.bigstudy1.objects.Subject;
import com.bigbirds.bigstudy1.objects.Teacher;
import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView listView;
    private ListMenuAdapter adapter;
    private ArrayList<ItemMenu> itemMenus;

    private FloatingActionButton floatingActionButton;

    private Dialog mDialog;

    private Spinner taskSpinner, documentSpinner, subjectSpinner1, subjectSpinner2,
            subjectSpinner3, subjectSpinner4, noteSpinner;

    private WeekView mWeekView;

    private TimePicker timePicker;
    private DatePicker datePicker;

    private ArrayList<Subject> subjectArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseClassHelper.createInstance(getApplicationContext());

        setContentView(R.layout.activity_main);

        itemMenus = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.menu_list);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_image);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, floatingActionButton);

                popupMenu.getMenuInflater().inflate(R.menu.floatingbtn_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.addSubject:
                                showSubjectDialog(false, null);
                                return true;
                            case R.id.addNote:
                                showNoteDialog();
                                return true;
                            case R.id.addTask:
                                showTaskDialog();
                                return true;
                            case R.id.addDocument:
                                showDocumentDialog();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });

        setSupportActionBar(toolbar);

        initDrawerLayout();

        updateUI();

    }

    private void showNoteDialog() {
        mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.add_note);

        Button saveButton = (Button) mDialog.findViewById(R.id.note_save_button);
        Button cancelButton = (Button) mDialog.findViewById(R.id.note_cancel_button);

        final EditText noteTitle = (EditText) mDialog.findViewById(R.id.edit_note_title);
        final EditText noteContent = (EditText) mDialog.findViewById(R.id.edit_note_content);

        noteSpinner = (Spinner) mDialog.findViewById(R.id.note_spinner);

        initNoteSpinner();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(noteTitle) || isEmpty(noteContent)) {
                    Toast.makeText(MainActivity.this,
                            "Bạn nhập thiếu thông tin hoặc sai yêu cầu!", Toast.LENGTH_SHORT).show();
                } else {
                    Note newNote = new Note();

                    newNote.setTitle(noteTitle.getText().toString());
                    newNote.setContent(noteContent.getText().toString());
                    newNote.setSubjectID(noteSpinner.getSelectedItemPosition());

                    DataHandler.saveNote(newNote, false);

                    mDialog.dismiss();
                }
            }
        });

        mDialog.setCancelable(false);

        mDialog.show();
    }

    public void showSubjectDialog(final boolean isEdited, final Subject subject) {
        mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.add_subject);

        Button saveButton = (Button) mDialog.findViewById(R.id.subject_save_button);
        Button cancelButton = (Button) mDialog.findViewById(R.id.subject_cancel_button);

        final EditText name = (EditText) mDialog.findViewById(R.id.edit_subject_name);
        final EditText location = (EditText) mDialog.findViewById(R.id.edit_subject_location);
        final EditText teacherName = (EditText) mDialog.findViewById(R.id.edit_subject_teacher);

        subjectSpinner1 = (Spinner) mDialog.findViewById(R.id.subject_spinner1);
        subjectSpinner2 = (Spinner) mDialog.findViewById(R.id.subject_spinner2);
        subjectSpinner3 = (Spinner) mDialog.findViewById(R.id.subject_spinner3);
        subjectSpinner4 = (Spinner) mDialog.findViewById(R.id.subject_spinner4);

        initSubjectSpinner1();
        initSubjectSpinner2();
        initSubjectSpinner3();
        initSubjectSpinner4();

        if (isEdited == true) {
            name.setText(subject.getName());
            location.setText(subject.getPlace());
            //teacherName.setText((DatabaseClassHelper.instance.getTeacherById(subject.getTeacherID())).getName());
            subjectSpinner4.setSelection(subject.getDayOfWeek()-1);
            subjectSpinner1.setSelection(subject.getBeginningPeriod()-1);
            subjectSpinner2.setSelection(subject.getEndingPeriod()-1);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(name) || isEmpty(location)
                        || (!isEmpty(teacherName) && subjectSpinner3.getSelectedItem().toString() != "<None>")
                        || (int)subjectSpinner1.getSelectedItem() > (int)subjectSpinner2.getSelectedItem()) {
                    Toast.makeText(MainActivity.this,
                            "Bạn nhập thiếu thông tin hoặc sai yêu cầu!", Toast.LENGTH_SHORT).show();
                } else {

                    Subject newSubject = new Subject();

                    newSubject.setName(name.getText().toString());
                    newSubject.setPlace(location.getText().toString());

                    if (subjectSpinner3.getSelectedItem().toString() != "<None>") {
                        newSubject.setTeacherID(subjectSpinner3.getSelectedItemPosition());
                    }

                    newSubject.setDayOfWeek(formatDayOfWeek(subjectSpinner4.getSelectedItem().toString()));
                    newSubject.setBeginningPeriod((int) subjectSpinner1.getSelectedItem());
                    newSubject.setEndingPeriod((int) subjectSpinner2.getSelectedItem());
                    newSubject.setYear(2016);
                    newSubject.setSemester(1);


                    Teacher newTeacher = new Teacher();

                    newTeacher.setName(teacherName.getText().toString());

                    boolean flag = DataHandler.saveSubjectTeacher(newSubject, newTeacher,
                                subjectSpinner3.getSelectedItemPosition(), isEdited);

                    if (flag == false && isEdited == false) {
                        Toast.makeText(MainActivity.this,
                                "Môn học bạn nhập bị trùng thời gian!", Toast.LENGTH_SHORT).show();
                    }

                    updateUI();

                    mDialog.dismiss();
                }
            }
        });


        mDialog.setCancelable(false);

        mDialog.show();
    }

    private void showTaskDialog() {
        mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.add_task);

        Button saveButton = (Button) mDialog.findViewById(R.id.task_save_button);
        Button cancelButton = (Button) mDialog.findViewById(R.id.task_cancel_button);
        timePicker = (TimePicker) mDialog.findViewById(R.id.time_picker);
        datePicker = (DatePicker) mDialog.findViewById(R.id.date_picker);

        datePicker.setCalendarViewShown(false);

        taskSpinner = (Spinner) mDialog.findViewById(R.id.task_spinner);

        initTaskSpinner();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        mDialog.setCancelable(false);

        mDialog.show();
    }

    private void showDocumentDialog() {
        mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.add_document);

        Button saveButton = (Button) mDialog.findViewById(R.id.document_save_button);
        Button cancelButton = (Button) mDialog.findViewById(R.id.document_cancel_button);

        documentSpinner = (Spinner) mDialog.findViewById(R.id.document_spinner);

        initDocumentSpinner();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        mDialog.setCancelable(false);

        mDialog.show();
    }

    private void initTaskSpinner() {
        ArrayList<String> spinnerArr = new ArrayList<>();

        subjectArr = DatabaseClassHelper.instance.getSubjects(2016, 1);

        for (int i=0; i<subjectArr.size(); i++) {
            spinnerArr.add(subjectArr.get(i).getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        taskSpinner.setAdapter(spinnerAdapter);

        taskSpinner.setSelection(0);

    }

    private void initDocumentSpinner() {
        ArrayList<String> spinnerArr = new ArrayList<>();

        subjectArr = DatabaseClassHelper.instance.getSubjects(2016, 1);

        for (int i=0; i<subjectArr.size(); i++) {
            spinnerArr.add(subjectArr.get(i).getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        documentSpinner.setAdapter(spinnerAdapter);

        documentSpinner.setSelection(0);

    }

    private void initSubjectSpinner1() {
        Integer spinnerArr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        subjectSpinner1.setAdapter(spinnerAdapter);

        subjectSpinner1.setSelection(0);

    }

    private void initSubjectSpinner2() {
        Integer spinnerArr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        subjectSpinner2.setAdapter(spinnerAdapter);

        subjectSpinner2.setSelection(0);

    }

    private void initSubjectSpinner3() {
        String spinnerArr[] = {"<None>", "Lê Nguyên Khôi", "Đặng Thanh Hải",
                "Lê Sĩ Vinh", "Nguyễn Thị Thanh Vân"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        subjectSpinner3.setAdapter(spinnerAdapter);

        subjectSpinner3.setSelection(0);
    }

    private void initSubjectSpinner4() {
        String spinnerArr[] = {"Chủ Nhật", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6",
                "Thứ 7"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        subjectSpinner4.setAdapter(spinnerAdapter);

        subjectSpinner4.setSelection(0);
    }

    private void initNoteSpinner() {
        ArrayList<String> spinnerArr = new ArrayList<>();

        subjectArr = DatabaseClassHelper.instance.getSubjects(2016, 1);

        for (int i=0; i<subjectArr.size(); i++) {
            spinnerArr.add(subjectArr.get(i).getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        noteSpinner.setAdapter(spinnerAdapter);

        noteSpinner.setSelection(0);
    }

    private void initDrawerLayout() {
        setListViewData();
        setListViewHeader();

        adapter = new ListMenuAdapter(this, R.layout.item_menu, itemMenus);
        listView.setAdapter(adapter);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void setListViewHeader() {
        View header = getLayoutInflater().inflate(R.layout.header_listview, listView, false);
        listView.addHeaderView(header, null, false);
    }

    private void setListViewData() {
        itemMenus.add(new ItemMenu("Học kì 1", R.mipmap.ic_semester));
        itemMenus.add(new ItemMenu("Học kì 2", R.mipmap.ic_semester));
        itemMenus.add(new ItemMenu("Thêm học kì", R.mipmap.ic_plus));

    }

    private int formatDayOfWeek(String s) {
        switch (s) {
            case "Chủ Nhật":
                return 1;
            case "Thứ 2":
                return 2;
            case "Thứ 3":
                return 3;
            case "Thứ 4":
                return 4;
            case "Thứ 5":
                return 5;
            case "Thứ 6":
                return 6;
            case "Thứ 7":
                return 7;
            default:
                return 0;
        }
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public void updateUI() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new ContentFragment());
        transaction.commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


}
