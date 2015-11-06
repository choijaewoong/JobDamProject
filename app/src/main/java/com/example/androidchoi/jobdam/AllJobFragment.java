package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    public AllJobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.all_job);
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
        View headerView = inflater.inflate(R.layout.view_job_item_count_header, null);

        mListView = (ListView)view.findViewById(R.id.listview_all_job);
        mListView.addHeaderView(headerView);
        mAdapter = new JobItemAdapter();
//        initData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobData data = (JobData) mAdapter.getItem(position - 1);
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(JobData.JOBITEM, data);
                startActivity(intent);
            }
        });
        mTextView = (TextView)view.findViewById(R.id.text_job_item_count);
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
            mAdapter.addList(allJobList.getJobList());
            mListView.setAdapter(mAdapter);
            mTextView.setText("공채정보 총 " + mAdapter.getCount() + "건");
        }
    }
}
