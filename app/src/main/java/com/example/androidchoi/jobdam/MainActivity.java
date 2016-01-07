package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Model.User;
import com.example.androidchoi.jobdam.Setting.SettingActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG_MY_JOB = "text";
    public static final String TAG_CARD_BOX = "cardBox";
    public static final String TAG_ALL_JOB = "jobInfo";
    public static final String TAG_BOARD = "board";
    public static final String TAG_ALARM = "alarm";
    SlidingMenu mSlidingMenu;
    TextView mUserName;
    TextView mUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.slidingmenu_main); //Setting SlidingMenu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //타이틀 제거

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View customToolbar = getLayoutInflater().inflate(R.layout.toolbar_main, null);

        getSupportActionBar().setCustomView(customToolbar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
//        setSlidingActionBarEnabled(false);

        if (savedInstanceState == null) {
//          getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new MenuFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyJobFragment(), TAG_MY_JOB).commit();
        }
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setBehindWidthRes(R.dimen.menu_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow_nav_menu);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_menu_width);
        mSlidingMenu.setFadeDegree(0.3f); //블러처리 해제
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        View navHeaderView = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.addHeaderView(navHeaderView);
        mUserEmail = (TextView) navHeaderView.findViewById(R.id.text_user_email);
        mUserName = (TextView) navHeaderView.findViewById(R.id.text_user_name);
        mUserEmail.setText(User.getInstance().getUserId());
        mUserName.setText(User.getInstance().getUserName());
        ImageView setting = (ImageView) navHeaderView.findViewById(R.id.btn_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
//                showContent(); //설정창에서 빠져 나온 뒤 네비게이션 메뉴가 닫혀있도록.
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (getSupportFragmentManager().findFragmentByTag(TAG_MY_JOB) != null
                    && myJobListCallBack.onCheckMode()) {
                return false;
            } else if(getSupportFragmentManager().findFragmentByTag(TAG_CARD_BOX) != null
                    && cardBoxCallBack.onCheckMode()){
                return false;
            }else {
                toggle();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_my_job) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_MY_JOB);
            if (old == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyJobFragment(), TAG_MY_JOB).commit();
            }
        } else if (id == R.id.nav_card_box) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_CARD_BOX);
            if (old == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CardBoxFragment(), TAG_CARD_BOX).commit();
            }
        } else if (id == R.id.nav_all_job) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_ALL_JOB);
            if (old == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllJobFragment(), TAG_ALL_JOB).commit();
            }
        } else if (id == R.id.nav_board) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_BOARD);
            if (old == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BoardFragment(), TAG_BOARD).commit();
            }
        }
        showContent();
        return true;
    }

    public static final int MESSAGE_BACK_TIMEOUT = 3;
    public static final int TIME_BACK_TIMEOUT = 2000;
    boolean isBackPressed = false;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_BACK_TIMEOUT:
                    isBackPressed = false;
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_CARD_BOX) != null
                && cardBoxCallBack.onCheckMode()) {
            cardBoxCallBack.onChangeMode();
        } else if (getSupportFragmentManager().findFragmentByTag(TAG_MY_JOB) != null
                && myJobListCallBack.onCheckMode()) {
            myJobListCallBack.onChangeMode();
        } else if (isBackPressed) {
            mHandler.removeMessages(MESSAGE_BACK_TIMEOUT);
            super.onBackPressed();
        } else {
            isBackPressed = true;
            Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(MESSAGE_BACK_TIMEOUT, TIME_BACK_TIMEOUT);
        }
    }

    public interface OnCardBoxCallBack {
        boolean onCheckMode();

        void onChangeMode();
    }

    OnCardBoxCallBack cardBoxCallBack;

    public void setOnCardBoxCallback(OnCardBoxCallBack callback) {
        cardBoxCallBack = callback;
    }

    public interface OnMyJobListCallBack {
        boolean onCheckMode();

        void onChangeMode();
    }

    OnMyJobListCallBack myJobListCallBack;

    public void setOnMyJobListCallback(OnMyJobListCallBack callback) {
        myJobListCallBack = callback;
    }
}
