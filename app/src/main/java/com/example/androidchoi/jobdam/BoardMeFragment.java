package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.MyBoardItemAdapter;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.ArticleLab;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardMeFragment extends Fragment {

    ListView mListView;
    MyBoardItemAdapter mAdapter;
    SwipeRefreshLayout mRefreshLayout;
    TextView mTextMyBoardTotal;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showArticle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_me, container, false);
        View profileHeaderView = inflater.inflate(R.layout.view_header_my_board, null);
        mListView = (ListView)view.findViewById(R.id.listView_board_me);
        mListView.addHeaderView(profileHeaderView);
        mAdapter = new MyBoardItemAdapter();
        showArticle();
        mListView.setAdapter(mAdapter);

        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_board_me);
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        mRefreshLayout.setColorSchemeResources(android.R.color.white);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showArticle();
            }
        });
        ((BoardFragment)getParentFragment()).writeActicleButton.attachToListView(mListView);

        mTextMyBoardTotal = (TextView)profileHeaderView.findViewById(R.id.text_my_board_total);
        return view;
    }

    public void showArticle(){
        NetworkManager.getInstance().showMyArticle(getActivity(),
                new NetworkManager.OnResultListener<ArticleLab>() {
                    @Override
                    public void onSuccess(ArticleLab result) {
                        //서버에서 게시글 리스트 가져와 저장
                        mAdapter.setItems(result.getArticleList());
                        mRefreshLayout.setRefreshing(false);
                        mTextMyBoardTotal.setText("Total " + result.getTotalCount());
                    }
                    @Override
                    public void onFail(int code) {
                        Log.i("error : ", code+"");
                        Toast.makeText(MyApplication.getContext(), "데이터를 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

