package com.bigbirds.bigstudy1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigbirds.bigstudy1.adapters.ListNoteAdapter;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.ItemNote;

import java.util.ArrayList;

/**
 * Created by Admin on 20/12/2015.
 */
public class NoteFragment extends Fragment {

    private ListView noteList;
    private ArrayList<ItemNote> noteArray;
    private ListNoteAdapter noteAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View v = inflater.inflate(R.layout.fragment_note, container, false);
        noteList = (ListView) v.findViewById(R.id.note_list);

        noteArray = new ArrayList<>();
        noteArray.add(new ItemNote("Đại số đổi sang phòng 301-GĐ2"));
        noteArray.add(new ItemNote("Đại số đổi sang phòng 301-GĐ2"));
        noteArray.add(new ItemNote("Đại số đổi sang phòng 301-GĐ2"));
        noteArray.add(new ItemNote("Đại số đổi sang phòng 301-GĐ2"));
        noteArray.add(new ItemNote("Đại số đổi sang phòng 301-GĐ2"));
        noteArray.add(new ItemNote("Đại số đổi sang phòng 301-GĐ2"));

        noteAdapter = new ListNoteAdapter(getActivity(), R.layout.item_note, noteArray);

        noteList.setAdapter(noteAdapter);

        return v;
    }
}
