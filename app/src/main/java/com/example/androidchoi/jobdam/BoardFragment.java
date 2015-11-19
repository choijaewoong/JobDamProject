package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.example.androidchoi.jobdam.Model.User;

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

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "jjj", Toast.LENGTH_SHORT).show();
        NetworkManager.getInstance().showArticle(getActivity(),
                User.USER_NAME, new NetworkManager.OnResultListener<ArticleLab>() {
                    @Override
                    public void onSuccess(ArticleLab result) {
                        //서버에서 게시글 리스트 가져와 저장
                        mAdapter.setItems(result.getArticleList());
                    }
                    @Override
                    public void onFail(int code) {
                        Toast.makeText(getActivity(), "error : " + code, Toast.LENGTH_SHORT).show();
                    }
                });
        Log.i("ddd", "어댑터" + mAdapter.getCount());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.board);
//        mArticlesList = ArticleLab.get(getActivity()).getArticleList();
//        mAdapter.setItems(mArticlesList);
        NetworkManager.getInstance().showArticle(getActivity(),
                User.USER_NAME, new NetworkManager.OnResultListener<ArticleLab>(){
                    @Override
                    public void onSuccess(ArticleLab result) {
                        //서버에서 게시글 리스트 가져와 저장
                        mAdapter.setItems(result.getArticleList());
                        Toast.makeText(getActivity(), "mArtistList" + mArticlesList.size() + "", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "result.getArticleList" + result.getArticleList().size()+"", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFail(int code) {
                        Toast.makeText(getActivity(), "error : " + code, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
}
