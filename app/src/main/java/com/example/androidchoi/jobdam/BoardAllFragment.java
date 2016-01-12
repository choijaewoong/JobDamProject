package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.androidchoi.jobdam.Adpater.BoardItemAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.ArticleLab;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardAllFragment extends Fragment {

    ListView mListView;
    BoardItemAdapter mAdapter;

    public BoardAllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showArticle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_all, container, false);
        mListView = (ListView)view.findViewById(R.id.listView_board_all);
        mAdapter = new BoardItemAdapter();
        showArticle();
        mListView.setAdapter(mAdapter);
        return view;
    }

    public void showArticle(){
        NetworkManager.getInstance().showArticle(getActivity(),
                new NetworkManager.OnResultListener<ArticleLab>() {
                    @Override
                    public void onSuccess(ArticleLab result) {
                        //서버에서 게시글 리스트 가져와 저장
                        mAdapter.setItems(result.getArticleList());
                    }
                    @Override
                    public void onFail(int code) {
                        Log.i("error : " , code+"");
                    }
                });
    }

}
