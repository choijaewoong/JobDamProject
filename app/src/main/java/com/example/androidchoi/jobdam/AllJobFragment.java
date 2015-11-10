package com.example.androidchoi.jobdam;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
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

import com.begentgroup.xmlparser.XMLParser;
import com.example.androidchoi.jobdam.Adpater.JobItemAdapter;
import com.example.androidchoi.jobdam.Model.AllJobListData;
import com.example.androidchoi.jobdam.Model.JobData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllJobFragment extends Fragment {

    ListView mListView;
    TextView mTextView;
    JobItemAdapter mAdapter;
    private List<JobData> mJobDataList;
    EditText mSearchEdit;
    ImageView mDeleteImage;

    public AllJobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.all_job);

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XmlParsingTask xmlParsingTask = new XmlParsingTask();
        xmlParsingTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_job, container, false);
        View searchHeaderView = inflater.inflate(R.layout.view_item_search_header, null);
        View countHeaderView = inflater.inflate(R.layout.view_item_count_header, null);

        mListView = (ListView)view.findViewById(R.id.listview_all_job);
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
//        initData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobData data = (JobData) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(JobData.JOBITEM, data);
                startActivity(intent);
            }
        });
        mTextView = (TextView)view.findViewById(R.id.text_item_count);
        return view;
    }

    public static final String JOB_URL = "http://api.saramin.co.kr/job-search?stock=kospi+kosdaq&sr=directhire&fields=posting-date+expiration-date+keyword-code+count&count=10";
    private class XmlParsingTask extends AsyncTask<String, Integer, AllJobListData>{

        @Override
        protected AllJobListData doInBackground(String... params) {
            try{
                URL url = new URL(JOB_URL);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                int code = conn.getResponseCode();
                if(code == HttpURLConnection.HTTP_OK){
                    XMLParser parser = new XMLParser();
                    AllJobListData allJobList = parser.fromXml(conn.getInputStream(), "jobs", AllJobListData.class);
                    return allJobList;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(AllJobListData allJobList) {
            super.onPostExecute(allJobList);
            mAdapter.setItems(allJobList.getJobList());
            mListView.setAdapter(mAdapter);
            mTextView.setText("공채정보 총 " + mAdapter.getCount() + "건");
        }
    }
}
