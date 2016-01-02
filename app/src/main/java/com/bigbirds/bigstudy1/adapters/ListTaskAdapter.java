package com.bigbirds.bigstudy1.adapters;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.Task;

import java.util.ArrayList;

/**
 * Created by Admin on 27/12/2015.
 */
public class ListTaskAdapter extends BaseAdapter {
    private Activity context;
    private int layout;
    private ArrayList<Task> arrayList;

    public ListTaskAdapter(Activity context, int layout, ArrayList<Task> arrayList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(layout, parent, false);
        }

        TextView content = (TextView) convertView.findViewById(R.id.task_content);

        content.setText(arrayList.get(position).getContent());

        final ImageView btnArrow = (ImageView) convertView.findViewById(R.id.btn_arrow2);
        btnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu taskMenu = new PopupMenu(context, btnArrow);
                taskMenu.getMenuInflater().inflate(R.menu.note_task_menu, taskMenu.getMenu());
                taskMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.note_edit:
                                return true;
                            case R.id.note_delete:
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                taskMenu.show();
            }
        });

        return convertView;
    }
}
