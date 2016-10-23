package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.io.Database;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для представления учебной недели (списка учебных дней)
 *
 * @author vaddya
 * @see StudyDay
 */
public class StudyWeek {

    // TODO: 10/23/2016 подумать над архитектурой
    private Database db = Schedule.db();

    private EnumMap<DaysOfWeek, StudyDay> days = new EnumMap<>(DaysOfWeek.class);

    public StudyWeek() {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            days.put(day, new StudyDay(db.getLessons(day)));
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
                        .append(entry.getKey().ru())
                        .append(":\n")
                        .append(entry.getValue());
            }
        }
        return builder.toString();
    }
}
