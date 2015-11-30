package com.example.androidchoi.jobdam.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Manager.PropertyManager;
import com.example.androidchoi.jobdam.Model.LoginData;
import com.example.androidchoi.jobdam.Model.User;
import com.example.androidchoi.jobdam.R;

public class SplashActivity extends AppCompatActivity {

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String id = PropertyManager.getInstance().getId();
        if (!TextUtils.isEmpty(id)) {
            String password = PropertyManager.getInstance().getPassword();
            NetworkManager.getInstance().login(getApplicationContext(), id, password, new NetworkManager.OnResultListener<LoginData>() {
                @Override
                public void onSuccess(LoginData result) {
                    if (result.getMessage().equals(LoginFragment.MESSAGE_SUCCESS)) {
                        User.getInstance().setUser(result.getUserId(), result.getName());
                        goMain();
                    } else {
                        goLogin();
                    }
                }
                @Override
                public void onFail(int code) {
                    goLogin();
                }
            });
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goLogin();
                }
            }, 1500);

        }
    }
    Handler mHandler = new Handler(Looper.getMainLooper());
    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void goLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
