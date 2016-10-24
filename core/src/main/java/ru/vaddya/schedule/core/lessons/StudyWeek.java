package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.Schedule;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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

    public Map<DaysOfWeek, List<Lesson>> getAll() {
        // TODO: 10/24/2016 Хочется Stream API
        Map<DaysOfWeek, List<Lesson>> map = new EnumMap<>(DaysOfWeek.class);
        for (Map.Entry<DaysOfWeek, StudyDay> day : days.entrySet()) {
            map.put(day.getKey(), day.getValue().getAll());
        }
        return map;
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
