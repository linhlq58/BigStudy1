package com.bigbirds.bigstudy1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.bigbirds.bigstudy1.adapters.MyPagerAdapter;
import com.bigbirds.bigstudy1.R;
import com.rey.material.widget.Button;

import java.util.ArrayList;

/**
 * Created by Admin on 20/12/2015.
 */
public class SubjectFragment extends Fragment {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private ArrayList<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NoteFragment noteFragment = new NoteFragment();
        TaskFragment taskFragment = new TaskFragment();
        DocumentFragment documentFragment = new DocumentFragment();

        Bundle bundle = this.getArguments();
        int subjectId = bundle.getInt("subjectId");

        Bundle bundle1 = new Bundle();
        bundle1.putInt("subjectId1", subjectId);
        noteFragment.setArguments(bundle1);
        taskFragment.setArguments(bundle1);
        documentFragment.setArguments(bundle1);

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);

        fragments = new ArrayList<>();

        fragments.add(noteFragment);
        fragments.add(taskFragment);
        fragments.add(documentFragment);

        adapter = new MyPagerAdapter(getChildFragmentManager(), fragments);

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

    }
}
