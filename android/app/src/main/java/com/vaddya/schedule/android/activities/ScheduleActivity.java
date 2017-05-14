package com.vaddya.schedule.android.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;

import com.vaddya.schedule.android.R;

public class ScheduleActivity extends NavigationDrawerActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.schedule));
        setSupportActionBar(toolbar);

        setupDrawer(toolbar, R.id.nav_schedule);
    }

}