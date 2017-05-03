package com.vaddya.schedule.android.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.fragments.TaskFragment;
import com.vaddya.schedule.android.model.Task;

import java.util.List;

/**
 * com.vaddya.schedule.android.activities at android
 *
 * @author vaddya
 */
public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Fragment fragment = new TaskFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
    }



}