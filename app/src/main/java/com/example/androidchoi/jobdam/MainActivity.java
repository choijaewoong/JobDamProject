package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.User;
import com.example.androidchoi.jobdam.Setting.SettingActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG_MY_JOB = "text";
    private static final String TAG_CARD_BOX = "cardBox";
    private static final String TAG_ALL_JOB = "jobInfo";
    private static final String TAG_BOARD = "board";
    private static final String TAG_ALARM = "alarm";
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
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MyJobFragment(), TAG_MY_JOB).commit();
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
        mUserEmail = (TextView)navHeaderView.findViewById(R.id.text_user_email);
        mUserName = (TextView)navHeaderView.findViewById(R.id.text_user_name);
        mUserEmail.setText(User.getInstance().getUserId());
        mUserName.setText(User.getInstance().getUserName());
        ImageView setting = (ImageView)navHeaderView.findViewById(R.id.btn_setting);
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
            toggle();
            return false;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_my_job) {
            emptyBackStack();
//            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_MY_JOB);
//            if(old == null) {
//                emptyBackStack();
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyJobFragment(), TAG_MY_JOB).commit();
//            }
        } else if (id == R.id.nav_card_box) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_CARD_BOX);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CardBoxFragment(),TAG_CARD_BOX).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_all_job) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_ALL_JOB);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllJobFragment(), TAG_ALL_JOB).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_board) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_BOARD);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BoardFragment(), TAG_BOARD).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_announcement) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_ALARM);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new NoticeFragment(), TAG_ALARM).addToBackStack(null).commit();
            }
        }
        showContent();
        return true;
    }
    private void emptyBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
