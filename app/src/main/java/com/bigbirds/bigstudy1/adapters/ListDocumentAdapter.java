package com.bigbirds.bigstudy1.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bigbirds.bigstudy1.DatabaseClassHelper;
import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.Document;
import com.rey.material.widget.Button;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Admin on 27/12/2015.
 */
public class ListDocumentAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private ArrayList<Document> arrayList;

    public ListDocumentAdapter(MainActivity context, int layout, ArrayList<Document> arrayList) {
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

        final TextView title = (TextView) convertView.findViewById(R.id.document_title);

        title.setText(arrayList.get(position).getTitle());

        final ImageView btnArrow = (ImageView) convertView.findViewById(R.id.btn_arrow3);
        btnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu noteMenu = new PopupMenu(context, btnArrow);
                noteMenu.getMenuInflater().inflate(R.menu.document_menu, noteMenu.getMenu());
                noteMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.document_edit:
                                context.showDocumentDialog(true, arrayList.get(position));
                                return true;
                            case R.id.document_delete:
                                try {
                                    DatabaseClassHelper.instance.delete(arrayList.get(position));
                                } catch (Exception e) {

                                }

                                Intent intent = new Intent();
                                intent.setAction("updateUIDocumentFragment");
                                context.sendBroadcast(intent);

                                return true;
                            case R.id.document_share:
                                showMyDialog(R.layout.dialog_share);
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

    private void showMyDialog(int id) {
        final Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(id);

        Button okButton = (Button) mDialog.findViewById(R.id.share_ok_button);
        Button cancelButton = (Button) mDialog.findViewById(R.id.share_cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        mDialog.setCancelable(false);

        mDialog.show();
    }
}
