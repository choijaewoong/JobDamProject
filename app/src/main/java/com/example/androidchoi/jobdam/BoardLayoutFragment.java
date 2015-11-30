package com.example.androidchoi.jobdam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.LayoutAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.ArticleLab;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardLayoutFragment extends Fragment {

    private static final int REQUEST_WRITE = 1;
    private View mViewRoot;
    private RecyclerViewPager mRecyclerView;
    private LayoutAdapter mLayoutAdapter;
    ImageView articleWriteImage;
    private Toast mToast;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (mViewRoot == null) {
            mViewRoot = inflater.inflate(R.layout.fragment_board_layout, container, false);
        } else if (mViewRoot.getParent() != null) {
            ((ViewGroup) mViewRoot.getParent()).removeView(mViewRoot);
        }
        View shadowToolbar = getActivity().findViewById(R.id.toolbar_shadow);
        shadowToolbar.setVisibility(View.VISIBLE);

        articleWriteImage =(ImageView)mViewRoot.findViewById(R.id.image_article_write_button);
        articleWriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleWriteActivity.class);
                startActivityForResult(intent, REQUEST_WRITE);
            }
        });
        return mViewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Activity activity = getActivity();

//        mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
//        mToast.setGravity(Gravity.CENTER, 0, 0);

        mRecyclerView = (RecyclerViewPager) view.findViewById(R.id.view_pager_article);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layout);
        mLayoutAdapter = new LayoutAdapter(activity, mRecyclerView);
        mRecyclerView.setAdapter(mLayoutAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);

//        mPositionText = (TextView) view.getRootView().findViewById(R.id.position);
//        mCountText = (TextView) view.getRootView().findViewById(R.id.count);
//
//        mStateText = (TextView) view.getRootView().findViewById(R.id.state);
        updateState(RecyclerView.SCROLL_STATE_IDLE);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width)/2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    float rate = 0;

                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                        v.setScaleX(1 - rate * 0.1f);

                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                        v.setScaleX(0.9f + rate * 0.1f);
                    }
                }
            }
        });
        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        if(mRecyclerView.getCurrentPosition()==0) {
                            View v1 = mRecyclerView.getChildAt(1);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        } else {
                            View v1 = mRecyclerView.getChildAt(0);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        }
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                        v0.setScaleX(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                        v2.setScaleX(0.9f);
                    }
                }

            }
        });
    }

    private void updateState(int scrollState) {
        String stateName = "Undefined";
        switch (scrollState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                stateName = "Idle";
                break;

            case RecyclerView.SCROLL_STATE_DRAGGING:
                stateName = "Dragging";
                break;

            case RecyclerView.SCROLL_STATE_SETTLING:
                stateName = "Flinging";
                break;
        }
    }
    public void showArticle(){
        NetworkManager.getInstance().showArticle(getActivity(),
                new NetworkManager.OnResultListener<ArticleLab>() {
                    @Override
                    public void onSuccess(ArticleLab result) {
                        //서버에서 게시글 리스트 가져와 저장
                        mLayoutAdapter.setItems(result.getArticleList());
//                        mRecyclerView..setCurrentItem(0,true);
                    }
                    @Override
                    public void onFail(int code) {
                        Toast.makeText(getActivity(), "error : " + code, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
