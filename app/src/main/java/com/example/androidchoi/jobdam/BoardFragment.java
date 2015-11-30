package com.example.androidchoi.jobdam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.BoardPagerAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.ArticleLab;
import com.example.androidchoi.jobdam.Model.Articles;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    private static final int REQUEST_WRITE = 1;

    ViewPager pager;
    BoardPagerAdapter mAdapter;
    ArrayList<Articles> mArticlesList = new ArrayList<Articles>();
    ImageView articleWriteImage;
    int currentCount = 1;

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){ return; }
        showArticle();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.board);
//        mArticlesList = ArticleLab.get(getActivity()).getArticleList();
//        mAdapter.setItems(mArticlesList);
        showArticle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View shadowToolbar = getActivity().findViewById(R.id.toolbar_shadow);
        shadowToolbar.setVisibility(View.VISIBLE);
        View view =  inflater.inflate(R.layout.fragment_board, container, false);
        mAdapter = new BoardPagerAdapter(getChildFragmentManager());
        pager = (ViewPager)view.findViewById(R.id.view_pager_article);
        pager.setClipToPadding(false);
        pager.setPageMargin(40);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        articleWriteImage =(ImageView)view.findViewById(R.id.image_article_write_button);
        articleWriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleWriteActivity.class);
                startActivityForResult(intent, REQUEST_WRITE);
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public void showArticle(){
        NetworkManager.getInstance().showArticle(getActivity(),
                new NetworkManager.OnResultListener<ArticleLab>() {
                    @Override
                    public void onSuccess(ArticleLab result) {
                        //서버에서 게시글 리스트 가져와 저장
                        mAdapter.setItems(result.getArticleList());
                        pager.setCurrentItem(0,true);
                    }
                    @Override
                    public void onFail(int code) {
                        Toast.makeText(getActivity(), "error : " + code, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void moreArticle(){

    }
}
