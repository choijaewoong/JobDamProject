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
import com.example.androidchoi.jobdam.Model.NetworkMessage;
import com.example.androidchoi.jobdam.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String id = PropertyManager.getInstance().getId();
        if (!TextUtils.isEmpty(id)) {
            String password = PropertyManager.getInstance().getPassword();
            NetworkManager.getInstance().login(getApplicationContext(), id, password, new NetworkManager.OnResultListener<NetworkMessage>() {
                @Override
                public void onSuccess(NetworkMessage result) {
                    if (result.getMessage().equals(LoginFragment.MESSAGE_LOGIN_SUCCESS)) {
                        goMain();
                    } else {
                        goLogin();
                    }
                }

                @Override
                public void onFail(int code) {

                }
            });
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goLogin();
                }
            }, 1000);
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
