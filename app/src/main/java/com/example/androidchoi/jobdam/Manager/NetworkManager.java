package com.example.androidchoi.jobdam.Manager;

import android.content.Context;
import android.util.Log;

import com.begentgroup.xmlparser.XMLParser;
import com.example.androidchoi.jobdam.Model.ArticleLab;
import com.example.androidchoi.jobdam.Model.Articles;
import com.example.androidchoi.jobdam.Model.JobList;
import com.example.androidchoi.jobdam.Model.LoginData;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.example.androidchoi.jobdam.Model.MyJobLab;
import com.example.androidchoi.jobdam.Model.QuestionLab;
import com.example.androidchoi.jobdam.Model.ScrapCheck;
import com.example.androidchoi.jobdam.Model.Tags;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dongja94 on 2015-10-21.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    AsyncHttpClient client;
    XMLParser parser;
    Gson gson;

    private NetworkManager() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = new AsyncHttpClient();
            client.setSSLSocketFactory(socketFactory);
            client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        parser = new XMLParser();
        gson = new Gson();
        client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
    }

    public HttpClient getHttpClient() {
        return client.getHttpClient();
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);

        public void onFail(int code);
    }

    private static final String API_ADDRESS = "http://api.saramin.co.kr/job-search";
//    ?stock=kospi+kosdaq&sr=directhire&fields=posting-date+expiration-date+keyword-code+count&count=10

    private static final String JOB_ORDER = "sort";
    private static final String JOB_REGION = "loc_mcd";
    private static final String JOB_KIND = "job_category";
    private static final String JOB_TYPE = "job_type";
    private static final String START = "start";
    private static final String COUNT = "count";
    private static final String KEYWORD = "keywords";

    // 사람인 api 불러오는 method
    public void getJobAPI(Context context, String keyword, String job_ordering, String job_region, String job_kind, String job_type,
                          int start, int count, final OnResultListener<JobList> listener) {
        final RequestParams params = new RequestParams();
        params.put("bbs_gb", 1);
        params.put(KEYWORD, keyword);
        params.put(JOB_ORDER, job_ordering);
        params.put(JOB_REGION, job_region);
        params.put(JOB_KIND, job_kind);
        params.put(JOB_TYPE, job_type);
        params.put(START, start);
        params.put(COUNT, count);
        client.get(context, API_ADDRESS, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final byte[] responseBody) {

                ByteArrayInputStream bais = new ByteArrayInputStream(responseBody);
                final JobList jobList = parser.fromXml(bais, "jobs", JobList.class);
                listener.onSuccess(jobList);
//                for(final JobData job : jobList.getJobList()) {
//                    MyTask myTask = new MyTask();
//                    myTask.setOnImagListener(new MyTask.OnImageListener() {
//                        @Override
//                        public void onSuccess() {
//                            listener.onSuccess(jobList);
//                        }
//                    });
//                    myTask.execute(job);
//                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFail(statusCode);
            }
        });
    }
