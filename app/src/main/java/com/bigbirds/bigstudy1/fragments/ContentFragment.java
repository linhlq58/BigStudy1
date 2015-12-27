package com.bigbirds.bigstudy1.fragments;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.R;

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

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new SubjectFragment());
                transaction.commit();
            }
        });

        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

            }
        });

        mWeekView.setMonthChangeListener(new WeekView.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                // Populate the week view with some events.
                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 0);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth - 1);
                startTime.set(Calendar.YEAR, newYear);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 3);
                endTime.set(Calendar.MONTH, newMonth - 1);
                WeekViewEvent event = new WeekViewEvent(1,
                        "Đường lối cách mạng của ĐCSVN \n B2-HT4", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_01));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 1);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 4);
                endTime.set(Calendar.MONTH, newMonth - 1);
                event = new WeekViewEvent(2,
                        "Đại số \n GĐ2-309", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_03));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 3);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 6);
                endTime.set(Calendar.MONTH, newMonth - 1);
                event = new WeekViewEvent(2,
                        "Tâm lý học \n A2-610", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_02));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 2);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 6);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(3,
                        "Toán cao cấp \n B2-HT8", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_03));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 0);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 2);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(4,
                        "Bóng đá 1 \n Sân 3", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_04));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 2);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 4);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(5,
                        "Thống kê cho khoa học xã hội \n B2-HT6", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_05));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 6);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 10);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(6,
                        "Tiếng Anh 4A \n A2-404", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_06));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 6);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 10);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(7,
                        "Tiếng Anh 4A \n A2-404", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_06));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 6);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 10);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(8,
                        "Tiếng Anh 4B \n A2-301", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_07));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 6);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 10);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(9,
                        "Tiếng Anh 4B \n A2-301", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_07));
                events.add(event);

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                startTime.set(Calendar.HOUR_OF_DAY, 6);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, 10);
                endTime.set(Calendar.MINUTE, 0);
                endTime.set(Calendar.MONTH, newMonth-1);
                event = new WeekViewEvent(10,
                        "Tiếng Anh 4C \n B2-408", startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_08));
                events.add(event);

                return events;
            }
        });

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