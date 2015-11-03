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

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_MY_JOB = "myJob";
    private static final String TAG_CARD_BOX = "cardBox";
    private static final String TAG_ALL_JOB = "jobInfo";
    private static final String TAG_BOARD = "board";
    private static final String TAG_ALARM = "alarm";
    private static final String TAG_SETTING = "setting";
    SlidingMenu mSlidingMenu;

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
        View customView = getLayoutInflater().inflate(R.layout.view_toolbar_custom, null);
        getSupportActionBar().setCustomView(customView, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
//        setSlidingActionBarEnabled(false);

        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new MenuFragment()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MyJobFragment(),TAG_MY_JOB).commit();
            }
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setBehindWidthRes(R.dimen.menu_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setFadeDegree(0.0f); //블러처리 해제
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView setting = (ImageView) findViewById(R.id.btn_setting);
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
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_camara) {
            emptyBackStack();
//            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_MY_JOB);
//            if(old == null) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyJobFragment(), TAG_MY_JOB).commit();
//            }
        } else if (id == R.id.nav_gallery) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_CARD_BOX);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CardBoxFragment(),TAG_CARD_BOX).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_slideshow) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_ALL_JOB);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllJobFragment(), TAG_ALL_JOB).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_manage) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_BOARD);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BoardFragment(), TAG_BOARD).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_share) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_ALARM);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AnnouncementFragment(), TAG_ALARM).addToBackStack(null).commit();
            }
        }
        showContent();
        return true;
    }
    private void emptyBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
