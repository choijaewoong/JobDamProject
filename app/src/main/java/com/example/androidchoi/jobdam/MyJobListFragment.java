package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.MyJobItemAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.MyJobLab;
import com.example.androidchoi.jobdam.Model.MyJobs;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobListFragment extends Fragment {

    SwipeRefreshLayout mRefreshLayout;
    ListView mListView;
    MyJobItemAdapter mAdapter;
    TextView mCountTextView;
    private ArrayList<MyJobs> mJobList;
    View countHeaderView;
    ArrayList<Integer> checkedItems = new ArrayList<Integer>();

    MainActivity.OnMyJobListCallBack callback = new MainActivity.OnMyJobListCallBack() {
        @Override
        public boolean onCheckMode() {
            if (mListView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                return true;
            }
            return false;
        }

        @Override
        public void onChangeMode() {
            defaultMode();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setOnMyJobListCallback(callback);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job_list, container, false);
        countHeaderView = inflater.inflate(R.layout.view_header_item_count, null);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_my_job);
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        mRefreshLayout.setColorSchemeResources(android.R.color.white);
        mListView = (ListView) view.findViewById(R.id.listview_my_job);
        mListView.addHeaderView(countHeaderView, null, false);
        mAdapter = new MyJobItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mListView.getCheckedItemCount() + " 개 선택");
                if (mListView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                    for (int i = 0; i < checkedItems.size(); i++) {
                        if (checkedItems.get(i).equals(position)) {
                            checkedItems.remove(i);
                            return;
                        }
                    }
                    checkedItems.add(position);
                    return;
                }
                Job data = (Job) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(Job.JOBITEM, data);
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
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMyJob();
            }
        });
        mCountTextView = (TextView) view.findViewById(R.id.text_item_count);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showMyJob();
    }

    public void showMyJob() {
        NetworkManager.getInstance().showMyJob(getActivity(), new NetworkManager.OnResultListener<MyJobLab>() {
            @Override
            public void onSuccess(MyJobLab result) {
                mJobList = result.getJobList();
                mAdapter.setItems(mJobList);
                mCountTextView.setText(Html.fromHtml("총 <font color=#0db5f7>" + mAdapter.getCount() + "</font>건"));
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail(int code) {
                Log.i("error : ", code + "");
            }
        });
    }

    public void deleteMode() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        super.setMenuVisibility(true);
        mRefreshLayout.setEnabled(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.colorNavHeaderBackground));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mListView.getCheckedItemCount() + " 개 선택");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        ((MyJobFragment) getParentFragment()).getViewPager().setPagingEnabled(false);
        ((MyJobFragment) getParentFragment()).getTabLayout().setVisibility(View.GONE);
    }

    public void defaultMode() {
        super.setMenuVisibility(false);
        for (int i = 0; i < checkedItems.size(); i++) {
            mListView.setItemChecked(checkedItems.get(i), false);
        }
        checkedItems.clear();
        mRefreshLayout.setEnabled(true);
        mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.colorPrimary));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MyJobFragment) getParentFragment()).getViewPager().setPagingEnabled(true);
        ((MyJobFragment) getParentFragment()).getTabLayout().setVisibility(View.VISIBLE);
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
        if (id == android.R.id.home) {
            defaultMode();
            return true;
        } else if (id == R.id.action_delete) {
            List<Integer> jobIdList = new ArrayList<Integer>();
            for (int i = 0; i < checkedItems.size(); i++) {
                jobIdList.add(((Job) mAdapter.getItem(checkedItems.get(i) - mListView.getHeaderViewsCount())).getId());
            }
            NetworkManager.getInstance().deleteMyJob(getActivity(), jobIdList, new NetworkManager.OnResultListener<String>() {
                @Override
                public void onSuccess(String result) {
                    showMyJob();
                }

                @Override
                public void onFail(int code) {
                    Log.i("code", code + " ");
                }
            });
            defaultMode();
            return true;
        }
        return true;
    }

}
