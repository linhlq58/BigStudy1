package com.bigbirds.bigstudy1.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.ItemMenu;

import java.util.ArrayList;

/**
 * Created by Admin on 09/12/2015.
 */
public class ListMenuAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private ArrayList<ItemMenu> arrayList;

    public ListMenuAdapter(MainActivity context, int layout, ArrayList<ItemMenu> arrayList) {
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
            convertView = context.getLayoutInflater().inflate(layout, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon1);

        name.setText(arrayList.get(position).getName());
        icon.setImageResource(arrayList.get(position).getIcon());

        return convertView;
    }
}
