package com.example.androidchoi.jobdam.Manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dongja94 on 2015-10-21.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEditor = mPrefs.edit();
    }

    public static final String KEY_ID = "id";
    public static final String KEY_PASSWORD = "password";

    public void setId(String id) {
        mEditor.putString(KEY_ID, id);
        mEditor.commit();
    }

    public String getId() {
        return mPrefs.getString(KEY_ID,"");
    }

    public void setPassword(String password) {
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPrefs.getString(KEY_PASSWORD, "");
    }

    public boolean isBackupSync() {
        return mPrefs.getBoolean("perf_sync", false);
    }


}
