package com.vaddya.schedule.android.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.fragments.TaskListFragment;

import java.util.ArrayList;
import java.util.List;

import static com.vaddya.schedule.android.model.TasksType.ACTIVE;
import static com.vaddya.schedule.android.model.TasksType.ALL;
import static com.vaddya.schedule.android.model.TasksType.COMPLETED;

/**
 * com.vaddya.schedule.android.activities at android
 *
 * @author vaddya
 */
public class TaskListActivity extends NavigationDrawerActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_list_toolbar);
        toolbar.setTitle(getString(R.string.task_list));
        setSupportActionBar(toolbar);

        setupDrawer(toolbar, R.id.nav_task_list);

        viewPager = (ViewPager) findViewById(R.id.task_list_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.task_list_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TaskListFragment.newInstance(ALL), getString(ALL.getId()));
        adapter.addFragment(TaskListFragment.newInstance(ACTIVE), getString(ACTIVE.getId()));
        adapter.addFragment(TaskListFragment.newInstance(COMPLETED), getString(COMPLETED.getId()));
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}