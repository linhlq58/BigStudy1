package com.bigbirds.bigstudy1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bigbirds.bigstudy1.DatabaseClassHelper;
import com.bigbirds.bigstudy1.MainActivity;
import com.bigbirds.bigstudy1.adapters.ListNoteAdapter;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.Note;
import com.bigbirds.bigstudy1.objects.Subject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Admin on 20/12/2015.
 */
public class NoteFragment extends Fragment {

    private ListView noteList;
    private ArrayList<Note> noteArray;
    private ListNoteAdapter noteAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        Bundle bundle = this.getArguments();
        int subjectId = bundle.getInt("subjectId1");

        View v = inflater.inflate(R.layout.fragment_note, container, false);

        noteList = (ListView) v.findViewById(R.id.note_list);

        noteArray = DatabaseClassHelper.instance.getNotesBySubjectID(subjectId);

        noteAdapter = new ListNoteAdapter(((MainActivity) getActivity()), R.layout.item_note, noteArray);

        noteAdapter.notifyDataSetChanged();

        noteList.setAdapter(noteAdapter);

        if (noteArray.size() == 0) {
            TextView nullText = (TextView) v.findViewById(R.id.note_null);
            nullText.setText("Chưa có ghi chú nào cho môn học này");
        }

        return v;
    }
}
