package com.example.androidchoi.jobdam;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.androidchoi.jobdam.Adpater.JobItemAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.JobData;
import com.example.androidchoi.jobdam.Model.JobList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllJobFragment extends Fragment {

    private final static int SHOW_JOB_MAX = 30;
    private String job_ordering;
    private String job_region;
    private String job_kind;
    private String job_type;
    private String job_keyword;
    int page;

    ListView mListView;
    TextView mTextView;
    JobItemAdapter mAdapter;
    EditText mSearchEdit;
    ImageView mDeleteImage;
    Spinner mSpinnerJobOrder;
    Spinner mSpinnerJobKind;
    Spinner mSpinnerJobType;
    ArrayAdapter<String>[] mArrayAdapters;
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
        View countHeaderView = inflater.inflate(R.layout.view_all_job_count_header, null);
        ToggleButton toggleButton = (ToggleButton)countHeaderView.findViewById(R.id.btn_order_toggle);
        toggleButton.setVisibility(View.VISIBLE);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    job_ordering = "da";
                }else {
                    job_ordering = "pd";
                }
                searchJob();
            }
        });

        View shadowToolbar = getActivity().findViewById(R.id.toolbar_shadow);
        shadowToolbar.setVisibility(View.GONE);
        mListView = (ListView) view.findViewById(R.id.listview_all_job);
        mListView.addHeaderView(searchHeaderView);
        mListView.addHeaderView(countHeaderView, null, false);
        mDeleteImage = (ImageView) searchHeaderView.findViewById(R.id.image_search_delete);
        mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEdit.setText("");
                searchJob();
            }
        });
        mSearchEdit = (EditText) searchHeaderView.findViewById(R.id.editText_search_bar);
        mSearchEdit.setHint("기업을 검색해주세요");
        mSearchEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //do here your stuff
                    job_keyword = v.getText().toString();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    searchJob();
                    return true;
                }
                return false;
            }
        });
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
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
                JobData data = (JobData) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(JobData.JOBITEM, data);
                startActivity(intent);
            }
        });
        mTextView = (TextView) view.findViewById(R.id.text_item_count);
        initArrayAdapter();

        //최신순, 마감순
        mSpinnerJobOrder = (Spinner)view.findViewById(R.id.spinner_job_region);
        mSpinnerJobOrder.setAdapter(mArrayAdapters[0]);
        mSpinnerJobOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: // 서울
                        job_region = "101000";
                        break;
                    case 2: // 경기,인천
                        job_region = "102000+108000";
                        break;
                    case 3: // 광주
                        job_region = "103000";
                        break;
                    case 4: // 대구
                        job_region = "104000";
                        break;
                    case 5: // 대전
                        job_region = "101000+118000";
                        break;
                    case 6: // 부산, 울산
                        job_region = "106000 + 107000";
                        break;
                    case 7: // 강원
                        job_region = "109000";
                        break;
                    case 8: // 경남,경북
                        job_region = "110000+111000";
                        break;
                    case 9: // 전남, 전북
                        job_region = "112000+113000";
                        break;
                    case 10: // 충남, 충북
                        job_region = "114000+115000";
                        break;
                    case 11: // 제주
                        job_region = "116000";
                        break;
                    case 12: // 해외
                        job_region = "210000+220000+230000+240000+" +
                                "250000+260000+270000+280000";

                        break;
                    case 0: // 전체
                    default:
                        job_region = "117000";
                        break;
                }
                searchJob();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 직무별
        mSpinnerJobKind = (Spinner)view.findViewById(R.id.spinner_job_kind);
        mSpinnerJobKind.setAdapter(mArrayAdapters[1]);
        mSpinnerJobKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: // 경영, 사무
                        job_kind = "1";
                        break;
                    case 2: // 영업, 고객상담
                        job_kind = "2";
                        break;
                    case 3: // 생산, 제조
                        job_kind = "3";
                        break;
                    case 4: // IT, 인터넷
                        job_kind = "4";
                        break;
                    case 5: // 전문직
                        job_kind = "5";
                        break;
                    case 6: // 교육
                        job_kind = "6";
                        break;
                    case 7: // 미디어
                        job_kind = "7";
                        break;
                    case 8: // 건설
                        job_kind = "9";
                        break;
                    case 9: // 유통,무역
                        job_kind = "10";
                        break;
                    case 10: // 서비스
                        job_kind = "11";
                        break;
                    case 11: // 디자인
                        job_kind = "12";
                        break;
                    case 12: // 의료
                        job_kind = "13";
                        break;
                    case 13: // 기타
                        job_kind = "8";
                        break;
                    case 0:
                    default:
                        job_kind = null;
                        break;
                }
                searchJob();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 채용 형태
        mSpinnerJobType = (Spinner)view.findViewById(R.id.spinner_job_type);
        mSpinnerJobType.setAdapter(mArrayAdapters[2]);
        mSpinnerJobType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: //정규직
                        job_type = "1";
                        break;
                    case 2: //계약직
                        job_type = "2+10";
                        break;
                    case 3: //인턴직
                        job_type = "4+11";
                        break;
                    case 4: //시간제,일용직
                        job_type = "5";
                        break;
                    case 5: //프리랜서
                        job_type = "9";
                        break;
                    case 6: //전임
                        job_type = "15";
                        break;
                    case 7: //해외
                        job_type = "6+7";
                        break;
                    case 8: //기타
                        job_type = "3+8+12+13+14";
                        break;
                    case 0: // 최신순
                    default:
                        job_type = null;
                        break;
                }
                searchJob();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    private void getMoreItem() {
        if (!isUpdate) {
            final int startIndex = mAdapter.getStartIndex();
            if (startIndex != -1) {
                isUpdate = true;
                NetworkManager.getInstance().getJobAPI(getActivity(), job_keyword, job_ordering, job_region, job_kind, job_type,
                        page, SHOW_JOB_MAX, new NetworkManager.OnResultListener<JobList>() {
                    @Override
                    public void onSuccess(JobList result) {
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
        NetworkManager.getInstance().getJobAPI(getActivity(), job_keyword, job_ordering, job_region, job_kind, job_type,
                page, SHOW_JOB_MAX, new NetworkManager.OnResultListener<JobList>() {
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
    private void initArrayAdapter() {
        mArrayAdapters = new ArrayAdapter[3];
        String[][] StringArray = new String[3][];
        StringArray[0] = getResources().getStringArray(R.array.job_region);
        StringArray[1] = getResources().getStringArray(R.array.job_kind);
        StringArray[2] = getResources().getStringArray(R.array.job_type);
        for(int i=0; i<mArrayAdapters.length; i++){
            mArrayAdapters[i] = new ArrayAdapter<String>(getActivity(),R.layout.spinner_header_item);
            mArrayAdapters[i].setDropDownViewResource(R.layout.spinner_dropdown_item);
            mArrayAdapters[i].addAll(StringArray[i]);
        }
    }
}
