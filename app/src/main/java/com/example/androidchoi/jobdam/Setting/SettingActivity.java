package com.example.androidchoi.jobdam.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.R;

public class SettingActivity extends AppCompatActivity {
    public final static String[] SETTINGMENU
            = {"Logout", "Help Desk", "Privacy", "Legal", "Alarm", "Version" };

    ListView mListView;
    ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mListView = (ListView)findViewById(R.id.listview_setting);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
//                Toast.makeText(SettingActivity.this, mAdapter.getItem(position) + "/" + mAdapter.getItemId(position), Toast.LENGTH_SHORT).show();
                switch ((int) mAdapter.getItemId(position)) {
                    case 0:
                        Toast.makeText(SettingActivity.this, "로그아웃!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(SettingActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        intent = new Intent(SettingActivity.this, HelpDeskActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(SettingActivity.this, PrivacyActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(SettingActivity.this, LegalActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(SettingActivity.this, AlarmSettingActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(SettingActivity.this, VersionInfoActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);
        initSettingMenu();
    }
    private void initSettingMenu() {
        mAdapter.addAll(SETTINGMENU);
    }
}