//    static class MyTask extends AsyncTask<JobData, Void, Void> {
//        public interface OnImageListener {
//            public void onSuccess();
//        }
//        OnImageListener mListener;
//        public void setOnImagListener(OnImageListener listener) {
//            mListener = listener;
//        }
//        @Override
//        protected Void doInBackground(JobData... params) {
//            Document doc = null;
//            try {
//                for (JobData job : params) {
//                    doc = Jsoup.connect(job.getSiteUrl()).get();
//
//                    Element img = doc.select("table.corp_logo img").first();
//                    if (img != null) {
////                    setCompanyImage(img.attr("src"));
//                        job.setCompanyImage(img.attr("src"));
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            mListener.onSuccess();
//        }
//    }

    private static final String SERVER = "http://52.69.235.46:3000";
    // 내가 담은 채용정보 불러오는 method
    private static final String SHOW_MY_JOB = SERVER + "/showmyscrap";
    public void showMyJob(Context context, final OnResultListener<MyJobLab> listener) {
        RequestParams params = new RequestParams();
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        client.get(context, SHOW_MY_JOB, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MyJobLab myJobLab = new MyJobLab();
                try {
                    myJobLab = gson.fromJson(responseString, MyJobLab.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(myJobLab);
            }
        });
    }

    private static final String CHECK_JOB_SCRAP = SERVER + "/scrapcheck/%s";
    public void checkJobScrap(Context context, int jobId, final OnResultListener<ScrapCheck> listener){
        RequestParams params = new RequestParams();
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        String url = String.format(CHECK_JOB_SCRAP, jobId);
        client.get(context, url, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    ScrapCheck scrap = gson.fromJson(responseString, ScrapCheck.class);
                    listener.onSuccess(scrap);
                }catch (JsonSyntaxException e){
                    listener.onSuccess(new ScrapCheck());
                    e.printStackTrace();
                }
            }
        });
    }

    private static final String SHOW_JOB_QUESTION = SERVER + "/jasoseo/%s";
    public void showJobQuestion(Context context, int jobId, final OnResultListener<QuestionLab> listener) {
        RequestParams params = new RequestParams();
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        String url = String.format(SHOW_JOB_QUESTION, jobId);
        client.get(context, url, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("QuestionLab", responseString);
                QuestionLab questionLab = new QuestionLab();
                try {
                    questionLab = gson.fromJson(responseString, QuestionLab.class);
//                    questionLab.getQuestions().getQuestionList().get(0).addTestTag();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(questionLab);
            }
        });
    }

    private static final String ADD_QUESTION_TAG = SERVER + "/jasoseotag";
    public void addQuestionTag(Context context, int jobId, ArrayList<String> memoId, int questionNumber, final OnResultListener<String> listener) {
        RequestParams params = new RequestParams();
        params.put("job_id", jobId);
        params.put("Qnum", questionNumber);
        for (String id : memoId) {
            params.add("memo_id", id);
        }
        client.post(context, ADD_QUESTION_TAG, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                listener.onSuccess(responseString);
            }
        });
    }

    // 채용정보 담기
    private static final String ADD_MY_JOB = SERVER + "/addscrap";

    public void addMyJob(Context context, final String jsonString, final OnResultListener<String> listener) {
        RequestParams params = new RequestParams();
        try {
            client.post(context, ADD_MY_JOB, new StringEntity(jsonString, "UTF-8"), "application/json", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    listener.onFail(statusCode);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    listener.onSuccess(responseString);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 채용정보 삭제
    private static final String DELETE_MY_JOB = SERVER + "/myscrapd";
    public void deleteMyJob(Context context, List<Integer> jobIds, final OnResultListener<String> listener) {
        RequestParams params = new RequestParams();
        for (int id : jobIds) {
            params.put("job_id", id);
        }
        client.post(context, DELETE_MY_JOB, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                listener.onSuccess(responseString);
            }
        });
    }

    //메모 보기
    public static final String SHOW_MY_MEMO = SERVER + "/mymemolist";
    public void showMyMemo(Context context, final OnResultListener<MyCardLab> listener) {
        RequestParams params = new RequestParams();
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        client.get(context, SHOW_MY_MEMO, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MyCardLab myCardLab = new MyCardLab();
                try {
                    myCardLab = gson.fromJson(responseString, MyCardLab.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(myCardLab);
            }
        });
    }

    public static final String SHOW_FILTERED_MEMO = SERVER + "/folderlist";
    public void showFilteredMemo(Context context, int index, final OnResultListener<MyCardLab> listener) {
        RequestParams params = new RequestParams();
        params.put("category", index);
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        client.post(context, SHOW_FILTERED_MEMO, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MyCardLab myCardLab = new MyCardLab();
                try {
                    myCardLab = gson.fromJson(responseString, MyCardLab.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(myCardLab);
            }
        });
    }

    private static final String SHOW_MEMO_WITH_TAG = SERVER + "/findtag/%s";
    public void showMemoWithTag(Context context, String tag, final OnResultListener<MyCardLab> listener) {
        RequestParams params = new RequestParams();
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        String url = String.format(SHOW_MEMO_WITH_TAG, tag);
        client.get(context, url, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MyCardLab myCardLab = new MyCardLab();
                try {
                    myCardLab = gson.fromJson(responseString, MyCardLab.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(myCardLab);
            }
        });
    }

    // 메모 추가
    private static final String ADD_MEMO = SERVER + "/addmemo";
    public void addMemo(Context context, final String jsonString, final OnResultListener<String> listener) {
        try {
            client.post(context, ADD_MEMO, new StringEntity(jsonString, "UTF-8"), "application/json", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    listener.onSuccess(responseString);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //메모 수정
    private static final String UPDATE_MEMO = SERVER + "/memo/update";
    public void updateMemo(Context context, final String jsonString, final OnResultListener<String> listener) {
        try {
            client.post(context, UPDATE_MEMO, new StringEntity(jsonString, "UTF-8"), "application/json", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    listener.onSuccess(responseString);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //메모 삭제
    private static final String DELETE_MEMO = SERVER + "/memo/delete";
    public void deleteMemo(Context context, List<String> memoIds, final OnResultListener<String> listener){
        RequestParams params = new RequestParams();
        for(String id : memoIds){
            params.add("memo_id", id);
        }
        client.post(context, DELETE_MEMO, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                listener.onSuccess(responseString);
            }
        });
    }

    private static final String CHANGE_MEMO_CATEGORY = SERVER + "/movingfolder";
    public void changeMemoCategory(Context context, List<String> memoIds, int categoryIndex, final OnResultListener<String> listener){
        RequestParams params = new RequestParams();
        params.put("category", categoryIndex);
        for(String id : memoIds){
            params.add("memo_id", id);
        }
        client.post(context, CHANGE_MEMO_CATEGORY, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                listener.onSuccess(responseString);
            }
        });
    }

    public static final String SHOW_MEMO_TAG = SERVER + "/memotaglist";
    public void showCardTag(Context context, final OnResultListener<Tags> listener) {
        RequestParams params = new RequestParams();
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        client.get(context, SHOW_MEMO_TAG, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Tags tagList = new Tags();
                try {
                    tagList = gson.fromJson(responseString, Tags.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(tagList);
            }
        });
    }

    //게시글 보기
    public static final String SHOW_ARTICLE = SERVER + "/boardlist";
    public void showArticle(Context context, final OnResultListener<ArticleLab> listener) {
        RequestParams params = new RequestParams();
        params.put(ARTICLE_PAGE, 1);
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        client.get(context, SHOW_ARTICLE, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ArticleLab articleLab = new ArticleLab();
                try {
                    articleLab = gson.fromJson(responseString, ArticleLab.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(articleLab);
            }
        });
    }

    //내가 쓴 게시글 보기
    public static final String SHOW_MY_ARTICLE = SERVER + "/myboardlist";
    public static final String ARTICLE_PAGE = "page";
    public void showMyArticle(Context context, final OnResultListener<ArticleLab> listener) {
        RequestParams params = new RequestParams();
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        client.get(context, SHOW_MY_ARTICLE, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ArticleLab articleLab = new ArticleLab();
                try {
                    articleLab = gson.fromJson(responseString, ArticleLab.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(articleLab);
            }
        });
    }

    // 게시글 추가
    private static final String ADD_ARTICLE = SERVER + "/addboard";
    public void addArticle(Context context, String content, int emotion, long timeStamp, final OnResultListener<String> listener) {
        RequestParams params = new RequestParams();
        params.put("content", content);
        params.put("emotionIndex", emotion);
        params.put("writeTimeStamp", timeStamp);
        client.post(context, ADD_ARTICLE, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                listener.onSuccess(responseString);
            }
        });
    }

    //게시글 수정
    private static final String UPDATE_ARTICLE = SERVER + "/board/update";
    public void updateArticle(Context context, final String jsonString, final OnResultListener<String> listener) {
        try {
            client.post(context, UPDATE_ARTICLE, new StringEntity(jsonString, "UTF-8"), "application/json", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    listener.onSuccess(responseString);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 좋아요
    public static final String LIKE_ARTICLE = SERVER + "/like/%s";
    public void likeArticle(Context context, String board_id, final OnResultListener<Articles> listener) {
        RequestParams params = new RequestParams();
        String url = String.format(LIKE_ARTICLE, board_id);
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("Accept", "application/json");
        client.get(context, url, headers, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Articles articles = new Articles();
                try {
                    articles = gson.fromJson(responseString, Articles.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(articles);
            }
        });
    }

    public static final String LOG_IN = SERVER + "/login";
    public void login(Context context, String userid, String password, final OnResultListener<LoginData> listener) {
        RequestParams params = new RequestParams();
        params.put("user_id", userid);
        params.put("pw", password);
        client.post(context, LOG_IN, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LoginData loginData = new Gson().fromJson(responseString, LoginData.class);
                listener.onSuccess(loginData);
            }
        });
    }

    public static final String SIGN_UP = SERVER + "/signup";
    public void signup(Context context, String userid, String password, String name, final OnResultListener<LoginData> listener) {
        RequestParams params = new RequestParams();
        params.put("user_id", userid);
        params.put("pw", password);
        params.put("name", name);
        client.post(context, SIGN_UP, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LoginData loginData = new Gson().fromJson(responseString, LoginData.class);
                listener.onSuccess(loginData);
            }
        });
    }
}


