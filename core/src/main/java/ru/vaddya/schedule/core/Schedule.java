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

    public String test;

    public Schedule() {
        this.today = new Date();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Scanner scan = new Scanner(
                    new File(classLoader.getResource("schedule.json").getFile()));
            scan.useDelimiter("\\Z");
            test = scan.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Date getToday() {
        return today;
    }
}
