package com.bigbirds.bigstudy1.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bigbirds.bigstudy1.DatabaseClassHelper;
import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.adapters.ListDocumentAdapter;
import com.bigbirds.bigstudy1.adapters.ListNoteAdapter;
import com.bigbirds.bigstudy1.objects.Document;
import com.bigbirds.bigstudy1.objects.Note;

import java.util.ArrayList;

/**
 * Created by Admin on 20/12/2015.
 */
public class DocumentFragment extends Fragment {
    private ListView documentList;
    private ListDocumentAdapter documentAdapter;
    private ArrayList<Document> documentArray;
    private TextView nullText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View v = inflater.inflate(R.layout.fragment_document, container, false);
        documentList = (ListView) v.findViewById(R.id.document_list);

        nullText = (TextView) v.findViewById(R.id.document_null);

        IntentFilter filter = new IntentFilter("updateUIDocumentFragment");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onResume();
            }
        };
        getActivity().registerReceiver(broadcastReceiver, filter);

        return v;
    }

    @Override
    public void onResume() {

        final Bundle bundle = this.getArguments();
        int subjectId = bundle.getInt("subjectId1");

        documentArray = DatabaseClassHelper.instance.getDocumentsBySubjectID(subjectId);

        if (documentArray.size() == 0) {

            nullText.setText("Chưa có tài liệu nào cho môn học này");
        } else {

            nullText.setText("");
        }

        documentAdapter = new ListDocumentAdapter(((MainActivity) getActivity()), R.layout.item_document, documentArray);

        documentList.setAdapter(documentAdapter);

        super.onResume();
    }
}
