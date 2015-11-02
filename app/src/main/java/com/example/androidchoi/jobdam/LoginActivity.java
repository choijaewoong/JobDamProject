package com.example.androidchoi.jobdam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.login_container, new LoginFragment()).commit();
        }
    }
    public void pushSingUpFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new ServiceAgreementFragment()).addToBackStack(null).commit();
    }
}
