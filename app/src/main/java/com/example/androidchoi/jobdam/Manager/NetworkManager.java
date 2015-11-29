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
import com.google.gson.Gson;
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
        params.put(KEYWORD, keyword);
        params.put(JOB_ORDER, job_ordering);
        params.put(JOB_REGION, job_region);
        params.put(JOB_KIND, job_kind);
        params.put(JOB_TYPE, job_type);
        params.put(START, start);
        params.put(COUNT, count);
        client.get(context, API_ADDRESS, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ByteArrayInputStream bais = new ByteArrayInputStream(responseBody);
                JobList jobList = parser.fromXml(bais, "jobs", JobList.class);
                Log.i("개수 :", jobList.getTotal() + " / " + jobList.getJobList().size());
                listener.onSuccess(jobList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFail(statusCode);
            }
        });
    }

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
                MyJobLab myJobLab = gson.fromJson(responseString, MyJobLab.class);
                listener.onSuccess(myJobLab);
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
                MyCardLab myCardLab = gson.fromJson(responseString, MyCardLab.class);
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

    //게시글 보기
    public static final String SHOW_ARTICLE = SERVER + "/boardlist";
    public static final String ARTICLE_PAGE = "page";
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
                ArticleLab articleLab = gson.fromJson(responseString, ArticleLab.class);
                listener.onSuccess(articleLab);
            }
        });
    }

    // 게시글 추가
    private static final String ADD_ARTICLE = SERVER + "/addboard";
    public void addArticle(Context context, final String jsonString, final OnResultListener<String> listener) {
        try {
            client.post(context, ADD_ARTICLE, new StringEntity(jsonString, "UTF-8"), "application/json", new TextHttpResponseHandler() {
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
                Articles articles = gson.fromJson(responseString, Articles.class);
                //ArticleLab.get(MyApplication.getContext(), gson.fromJson(responseString, ArticleLab.class));
                listener.onSuccess(articles);
            }
        });
    }

//    public void cancelAll(Context context) {
//        client.cancelRequests(context, true);
//    }

    public static final String LOG_IN = SERVER + "/login";
    public void login(Context context, String userid, String password, final OnResultListener<LoginData> listener) {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                OnResultListener<String> listener = null;
//                listener.onSuccess(null, "ok");
//            }
//        }, 1000);
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
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                OnResultListener<String> listener = null;
//                listener.onSuccess(null, "ok");
//            }
//        }, 1000);
    }


    //    ThreadPoolExecutor mExecutor;
//    public static final int CORE_POOL_SIZE = 5;
//    public static final int MAXIMUN_POOL_SIZE = 64;
//    public static final int KEEP_ALIVE_TIME = 5000;
//    LinkedBlockingQueue<Runnable> mRequestQueue = new LinkedBlockingQueue<Runnable>();
//    Map<Context, List<NetworkRequest>> mRequestMap = new HashMap<Context, List<NetworkRequest>>();
//
//    private NetworkManager() {
//        mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUN_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, mRequestQueue);
//    }
//
//    public interface OnResultListener<T> {
//        public void onSuccess(NetworkRequest<T> request, T result);
//        public void onFail(NetworkRequest<T> request, int code);
//    }
//
//    public static final int MESSAGE_SEND_SUCCESS = 1;
//    public static final int MESSAGE_SEND_FAIL = 2;
//
//    Handler mHandler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            NetworkRequest request = (NetworkRequest) msg.obj;
//            switch (msg.what) {
//                case MESSAGE_SEND_SUCCESS:
//                    request.sendSuccess();
//                    break;
//                case MESSAGE_SEND_FAIL:
//                    request.sendFail();
//                    break;
//            }
//            removeMap(request);
//        }
//    };
//
//    public void sendSuccess(NetworkRequest request) {
//        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SEND_SUCCESS, request));
//    }
//
//    public void sendFail(NetworkRequest request) {
//        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SEND_FAIL, request));
//    }
//
//    public <T> void getNetworkData(Context context, NetworkRequest<T> request, OnResultListener<T> listener) {
//        request.setOnResultListener(listener);
//        getNetworkData(context, request);
//    }
//
//    public void cancelAll(Context context) {
//        List<NetworkRequest> list = mRequestMap.get(context);
//        if (list != null) {
//            List<NetworkRequest> removelist = new ArrayList<NetworkRequest>(list);
//            for (NetworkRequest req : removelist) {
//                req.cancel();
//            }
//        }
//    }
//
//    private void addMap(NetworkRequest request) {
//        Context context = request.getContext();
//        List<NetworkRequest> list = mRequestMap.get(context);
//        if (list == null) {
//            list = new ArrayList<NetworkRequest>();
//            mRequestMap.put(context, list);
//        }
//        list.add(request);
//    }
//
//    private void removeMap(NetworkRequest request) {
//        Context context = request.getContext();
//        List<NetworkRequest> list = mRequestMap.get(context);
//        if (list != null) {
//            list.remove(request);
//            if (list.size() == 0) {
//                mRequestMap.remove(context);
//            }
//        }
//    }
//
//    public <T> void getNetworkData(Context context, NetworkRequest<T> request) {
//        request.setNetworkManager(this);
//        request.setContext(context);
//        addMap(request);
//        mExecutor.execute(request);
//    }
//
//    public void processCancel(NetworkRequest request) {
//        mRequestQueue.remove(request);
//        removeMap(request);
//    }
//
//
//
//
//

