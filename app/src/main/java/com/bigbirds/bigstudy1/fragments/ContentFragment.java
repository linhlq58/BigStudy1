package com.bigbirds.bigstudy1.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.bigbirds.bigstudy1.DatabaseClassHelper;
import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.adapters.ListNoteTextAdapter;
import com.bigbirds.bigstudy1.adapters.ListTaskAdapter;
import com.bigbirds.bigstudy1.adapters.ListTaskTextAdapter;
import com.bigbirds.bigstudy1.objects.Note;
import com.bigbirds.bigstudy1.objects.Subject;
import com.bigbirds.bigstudy1.objects.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 13/12/2015.
 */
public class ContentFragment extends Fragment {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;

    private int mWeekViewType = TYPE_THREE_DAY_VIEW;

    private WeekView mWeekView;

    private ScrollView scrollNote, scrollTask;

    private MainActivity context;

    private ArrayList<Subject> subjectArr;

    private ListView listNoteView;
    private ListNoteTextAdapter noteAdapter;
    private ArrayList<Note> listNoteByDay;
    private ListView listTaskView;
    private ListTaskTextAdapter taskAdapter;
    private ArrayList<Task> listTaskByDay;
    private TextView mainTaskSubjectName, mainTaskTitle;

    private Button completeBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        scrollNote = (ScrollView) view.findViewById(R.id.scroll_note);
        scrollTask = (ScrollView) view.findViewById(R.id.scroll_task);

        listNoteView = (ListView) view.findViewById(R.id.list_note_text);
        listTaskView = (ListView) view.findViewById(R.id.list_task_text);

        Calendar calendar = Calendar.getInstance();

        listNoteByDay = DatabaseClassHelper.instance.getNotesByDay(calendar.get(Calendar.DAY_OF_WEEK));
        listTaskByDay = DatabaseClassHelper.instance.getTasksByDay(calendar.get(Calendar.DAY_OF_WEEK));

        noteAdapter = new ListNoteTextAdapter(getActivity(), R.layout.item_note_text, listNoteByDay);
        listNoteView.setAdapter(noteAdapter);

        taskAdapter = new ListTaskTextAdapter(getActivity(), R.layout.item_task_text, listTaskByDay);
        listTaskView.setAdapter(taskAdapter);

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {
                int subjectId = (int) weekViewEvent.getId();

                SubjectFragment subjectFragment = new SubjectFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("subjectId", subjectId);
                subjectFragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, subjectFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(final WeekViewEvent weekViewEvent, RectF rectF) {

                final int subjectId = (int) weekViewEvent.getId();

                PopupMenu popupMenu = new PopupMenu(getActivity(), mWeekView);

                popupMenu.getMenuInflater().inflate(R.menu.subject_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        Subject currentSubject = DatabaseClassHelper.instance.getSubjectById(subjectId);

                        switch (id) {
                            case R.id.subject_edit:

                                ((MainActivity) getActivity()).showSubjectDialog(true,
                                        currentSubject, DatabaseClassHelper.instance.getTeacherById(currentSubject.getTeacherID()));

                                mWeekView.notifyDatasetChanged();

                                return true;
                            case R.id.subject_delete:
                                try {
                                    DatabaseClassHelper.instance.delete(currentSubject);
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "Có lỗi khi xóa",
                                            Toast.LENGTH_SHORT).show();
                                }
                                mWeekView.notifyDatasetChanged();

                                Intent intent = new Intent();
                                intent.setAction("updateUISubject");
                                ((MainActivity) getActivity()).sendBroadcast(intent);

                                return true;
                            case R.id.subject_info:
                                ((MainActivity) getActivity()).showTeacherDialog(DatabaseClassHelper.instance.getTeacherById(subjectArr.get(subjectId).getTeacherID()));
                                return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();

            }
        });

        mWeekView.setFirstDayOfWeek(Calendar.MONDAY);

