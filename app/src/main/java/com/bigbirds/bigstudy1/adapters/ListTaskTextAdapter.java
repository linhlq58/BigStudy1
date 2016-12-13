package com.bigbirds.bigstudy1.adapters;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigbirds.bigstudy1.DatabaseClassHelper;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.Note;
import com.bigbirds.bigstudy1.objects.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Linh Lee on 12/14/2016.
 */
public class ListTaskTextAdapter extends BaseAdapter {
    private Activity context;
    private int layout;
    private ArrayList<Task> arrayList;

    public ListTaskTextAdapter(Activity context, int layout, ArrayList<Task> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(layout, parent, false);
        }

        final TextView content = (TextView) convertView.findViewById(R.id.task_content);
        final TextView time = (TextView) convertView.findViewById(R.id.time);
        String subject = "<b>" + "- " + DatabaseClassHelper.instance.getSubjectById(arrayList.get(position).getSubjectID()).getName() + ": " + "</b>" + arrayList.get(position).getTitle();
        content.setText(Html.fromHtml(subject));
        time.setText(formatTime(arrayList.get(position).getDateTime()));

        return convertView;
    }

    public static String formatTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy");
        Date date = new Date(Long.parseLong(time));
        return simpleDateFormat.format(date);
    }
}
