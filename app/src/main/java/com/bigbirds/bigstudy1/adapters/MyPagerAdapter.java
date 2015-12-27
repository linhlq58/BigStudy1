package com.bigbirds.bigstudy1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 20/12/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"Ghi chú", "Nhiệm vụ", "Tài liệu"};

    private ArrayList<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
