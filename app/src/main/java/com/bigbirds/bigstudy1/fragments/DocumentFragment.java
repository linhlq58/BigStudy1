package com.bigbirds.bigstudy1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View v = inflater.inflate(R.layout.fragment_document, container, false);
        documentList = (ListView) v.findViewById(R.id.document_list);

        documentArray = new ArrayList<>();

        documentAdapter = new ListDocumentAdapter(getActivity(), R.layout.item_document, documentArray);

        documentList.setAdapter(documentAdapter);

        return v;
    }
}
