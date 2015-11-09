package com.example.androidchoi.jobdam;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.JobItemAdapter;
import com.example.androidchoi.jobdam.Model.JobData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobListFragment extends Fragment {
    ListView mListView;
    JobItemAdapter mAdapter;
    EditText mSearchEdit;
    ImageView mDeleteImage;

//    public static MyJobListFragment newInstance(int page){
//        Bundle args = new Bundle();
//        args.putInt("ddd",page);
//        MyJobListFragment myJobListFragment = new MyJobListFragment();
//        myJobListFragment.setArguments(args);
//        return myJobListFragment;
//    }

    public MyJobListFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FrameLayout touchInterceptor = (FrameLayout)getActivity().findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mSearchEdit.isFocused()) {
                        Rect outRect = new Rect();
                        mSearchEdit.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mSearchEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job_list, container, false);
        View searchHeaderView = inflater.inflate(R.layout.view_item_search_header, null);
        View countHeaderView = inflater.inflate(R.layout.view_item_count_header, null);
        mListView = (ListView)view.findViewById(R.id.listview_my_job);
        mListView.addHeaderView(searchHeaderView);
        mListView.addHeaderView(countHeaderView, null, false);
        mDeleteImage = (ImageView)searchHeaderView.findViewById(R.id.image_search_delete);
        mSearchEdit = (EditText)searchHeaderView.findViewById(R.id.editText_search_bar);
        mSearchEdit.setHint("기업을 검색해주세요");
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (!string.equals("")) {
                    mDeleteImage.setVisibility(View.VISIBLE);
                } else {
                    mDeleteImage.setVisibility(View.GONE);
                }
            }
        });
        mAdapter = new JobItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobData data = (JobData) mAdapter.getItem(position - 1);
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(JobData.JOBITEM, data);
                startActivity(intent);
            }
        });
        initData();
        TextView textView = (TextView)view.findViewById(R.id.text_item_count);
        textView.setText("총 "+ mAdapter.getCount() + "건");
        return view;
    }
    private void initData() {
//        for(int i = 0; i<5; i++) {
//            JobData data = new JobData();
//            data.setJobTitle("기업" + i);
//            mAdapter.add(data);
//        }
    }
}
