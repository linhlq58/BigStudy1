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

        final TextView content = (TextView) convertView.findViewById(R.id.note_content);

        content.setText(arrayList.get(position).getContent());

        final ImageView btnArrow = (ImageView) convertView.findViewById(R.id.btn_arrow);
        btnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu noteMenu = new PopupMenu(context, btnArrow);
                noteMenu.getMenuInflater().inflate(R.menu.note_menu, noteMenu.getMenu());
                noteMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
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

                noteMenu.show();
            }
        });

        return convertView;
    }
}
