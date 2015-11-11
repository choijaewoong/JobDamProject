package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.BoardPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    ViewPager pager;

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.board);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_board, container, false);
        pager = (ViewPager)view.findViewById(R.id.view_pager_article);
        pager.setClipToPadding(false);
        pager.setPageMargin(40);
        pager.setAdapter(new BoardPagerAdapter(getChildFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getActivity(), "selected : " + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setCurrentItem(2, true);
        return view;
    }


}
