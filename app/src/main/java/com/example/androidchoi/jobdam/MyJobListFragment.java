package com.example.androidchoi.jobdam;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.MyJobItemAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.MyJobLab;
import com.example.androidchoi.jobdam.Model.MyJobs;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobListFragment extends Fragment {

    ListView mListView;
    MyJobItemAdapter mAdapter;
    EditText mSearchEdit;
    ImageView mDeleteImage;
    TextView mCountTextView;
    private ArrayList<MyJobs> mJobList;

    public MyJobListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Toast.makeText(getActivity(), "MyJob이 갱신 되었습니다.", Toast.LENGTH_SHORT).show();
        showMyJob();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        View view = inflater.inflate(R.layout.fragment_my_job_list, container, false);
        View searchHeaderView = inflater.inflate(R.layout.view_header_item_search, null);
        View countHeaderView = inflater.inflate(R.layout.view_header_item_count, null);
        mListView = (ListView) view.findViewById(R.id.listview_my_job);
        mListView.addHeaderView(searchHeaderView);
        mListView.addHeaderView(countHeaderView, null, false);
        mDeleteImage = (ImageView) searchHeaderView.findViewById(R.id.image_search_delete);
        mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEdit.setText("");
            }
        });
        mSearchEdit = (EditText) searchHeaderView.findViewById(R.id.editText_search_bar);
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
                    mDeleteImage.setVisibility(View.INVISIBLE);
                }
            }
        });
        mAdapter = new MyJobItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mListView.getCheckedItemCount() + " 개 선택");
                    for(int i = 0; i< mAdapter.getCheckedItemIndexList().size(); i++){
                        if(mAdapter.getCheckedItemIndexList().get(i).equals(position-mListView.getHeaderViewsCount())){
                            mAdapter.getCheckedItemIndexList().remove(i);
                            mAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                    mAdapter.getCheckedItemIndexList().add(position-mListView.getHeaderViewsCount());
                    mAdapter.notifyDataSetChanged();
                    return;
                }
                Job data = (Job) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(Job.JOBITEM, data);
                intent.putExtra(Job.JOB_SCRAP_CHECK, true);
                startActivity(intent);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteMode();
                return true;
            }
        });
        mCountTextView = (TextView) view.findViewById(R.id.text_item_count);
        showMyJob();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    public void showMyJob() {
        NetworkManager.getInstance().showMyJob(getActivity(), new NetworkManager.OnResultListener<MyJobLab>() {
            @Override
            public void onSuccess(MyJobLab result) {
                mJobList = result.getJobList();
                mAdapter.setItems(mJobList);
                mCountTextView.setText(Html.fromHtml("총 <font color=#0db5f7>" + mAdapter.getCount() + "</font>건"));
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(getActivity(), code + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteMode(){
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        super.setMenuVisibility(true);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.colorNavHeaderBackground));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mListView.getCheckedItemCount() + " 개 선택");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        ((MyJobFragment)getParentFragment()).getViewPager().setPagingEnabled(false);
        ((MyJobFragment)getParentFragment()).getTabLayout().setVisibility(View.GONE);
    }

    public void defaultMode(){
        super.setMenuVisibility(false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.colorPrimary));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MyJobFragment)getParentFragment()).getViewPager().setPagingEnabled(true);
        ((MyJobFragment)getParentFragment()).getTabLayout().setVisibility(View.VISIBLE);
        mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_my_job, menu);
    }
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {

            // mAdapter.getCheckedItemIndexList() 보내 삭제 요청
            for(int i =0; i<mAdapter.getCheckedItemIndexList().size(); i++){
                mListView.setItemChecked(mAdapter.getCheckedItemIndexList().get(i)+mListView.getHeaderViewsCount(), false);
            }
            mAdapter.getCheckedItemIndexList().clear();
            mAdapter.notifyDataSetChanged();
            defaultMode();
            return false;
        } else if (id == R.id.action_cancel) {
            for(int i =0; i<mAdapter.getCheckedItemIndexList().size(); i++){
                mListView.setItemChecked(mAdapter.getCheckedItemIndexList().get(i)+mListView.getHeaderViewsCount(), false);
            }
            mAdapter.getCheckedItemIndexList().clear();
            mAdapter.notifyDataSetChanged();
            defaultMode();
            return false;
        }
        return false;
    }
}
