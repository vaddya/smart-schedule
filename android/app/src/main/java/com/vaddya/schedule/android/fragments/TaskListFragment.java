package com.vaddya.schedule.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.model.Storage;
import com.vaddya.schedule.android.model.Task;
import com.vaddya.schedule.android.model.TasksType;
import com.vaddya.schedule.android.rest.LocalDateSerializer;
import com.vaddya.schedule.android.rest.TaskService;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * com.vaddya.schedule.android.fragments at android
 *
 * @author vaddya
 */
public class TaskListFragment extends Fragment {

    private static final String ARG_TASKS_TYPE = "ARG_TASKS_TYPE";
    private static final String TAG = TaskListFragment.class.getName();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd MMMM yyyy");

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

        TasksType tasksType = (TasksType) getArguments().getSerializable(ARG_TASKS_TYPE);

        adapter = new TaskAdapter(new ArrayList<Task>());

        Storage.callTasks(tasksType, new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                Log.i(TAG, "Response received: Code " + response.code());
                tasks = response.body();
                adapter.setTasks(tasks);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e(TAG, "Response failed: " + t.getMessage());
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private FloatingActionButton fab = ((FloatingActionButton) getActivity().findViewById(R.id.task_list_fab));

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });
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
            deadline.setText(DATE_FORMAT.print(task.getDeadline()));
            isComplete.setChecked(task.isComplete());
        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        public void setTasks(List<Task> tasks) {
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