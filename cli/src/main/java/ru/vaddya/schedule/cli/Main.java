package ru.vaddya.schedule.cli;

import ru.vaddya.schedule.core.Schedule;

/**
 * Created by Vadim on 9/22/2016.
 */
public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        System.out.println("Today is " + schedule.getToday());
    }
}
