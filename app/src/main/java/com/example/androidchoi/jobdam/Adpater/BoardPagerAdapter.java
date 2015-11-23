package com.example.androidchoi.jobdam.Adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.androidchoi.jobdam.ArticleFragment;
import com.example.androidchoi.jobdam.Model.Articles;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Choi on 2015-11-11.
 */
public class BoardPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Articles> mItems = new ArrayList<Articles>();

    public void setItems(ArrayList<Articles> items){
        mItems = items;
        Collections.reverse(mItems);
        notifyDataSetChanged();
    }

    public BoardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
           // after this, onCreateView() of Fragment is ca lled.
            return POSITION_NONE;   // notifyDataSetChanged
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleFragment.newInstance(mItems.get(position));
    }
    @Override
    public int getCount() {
        return mItems.size();
    }
    @Override
    public float getPageWidth(int position) {
        return 1.0f;
    }
}
