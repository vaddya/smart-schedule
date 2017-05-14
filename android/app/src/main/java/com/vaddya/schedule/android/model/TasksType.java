package com.vaddya.schedule.android.model;

import com.vaddya.schedule.android.R;

/**
 * com.vaddya.schedule.app.model at android
 *
 * @author vaddya
 */
public enum TasksType {

    ALL(R.string.all),
    ACTIVE(R.string.active),
    COMPLETED(R.string.completed);

    private int id;

    TasksType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}