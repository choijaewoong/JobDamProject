package com.example.androidchoi.jobdam;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.JobItemAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.JobData;
import com.example.androidchoi.jobdam.Model.JobList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllJobFragment extends Fragment {

    private final static int SHOW_JOB_MAX = 110;
    private String job_sort;
    private String job_kind;
    private String job_type;
    private String job_keyword;
    int page;

    ListView mListView;
    TextView mTextView;
    JobItemAdapter mAdapter;
    private List<JobData> mJobDataList;
    EditText mSearchEdit;
    ImageView mDeleteImage;
    boolean isUpdate = false;
    boolean isLastItem = false;

    public AllJobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView) getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.all_job);

        searchJob();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getMoreItem();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });

        FrameLayout touchInterceptor = (FrameLayout) getActivity().findViewById(R.id.touchInterceptor);
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
        View view = inflater.inflate(R.layout.fragment_all_job, container, false);
        View searchHeaderView = inflater.inflate(R.layout.view_item_search_header, null);
        View countHeaderView = inflater.inflate(R.layout.view_item_count_header, null);

        mListView = (ListView) view.findViewById(R.id.listview_all_job);
        mListView.addHeaderView(searchHeaderView);
        mListView.addHeaderView(countHeaderView, null, false);
        mDeleteImage = (ImageView) searchHeaderView.findViewById(R.id.image_search_delete);
        mSearchEdit = (EditText) searchHeaderView.findViewById(R.id.editText_search_bar);
        mSearchEdit.setHint("기업을 검색해주세요");
        mSearchEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //do here your stuff
                    Toast.makeText(getActivity(), v.getText() , Toast.LENGTH_SHORT).show();
                    job_keyword = v.getText().toString();
                    searchJob();
                    return true;
                }
                return false;
            }
        });
        mAdapter = new JobItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobData data = (JobData) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(JobData.JOBITEM, data);
                startActivity(intent);
            }
        });
        mTextView = (TextView) view.findViewById(R.id.text_item_count);
        return view;
    }
    private void getMoreItem() {
        if (!isUpdate) {
            final int startIndex = mAdapter.getStartIndex();
            if (startIndex != -1) {
                isUpdate = true;
                NetworkManager.getInstance().getJobAPI(getActivity(), job_keyword, page, 110, new NetworkManager.OnResultListener<JobList>() {
                    @Override
                    public void onSuccess(JobList result) {
//                        Toast.makeText(getActivity(), startIndex+"/" + result.getJobList().size() + "/ " + mAdapter.getCount(), Toast.LENGTH_SHORT).show();
                        page++;
                        for (Job item : result.getJobList()) {
                            mAdapter.add(item);
                        }
                        isUpdate = false;
                    }

                    @Override
                    public void onFail(int code) {
                        isUpdate = false;
                    }
                });
            }
        }
    }

    public void searchJob(){
        page = 0;
        NetworkManager.getInstance().getJobAPI(getActivity(), job_keyword, page, SHOW_JOB_MAX,
                new NetworkManager.OnResultListener<JobList>() {
                    @Override
                    public void onSuccess(JobList result) {
                        page++;
                        mAdapter.setItems(result.getJobList());
                        mAdapter.setTotalCount(result.getTotal());
                        mTextView.setText("공채정보 총 " + result.getTotal() + "건");
                        job_keyword = null;
                    }

                    @Override
                    public void onFail(int code) {
                        Toast.makeText(getActivity(), "error : " + code, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}