package com.example.androidchoi.jobdam;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
//        setSlidingActionBarEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        } else if (id == R.id.nav_send) {
            Fragment old = getSupportFragmentManager().findFragmentById(R.id.nav_send);
            if (old == null) {
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingFragment()).addToBackStack(null).commit();
            }
        }
        showContent();//
        return true;
    }
    private void emptyBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
