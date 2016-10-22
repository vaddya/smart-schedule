package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Vadim on 10/5/2016.
 */
public class StudyWeek {

    private EnumMap<DaysOfWeek, StudyDay> days = new EnumMap<>(DaysOfWeek.class);

    public StudyWeek() {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            days.put(day, new StudyDay());
        }
    }

    public void set(DaysOfWeek day, StudyDay studyDay) {
        days.remove(day);
        days.put(day, studyDay);
    }

    public StudyDay get(DaysOfWeek day) {
        return days.get(day);
    }

    public List<StudyDay> getAll() {
        return days.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<DaysOfWeek, StudyDay> entry : days.entrySet()) {
            if (entry.getValue().isEmpty()) {
                builder
                        .append(entry.getKey().getRu())
                        .append(":\n")
                        .append(entry.getValue());
            }
        }
        return builder.toString();
    }
}