        IntentFilter filter = new IntentFilter("updateUISubject");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onResume();
            }
        };
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onResume() {

        //Subject nextSubject = Subject.getNextSubject(DatabaseClassHelper.instance.getSubjects(2016, 1));

        //ArrayList<Task> taskArr = DatabaseClassHelper.instance.getTasksInAWeek(2016, 1);

        /*ArrayList<Note> noteArr = DatabaseClassHelper.instance.getNotesBySubjectID(nextSubject.getId());

        mainNoteSubjectName.setText(nextSubject.getName());

        if (noteArr.size() > 0) {
            String s = new String();

            for (int i = 0; i<noteArr.size(); i++) {
                s = s + "- " + noteArr.get(i).getContent() + "\n";
            }

            mainNoteContent.setText(s);
        } else {
            mainNoteContent.setText("Không có ghi chú nào cho môn học sắp tới");
        }*/

        /*if (taskArr.size() > 0) {

            Task nearestTask = taskArr.get(0);

            mainTaskSubjectName.setText(DatabaseClassHelper.instance.getSubjectById(nearestTask.getSubjectID()).getName());

            String str = ListTaskAdapter.formatTime(nearestTask.getDateTime());

            mainTaskTitle.setText(nearestTask.getTitle() + "\n" + str);

            completeBtn.setClickable(true);
        } else {
            mainTaskTitle.setText("Không có nhiệm vụ nào gần đây");

            completeBtn.setClickable(false);
        }*/


        subjectArr = DatabaseClassHelper.instance.getSubjects(2016, 1);

        final int[] colorArr = {R.color.event_color_01, R.color.event_color_02, R.color.event_color_03,
                R.color.event_color_04, R.color.event_color_05, R.color.event_color_06,
                R.color.event_color_07, R.color.event_color_08, R.color.event_color_09,
                R.color.event_color_10};

        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                for (int i = 0; i < subjectArr.size(); i++) {
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.DAY_OF_WEEK, subjectArr.get(i).getDayOfWeek());

                    if (subjectArr.get(i).getBeginningPeriod() >= 1
                            && subjectArr.get(i).getBeginningPeriod() <= 5) {
                        startTime.set(Calendar.HOUR_OF_DAY, subjectArr.get(i).getBeginningPeriod() - 1 + 7);
                    } else if (subjectArr.get(i).getBeginningPeriod() >= 6
                            && subjectArr.get(i).getBeginningPeriod() <= 10) {
                        startTime.set(Calendar.HOUR_OF_DAY, subjectArr.get(i).getBeginningPeriod() - 1 + 8);
                    }

                    startTime.set(Calendar.MINUTE, 0);
                    startTime.set(Calendar.MONTH, newMonth - 1);
                    startTime.set(Calendar.YEAR, newYear);
                    Calendar endTime = (Calendar) startTime.clone();

                    if (subjectArr.get(i).getEndingPeriod() >= 1
                            && subjectArr.get(i).getEndingPeriod() <= 5) {
                        endTime.set(Calendar.HOUR_OF_DAY, subjectArr.get(i).getEndingPeriod() + 7);
                    } else if (subjectArr.get(i).getEndingPeriod() >= 6
                            && subjectArr.get(i).getEndingPeriod() <= 10) {
                        endTime.set(Calendar.HOUR_OF_DAY, subjectArr.get(i).getEndingPeriod() + 8);
                    }

                    endTime.set(Calendar.MONTH, newMonth - 1);

                    if (subjectArr.get(i).getTeacherID() != null
                            && subjectArr.get(i).getTeacherID() > 0) {
                        WeekViewEvent event = new WeekViewEvent(subjectArr.get(i).getId(), subjectArr.get(i).getName() + "\n",
                                subjectArr.get(i).getPlace() + "\n" + DatabaseClassHelper.instance.getTeacherById(subjectArr.get(i).getTeacherID()).getName(), startTime, endTime);
                        event.setColor(getResources().getColor(colorArr[i % 10]));
                        events.add(event);
                    } else {
                        WeekViewEvent event = new WeekViewEvent(subjectArr.get(i).getId(), subjectArr.get(i).getName() + "\n",
                                subjectArr.get(i).getPlace(), startTime, endTime);
                        event.setColor(getResources().getColor(colorArr[i % 10]));
                        events.add(event);
                    }
                }

                return events;
            }
        });

        mWeekView.goToHour(7);

        mWeekView.notifyDatasetChanged();

        setupDateTimeInterpreter(false);

        super.onResume();
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" d/M", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + "\n" + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                //return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
                switch (hour) {
                    case 7:
                        return "Tiết 1";
                    case 8:
                        return "Tiết 2";
                    case 9:
                        return "Tiết 3";
                    case 10:
                        return "Tiết 4";
                    case 11:
                        return "Tiết 5";
                    case 12:
                        return "BREAK";
                    case 13:
                        return "Tiết 6";
                    case 14:
                        return "Tiết 7";
                    case 15:
                        return "Tiết 8";
                    case 16:
                        return "Tiết 9";
                    case 17:
                        return "Tiết 10";
                    default:
                        return "";
                }
            }
        });
    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                getResources().getDisplayMetrics());
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);
                    mWeekView.goToHour(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;

            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);
                    mWeekView.goToHour(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;

            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);
                    mWeekView.goToHour(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;

            case R.id.action_zoom_in:
                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                mWeekView.setLayoutParams(param1);

                if (mWeekViewType == TYPE_WEEK_VIEW)
                    setupDateTimeInterpreter(true);

                return true;

            case R.id.action_zoom_out:
                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0, 0.5f);
                param2.setMargins(dpValue, dpValue, dpValue, dpValue);

                LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0, 0.25f);
                param3.setMargins(dpValue, dpValue, dpValue, dpValue);

                mWeekView.setLayoutParams(param2);
                scrollNote.setLayoutParams(param3);
                scrollTask.setLayoutParams(param3);

                if (mWeekViewType == TYPE_WEEK_VIEW)
                    setupDateTimeInterpreter(true);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
