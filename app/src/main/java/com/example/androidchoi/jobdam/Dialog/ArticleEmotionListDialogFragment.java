package com.example.androidchoi.jobdam.Dialog;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.androidchoi.jobdam.Adpater.RecyclerAdapter;
import com.example.androidchoi.jobdam.Adpater.ViewHolder;
import com.example.androidchoi.jobdam.ArticleWriteActivity;
import com.example.androidchoi.jobdam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleEmotionListDialogFragment extends DialogFragment {

    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;

    public ArticleEmotionListDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.EmotionDialog);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        dialog.getWindow().setWindowAnimations(R.style.EmotionDialogAnimation);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.TOP;
        params.y = (getResources().getDimensionPixelSize(R.dimen.emotion_dialog_top_margin));
        dialog.getWindow().setAttributes(params);
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        dialog.getWindow().setDimAmount(0.0f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_emotion_list_dialog, container, false);
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_emotion);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new VerticalDividerDecoration(getActivity()));
        mAdapter = new RecyclerAdapter();
        mAdapter.setOnItemClickListener(new ViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ((ArticleWriteActivity)getActivity()).setEmotion(position);
                dismiss();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ArticleWriteActivity)getActivity()).setArrow();
    }
}
