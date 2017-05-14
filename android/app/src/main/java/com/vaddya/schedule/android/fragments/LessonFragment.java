package com.vaddya.schedule.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vaddya.schedule.android.R;

import java.util.UUID;

public class LessonFragment extends Fragment {

    private static final String ARG_UUID = "ARG_UUID";

    private UUID uuid;

    public static LessonFragment newInstance(UUID uuid) {
        LessonFragment fragment = new LessonFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_UUID, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uuid = (UUID) getArguments().getSerializable(ARG_UUID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson, container, false);
    }

}
