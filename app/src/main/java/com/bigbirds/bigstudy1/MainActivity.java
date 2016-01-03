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
import android.widget.ListView;
import android.widget.PopupMenu;

import com.alamkanak.weekview.WeekView;
import com.bigbirds.bigstudy1.adapters.ListMenuAdapter;
import com.bigbirds.bigstudy1.fragments.ContentFragment;
import com.bigbirds.bigstudy1.objects.ItemMenu;
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

    private Spinner spinner1, spinner2, documentSpinner, subjectSpinner1, subjectSpinner2;

    private WeekView mWeekView;

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

                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.addSubject:
                                showSubjectDialog();
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

        //mWeekView = (WeekView) findViewById(R.id.weekView);

        setSupportActionBar(toolbar);

        initDrawerLayout();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new ContentFragment());
        transaction.addToBackStack("ft");
        transaction.commit();

    }

    private void showNoteDialog() {
        mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.add_note);

        Button saveButton = (Button) mDialog.findViewById(R.id.note_save_button);
        Button cancelButton = (Button) mDialog.findViewById(R.id.note_cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        mDialog.setCancelable(false);

        mDialog.show();
    }

    private void showSubjectDialog() {
        mDialog = new Dialog(MainActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.add_subject);

        Button saveButton = (Button) mDialog.findViewById(R.id.subject_save_button);
        Button cancelButton = (Button) mDialog.findViewById(R.id.subject_cancel_button);

        subjectSpinner1 = (Spinner) mDialog.findViewById(R.id.subject_spinner1);
        subjectSpinner2 = (Spinner) mDialog.findViewById(R.id.subject_spinner2);

        initSubjectSpinner1();
        initSubjectSpinner2();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
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

        spinner1 = (Spinner) mDialog.findViewById(R.id.spinner1);
        spinner2 = (Spinner) mDialog.findViewById(R.id.spinner2);

        initSpinner1();
        initSpinner2();

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

    private void initSpinner1() {
        String spinnerArr[] = {"<None>", "Giải tích 2", "Lập trình nâng cao",
                "Đại số", "Tối ưu hóa"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner1.setAdapter(spinnerAdapter);

        spinner1.setSelection(0);

    }

    private void initSpinner2() {
        String spinnerArr[] = {"AM", "PM"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, spinnerArr);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner2.setAdapter(spinnerAdapter);

        spinner2.setSelection(0);

    }

    private void initDocumentSpinner() {
        String spinnerArr[] = {"<None>", "Giải tích 2", "Lập trình nâng cao",
                "Đại số", "Tối ưu hóa"};

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
