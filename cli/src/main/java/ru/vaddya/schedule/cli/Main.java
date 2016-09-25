package ru.vaddya.schedule.cli;

import ru.vaddya.schedule.core.Schedule;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Vadim on 9/22/2016.
 */
public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.Y", new Locale("ru"));
        System.out.println("Сегодня " + dateFormat.format(schedule.getToday()));
    }
}
