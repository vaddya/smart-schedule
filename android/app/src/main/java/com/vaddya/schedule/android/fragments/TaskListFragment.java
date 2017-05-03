package com.vaddya.schedule.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vaddya.schedule.android.model.Task;

import java.util.List;

/**
 * com.vaddya.schedule.android.fragments at android
 *
 * @author vaddya
 */
public class TaskListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> tasks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        public TaskHolder(View itemView) {
            super(itemView);
        }

        public void bindTask(Task task) {

        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

    }

}