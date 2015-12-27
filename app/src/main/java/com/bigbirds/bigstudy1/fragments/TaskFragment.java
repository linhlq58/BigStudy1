package com.bigbirds.bigstudy1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigbirds.bigstudy1.adapters.ListTaskAdapter;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.ItemTask;

import java.util.ArrayList;

/**
 * Created by Admin on 20/12/2015.
 */
public class TaskFragment extends Fragment {

    private ListView taskList;
    private ArrayList<ItemTask> taskArray;
    private ListTaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View v = inflater.inflate(R.layout.fragment_task, container, false);
        taskList = (ListView) v.findViewById(R.id.task_list);

        taskArray = new ArrayList<>();
        taskArray.add(new ItemTask("Hoàn thành bài tập XSTK"));
        taskArray.add(new ItemTask("Hoàn thành bài tập XSTK"));
        taskArray.add(new ItemTask("Hoàn thành bài tập XSTK"));
        taskArray.add(new ItemTask("Hoàn thành bài tập XSTK"));
        taskArray.add(new ItemTask("Hoàn thành bài tập XSTK"));
        taskArray.add(new ItemTask("Hoàn thành bài tập XSTK"));

        taskAdapter = new ListTaskAdapter(getActivity(), R.layout.item_task, taskArray);

        taskList.setAdapter(taskAdapter);

        return v;
    }
}
