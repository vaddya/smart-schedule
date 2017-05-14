package com.vaddya.schedule.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.model.Storage;
import com.vaddya.schedule.android.model.Task;
import com.vaddya.schedule.android.model.TasksType;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * com.vaddya.schedule.android.fragments at android
 *
 * @author vaddya
 */
public class TaskListFragment extends Fragment {

    public static final String ARG_TASKS_TYPE = "ARG_TASKS_TYPE";

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> tasks;

    public static TaskListFragment newInstance(TasksType tasksType) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASKS_TYPE, tasksType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_task_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });

        TasksType type = (TasksType) getArguments().getSerializable(ARG_TASKS_TYPE);
        tasks = Storage.getTasks(type);
        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView subject;
        private EditText text;
        private TextView deadline;
        private CheckBox isComplete;

        public TaskHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.fragment_task_subject);
            text = (EditText) itemView.findViewById(R.id.fragment_task_text);
            deadline = (TextView) itemView.findViewById(R.id.fragment_task_deadline);
            isComplete = (CheckBox) itemView.findViewById(R.id.fragment_task_is_complete);
        }

        public void bindTask(Task task) {
            subject.setText(task.getSubject() + " | " + task.getType());
            text.setText(task.getTextTask());
            text.setSelection(text.getText().length());
            deadline.setText(task.getDeadline().toString("dd MMMM yyyy", Locale.getDefault()));
            isComplete.setChecked(task.isComplete());
        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_task, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = tasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

    }

}