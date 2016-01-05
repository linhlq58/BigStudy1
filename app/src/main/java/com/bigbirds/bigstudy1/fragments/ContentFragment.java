package com.bigbirds.bigstudy1.fragments;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.bigbirds.bigstudy1.DatabaseClassHelper;
import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.Subject;

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

        subjectArr = new ArrayList<>();

        subjectArr = DatabaseClassHelper.instance.getSubjects(2016, 1);

        final int[] colorArr = {R.color.event_color_01, R.color.event_color_02, R.color.event_color_03,
                R.color.event_color_04, R.color.event_color_05, R.color.event_color_06,
                R.color.event_color_07, R.color.event_color_08, R.color.event_color_09,
                R.color.event_color_10};

        //((MainActivity) getActivity()).subjectDialog

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

                        switch (id) {
                            case R.id.subject_edit:
                                ((MainActivity) getActivity()).showSubjectDialog(true,
                                        subjectArr.get(subjectId), null);

                                mWeekView.notifyDatasetChanged();

                                ((MainActivity) getActivity()).updateUI();
                                return true;
                            case R.id.subject_delete:
                                try {
                                    DatabaseClassHelper.instance.delete(subjectArr.get(subjectId));
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "Có lỗi khi xóa",
                                            Toast.LENGTH_SHORT).show();
                                }
                                mWeekView.notifyDatasetChanged();

                                ((MainActivity) getActivity()).updateUI();

                                return true;
                            case R.id.subject_info:
                                return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();

            }
        });

        mWeekView.setFirstDayOfWeek(Calendar.MONDAY);

        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                for (int i=0; i<subjectArr.size(); i++) {
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.DAY_OF_WEEK, subjectArr.get(i).getDayOfWeek());
                    startTime.set(Calendar.HOUR_OF_DAY, subjectArr.get(i).getBeginningPeriod() - 1);
                    startTime.set(Calendar.MINUTE, 0);
                    startTime.set(Calendar.MONTH, newMonth - 1);
                    startTime.set(Calendar.YEAR, newYear);
                    Calendar endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, subjectArr.get(i).getEndingPeriod());
                    endTime.set(Calendar.MONTH, newMonth - 1);


                    WeekViewEvent event = new WeekViewEvent(i, subjectArr.get(i).getName() + "\n",
                            subjectArr.get(i).getPlace() + "\n" + DatabaseClassHelper.instance.getTeacherById(subjectArr.get(i).getTeacherID()).getName(), startTime, endTime);
                    event.setColor(getResources().getColor(colorArr[i % 10]));
                    events.add(event);

                    /*WeekViewEvent event = new WeekViewEvent(i, subjectArr.get(i).getName() + "\n",
                            subjectArr.get(i).getPlace(), startTime, endTime);
                    event.setColor(getResources().getColor(colorArr[i % 10]));
                    events.add(event);*/


                }

                return events;
            }
        });

        mWeekView.notifyDatasetChanged();

        setupDateTimeInterpreter(false);
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
                    case 0:
                        return "Tiết 1";
                    case 1:
                        return "Tiết 2";
                    case 2:
                        return "Tiết 3";
                    case 3:
                        return "Tiết 4";
                    case 4:
                        return "Tiết 5";
                    case 5:
                        return "Tiết 6";
                    case 6:
                        return "Tiết 7";
                    case 7:
                        return "Tiết 8";
                    case 8:
                        return "Tiết 9";
                    case 9:
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
