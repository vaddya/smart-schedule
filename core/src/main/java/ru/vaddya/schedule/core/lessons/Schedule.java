package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
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
            days.put(day, db.getLessons(day));
        }
    }

    public WeekType getWeekType() {
        return weekType;
    }

    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    public void addLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).add(lesson);
    }

    public List<Lesson> getLessons(DayOfWeek day) {
        return days.get(day);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DayOfWeek day : DayOfWeek.values()) {
            sb.append(day)
                    .append(":\n");
            days.get(day).forEach(l -> sb.append(l).append("\n"));
        }
        return sb.toString();
    }
}
