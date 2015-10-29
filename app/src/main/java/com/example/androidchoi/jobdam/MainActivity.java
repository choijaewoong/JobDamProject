package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_MY_JOB = "myJob";
    private static final String TAG_CARD_BOX = "cardBox";
    private static final String TAG_JOB_INFO = "jobInfo";
    private static final String TAG_BOARD_ = "board";
    private static final String TAG_ALARM  = "alarm";
    private static final String TAG_SETTING  = "setting";

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


        if (savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction().add(R.id.menu_container, new MenuFragment()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MyJobFragment()).commit();
        }
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setBehindWidthRes(R.dimen.menu_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setFadeDegree(0.0f);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyJobFragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Fragment old = getSupportFragmentManager().findFragmentById(R.id.nav_gallery);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CardBoxFragment()).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_slideshow) {
            Fragment old = getSupportFragmentManager().findFragmentById(R.id.nav_slideshow);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new JobInfoFragment()).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_manage) {
            Fragment old = getSupportFragmentManager().findFragmentById(R.id.nav_manage);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BoardFragment()).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_share) {
            Fragment old = getSupportFragmentManager().findFragmentById(R.id.nav_share);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AlarmFragment()).addToBackStack(null).commit();
            }
        }
//        else if (id == R.id.nav_send) {
//            Fragment old = getSupportFragmentManager().findFragmentById(R.id.nav_send);
//            if (old == null) {
//                emptyBackStack();
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingFragment()).addToBackStack(null).commit();
//            }
//        }
        showContent();
        return true;
    }
    private void emptyBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
