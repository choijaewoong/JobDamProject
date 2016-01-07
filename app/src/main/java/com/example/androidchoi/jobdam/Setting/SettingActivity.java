package com.example.androidchoi.jobdam.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.androidchoi.jobdam.Model.User;
import com.example.androidchoi.jobdam.R;

public class SettingActivity extends AppCompatActivity {

    TextView mTextUserEmail;
    ToggleButton mAutoLogin;
//    ImageView mImageAlarmSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        mTextUserEmail = (TextView)findViewById(R.id.text_setting_user_email);
        mTextUserEmail.setText(User.getInstance().getUserId());
        mAutoLogin = (ToggleButton)findViewById(R.id.toggleButton_auto_login);
//        mImageAlarmSetting = (ImageView)findViewById(R.id.image_setting_alarm_button);
//        mImageAlarmSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, AlarmSettingActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
