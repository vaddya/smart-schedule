package com.vaddya.schedule.android.rest;

import com.vaddya.schedule.android.model.Day;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * com.vaddya.schedule.android.rest at android
 *
 * @author vaddya
 */
public interface ScheduleService {

    @GET("schedule/{date}")
    Call<Day> getDay(@Path("date") String date);

}
