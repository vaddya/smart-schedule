package com.vaddya.schedule.android.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.fragments.DayFragment;

import org.joda.time.LocalDate;

public class ScheduleActivity extends NavigationDrawerActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;

    private LocalDate currentMonday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.schedule_toolbar);
//        toolbar.setTitle(getString(R.string.schedule));
        setSupportActionBar(toolbar);

        setupDrawer(toolbar, R.id.nav_schedule);

        currentMonday = LocalDate.now();
        currentMonday = currentMonday.minusDays(currentMonday.getDayOfWeek() - 1);
        getSupportActionBar().setTitle(currentMonday.toString("dd MMM"));

        viewPager = (ViewPager) findViewById(R.id.content_schedule_viewpager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                LocalDate date = currentMonday.plusDays(position);
                return DayFragment.newInstance(date);
            }

            @Override
            public int getCount() {
                return 7;
            }
        });
    }

}