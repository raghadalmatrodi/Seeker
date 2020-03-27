package com.example.seeker.Rating;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments;

    ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }//end constructor

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }//end getItem()

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }//end addFragment()

    @Override
    public int getCount() {
        return fragments.size();
    }//end getCount()

}
