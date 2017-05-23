package com.vaddya.schedule.android.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.fragments.CreateTaskDialog;
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
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_list_toolbar);
        toolbar.setTitle(getString(R.string.task_list));
        setSupportActionBar(toolbar);

        setupDrawer(toolbar, R.id.nav_task_list);


        viewPager = (ViewPager) findViewById(R.id.content_task_list_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.task_list_tabs);
        tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.task_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                CreateTaskDialog newFragment = new CreateTaskDialog();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(R.id.drawer_layout, newFragment).addToBackStack(null).commit();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TaskListFragment.newInstance(ALL), getString(ALL.getId()));
        adapter.addFragment(TaskListFragment.newInstance(ACTIVE), getString(ACTIVE.getId()));
        adapter.addFragment(TaskListFragment.newInstance(COMPLETED), getString(COMPLETED.getId()));
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

}