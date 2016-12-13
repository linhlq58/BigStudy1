package com.bigbirds.bigstudy1.adapters;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bigbirds.bigstudy1.DatabaseClassHelper;
import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.Note;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 12/14/2016.
 */
public class ListNoteTextAdapter extends BaseAdapter {
    private Activity context;
    private int layout;
    private ArrayList<Note> arrayList;

    public ListNoteTextAdapter(Activity context, int layout, ArrayList<Note> arrayList) {
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

        final TextView content = (TextView) convertView.findViewById(R.id.note_content);
        String subject = "<b>" + "- " + DatabaseClassHelper.instance.getSubjectById(arrayList.get(position).getSubjectID()).getName() + ": " + "</b>" + arrayList.get(position).getContent();
        content.setText(Html.fromHtml(subject));

        return convertView;
    }
}
