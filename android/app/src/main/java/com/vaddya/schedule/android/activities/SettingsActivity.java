package com.vaddya.schedule.android.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.fragments.SettingsFragment;

/**
 * com.vaddya.schedule.android.activities at android
 *
 * @author vaddya
 */
// TODO: 5/24/2017 add appcompatprefactivity
public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            Fragment preferenceFragment = new SettingsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.pref_container, preferenceFragment);
            ft.commit();
        }
    }
}