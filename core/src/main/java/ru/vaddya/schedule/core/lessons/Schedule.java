package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * ru.vaddya.schedule.core.lessons at smart-schedule
 *
 * @author vaddya
 * @since November 25, 2016
 */
public class Schedule {

    private static final Database db = Database.getConnection();

    private WeekType weekType;

    private Map<DayOfWeek, List<Lesson>> days = new EnumMap<>(DayOfWeek.class);

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

    public List<Lesson> getLessons(DayOfWeek day) {
        return days.get(day);
    }

}
