package com.bigbirds.bigstudy1;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbirds.bigstudy1.objects.ItemMenu;
import com.bigbirds.bigstudy1.objects.ItemNote;

import java.util.ArrayList;

/**
 * Created by Admin on 20/12/2015.
 */
public class ListNoteAdapter extends BaseAdapter {

    private Activity context;
    private int layout;
    private ArrayList<ItemNote> arrayList;

    public ListNoteAdapter(Activity context, int layout, ArrayList<ItemNote> arrayList) {
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

        TextView content = (TextView) convertView.findViewById(R.id.note_content);

        content.setText(arrayList.get(position).getContent());

        return convertView;
    }
}
