package ru.vaddya.schedule.core;

import java.util.*;

/**
 * Created by Vadim on 10/5/2016.
 */
public class StudyWeek {

    private EnumMap<DaysOfWeek, StudyDay> days = new EnumMap<>(DaysOfWeek.class);

    public void setDay(DaysOfWeek day, StudyDay studyDay) {
        days.remove(day);
        days.put(day, studyDay);
    }

    public StudyDay getDay(DaysOfWeek day) {
        return days.get(day);
    }
}
