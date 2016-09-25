package ru.vaddya.schedule.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

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
