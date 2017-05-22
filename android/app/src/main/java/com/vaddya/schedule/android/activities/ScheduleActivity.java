package com.vaddya.schedule.android.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.Utils;
import com.vaddya.schedule.android.fragments.DayFragment;
import com.vaddya.schedule.android.fragments.TaskListFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

public class ScheduleActivity extends NavigationDrawerActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("E, d MMM");
    private static final DateTimeFormatter SHORT_FORMATTER = DateTimeFormat.forPattern("d MMM");

    private static final String DATE_PICKER = "DATE_PICKER";

    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private Button calendarButton;

    private LocalDate monday;
    private LocalDate currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolbar);

        setupDrawer(toolbar, R.id.nav_schedule);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.schedule_collapsing_toolbar_layout);

        currentDate = LocalDate.now();
        int index = currentDate.getDayOfWeek() - 1;
        monday = currentDate.minusDays(index);
        updateDates(currentDate);

        initButtons();
        initViewPager(index);

        updateCurrentWeek();
    }

    private void initButtons() {
        previousButton = (ImageButton) findViewById(R.id.schedule_button_previous);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monday = monday.minusDays(7);
                currentDate = currentDate.minusDays(7);
                onDateUpdate();
            }
        });
        nextButton = (ImageButton) findViewById(R.id.schedule_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monday = monday.plusDays(7);
                currentDate = currentDate.plusDays(7);
                onDateUpdate();
            }
        });
        calendarButton = (Button) findViewById(R.id.schedule_button_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ScheduleActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), DATE_PICKER);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.schedule_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleActivity.this, "Add lesson", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        currentDate = new LocalDate(year, monthOfYear, dayOfMonth);
        int index = currentDate.getDayOfWeek() - 1;
        monday = currentDate.minusDays(index);
        onDateUpdate();
    }

    private void initViewPager(final int initIndex) {
        viewPager = (ViewPager) findViewById(R.id.content_schedule_viewpager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                currentDate = monday.plusDays(position);
                return DayFragment.newInstance(currentDate);
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
                updateDates(monday.plusDays(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(initIndex);
    }

    private void onDateUpdate() {
        updateDates(currentDate);
        updateCurrentWeek();
        viewPager.getAdapter().notifyDataSetChanged();

    }

    private void updateDates(LocalDate date) {
        String title = FORMATTER.print(date);
        collapsingToolbarLayout.setTitle(Utils.capitalize(title));
    }

    private void updateCurrentWeek() {
        String week = SHORT_FORMATTER.print(monday) + " - " + SHORT_FORMATTER.print(monday.plusDays(7));
        calendarButton.setText(week);
    }
}