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
import com.bigbirds.bigstudy1.adapters.ListNoteAdapter;
import com.bigbirds.bigstudy1.adapters.ListTaskAdapter;
import com.bigbirds.bigstudy1.R;
import com.bigbirds.bigstudy1.objects.Task;

import java.util.ArrayList;

/**
 * Created by Admin on 20/12/2015.
 */
public class TaskFragment extends Fragment {

    private ListView taskList;
    private ArrayList<Task> taskArray;
    private ListTaskAdapter taskAdapter;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        v = inflater.inflate(R.layout.fragment_task, container, false);

        taskList = (ListView) v.findViewById(R.id.task_list);

        IntentFilter filter = new IntentFilter("updateUITaskFragment");
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

        taskArray = DatabaseClassHelper.instance.getTasksBySubjectID(subjectId);

        if (taskArray.size() == 0) {
            TextView nullText = (TextView) v.findViewById(R.id.task_null);
            nullText.setText("Chưa có nhiệm vụ nào cho môn học này");
        }

        taskAdapter = new ListTaskAdapter(((MainActivity) getActivity()), R.layout.item_task, taskArray);

        taskList.setAdapter(taskAdapter);

        taskAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
