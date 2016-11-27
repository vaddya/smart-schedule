package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для представления расписания
 *
 * @author vaddya
 */
public class Schedule {

    private static final Database db = Database.getConnection();

    private WeekType weekType;

    private final Map<DayOfWeek, List<Lesson>> days = new EnumMap<>(DayOfWeek.class);

    public Schedule(WeekType weekType) {
        this.weekType = weekType;
        for (DayOfWeek day : DayOfWeek.values()) {
            days.put(day, db.getLessons(weekType, day));
        }
    }

    public WeekType getWeekType() {
        return weekType;
    }

    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    public Lesson getLesson() {
        // TODO: 11/27/2016
        return null;
    }

    public void addLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).add(lesson);
        db.addLesson(weekType, day, lesson);
    }

    public void updateLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).add(lesson);
        db.updateLesson(weekType, day, lesson);
    }

    public void removeLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).add(lesson);
        db.removeLesson(weekType, day, lesson);
    }

    public List<Lesson> getLessons(DayOfWeek day) {
        List<Lesson> clone = new ArrayList<>();
        days.get(day).forEach(lesson -> clone.add(new Lesson(lesson)));
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DayOfWeek day : DayOfWeek.values()) {
            sb.append(day)
                    .append(":\n");
            days.get(day).forEach(lesson -> sb.append(lesson).append("\n"));
        }
        return sb.toString();
    }
}
