package com.example.androidchoi.jobdam.Adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.androidchoi.jobdam.ArticleFragment;

/**
 * Created by Choi on 2015-11-11.
 */
public class BoardPagerAdapter extends FragmentPagerAdapter {

    public BoardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleFragment.newInstance("국민은행 합격했어요...!!!\n 잡담 덕분이에요..ㅜㅜ\n 감사해요!!!\n 잡담 짱짱!!!!!!!!!!!!");
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public float getPageWidth(int position) {
        return 1.0f;
    }
}
