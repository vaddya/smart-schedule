package com.vaddya.schedule.android.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.Utils;
import com.vaddya.schedule.android.fragments.DayFragment;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ScheduleActivity extends NavigationDrawerActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("E, dd MMM");

    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private LocalDate monday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolbar);

        setupDrawer(toolbar, R.id.nav_schedule);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.schedule_collapsing_toolbar_layout);

        LocalDate today = LocalDate.now();
        int index = today.getDayOfWeek() - 1;
        monday = today.minusDays(index);
        updateTitle(today);

        initViewPager(index);
    }

    private void initViewPager(int initIndex) {
        viewPager = (ViewPager) findViewById(R.id.content_schedule_viewpager);
        viewPager.setCurrentItem(initIndex);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                LocalDate date = monday.plusDays(position);
                return DayFragment.newInstance(date);
            }

            @Override
            public int getCount() {
                return 7;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTitle(monday.plusDays(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateTitle(LocalDate date) {
        String title = FORMATTER.print(date);
        collapsingToolbarLayout.setTitle(Utils.capitalize(title));
    }

}