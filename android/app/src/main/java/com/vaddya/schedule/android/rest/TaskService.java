package com.vaddya.schedule.android.rest;

import com.vaddya.schedule.android.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * com.vaddya.schedule.android.rest at android
 *
 * @author vaddya
 */
public interface TaskService {

    @GET("tasks")
    Call<List<Task>> getTasks();

}