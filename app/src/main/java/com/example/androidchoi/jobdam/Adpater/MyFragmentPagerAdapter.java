package com.example.androidchoi.jobdam.Adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-11-04.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter{
    final int PAGE_COUNT = 2;
    private String[] mTabTitles = new String[PAGE_COUNT];
    private int tabIcons[] = {R.drawable.image_board_all_icon, R.drawable.image_board_me_icon};
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public void setTabList(String strings[]){
        mTabTitles = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }


}

