package ru.vaddya.schedule.core;

import java.util.EnumMap;
import java.util.Map;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<DaysOfWeek, StudyDay> entry : days.entrySet()) {
            if (!entry.getValue().getLessons().isEmpty()) {
                builder
                    .append(entry.getKey().getRu())
                    .append(":\n")
                    .append(entry.getValue());
            }
        }
        return builder.toString();
    }
}
