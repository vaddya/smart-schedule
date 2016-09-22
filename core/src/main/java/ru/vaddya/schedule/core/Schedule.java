package ru.vaddya.schedule.core;

import java.util.Date;

/**
 * Created by Vadim on 9/18/2016.
 */
public class Schedule {

    private Date today;

    public Schedule() {
        this.today = new Date();
    }

    public Date getToday() {
        return today;
    }
}
