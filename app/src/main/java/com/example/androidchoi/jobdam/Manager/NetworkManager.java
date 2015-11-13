package com.example.androidchoi.jobdam.Manager;

import android.content.Context;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;


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

    ThreadPoolExecutor mExecutor;
    public static final int CORE_POOL_SIZE = 5;
    public static final int MAXIMUN_POOL_SIZE = 64;
    public static final int KEEP_ALIVE_TIME = 5000;
    LinkedBlockingQueue<Runnable> mRequestQueue = new LinkedBlockingQueue<Runnable>();
    Map<Context, List<NetworkRequest>> mRequestMap = new HashMap<Context, List<NetworkRequest>>();

    private NetworkManager() {

    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);

        public void onFail(int code);
    }

    Handler mHadler = new Handler(Looper.getMainLooper());

    public void login(String userid, String password, final OnResultListener<String> listener) {
        mHadler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess("ok");
            }
        }, 1000);
    }

    public void signup(String userid, String password, final OnResultListener<String> listener) {
        mHadler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess("ok");
            }
        }, 1000);
    }
}
