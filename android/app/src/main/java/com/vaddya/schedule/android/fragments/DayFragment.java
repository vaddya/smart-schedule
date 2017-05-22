package com.vaddya.schedule.android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vaddya.schedule.android.R;
import com.vaddya.schedule.android.model.Day;
import com.vaddya.schedule.android.model.Lesson;
import com.vaddya.schedule.android.model.Storage;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayFragment extends Fragment {

    private static final String ARG_DATE = "ARG_DATE";

    private LocalDate date;

    private RecyclerView recyclerView;
    private LessonAdapter adapter;

    public static DayFragment newInstance(LocalDate date) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        date = (LocalDate) getArguments().getSerializable(ARG_DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_day_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return view;
    }

    private void updateUi() {
        if (adapter == null) {
            adapter = new LessonAdapter(new ArrayList<Lesson>());
            recyclerView.setAdapter(adapter);
        }

        Storage.callLessons(date, new Callback<Day>() {
            @Override
            public void onResponse(@NonNull Call<Day> call, @NonNull Response<Day> response) {
                adapter.setLessons(response.body().getLessons());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<Day> call, @NonNull Throwable t) {
                Log.e(DayFragment.class.getSimpleName(), t.getMessage());
            }
        });
    }

    private class LessonHolder extends RecyclerView.ViewHolder {

        private TextView startTime;
        private TextView endTime;
        private TextView subject;
        private TextView type;
        private TextView place;
        private TextView teacher;

        public LessonHolder(View itemView) {
            super(itemView);

            startTime = (TextView) itemView.findViewById(R.id.fragment_lesson_start_time);
            endTime = (TextView) itemView.findViewById(R.id.fragment_lesson_end_time);
            subject = (TextView) itemView.findViewById(R.id.fragment_lesson_subject);
            type = (TextView) itemView.findViewById(R.id.fragment_lesson_type);
            place = (TextView) itemView.findViewById(R.id.fragment_lesson_place);
            teacher = (TextView) itemView.findViewById(R.id.fragment_lesson_teacher);
        }

        public void bindLesson(Lesson lesson) {
            startTime.setText(lesson.getStartTime());
            endTime.setText(lesson.getEndTime());
            subject.setText(lesson.getSubject());
            type.setText(lesson.getType().toString());
            place.setText(lesson.getPlace());
            teacher.setText(lesson.getTeacher());
        }

    }

    private class LessonAdapter extends RecyclerView.Adapter<LessonHolder> {

        private List<Lesson> lessons;

        public LessonAdapter(List<Lesson> lessons) {
            this.lessons = lessons;
        }

        public void setLessons(List<Lesson> lessons) {
            this.lessons = lessons;
        }

        @Override
        public LessonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_lesson, parent, false);
            return new LessonHolder(view);
        }

        @Override
        public void onBindViewHolder(LessonHolder holder, int position) {
            Lesson lesson = lessons.get(position);
            holder.bindLesson(lesson);
        }

        @Override
        public int getItemCount() {
            return lessons.size();
        }
    }

}