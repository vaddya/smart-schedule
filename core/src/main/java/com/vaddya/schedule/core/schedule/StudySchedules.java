package com.vaddya.schedule.core.schedule;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

import static com.vaddya.schedule.core.utils.WeekType.EVEN;
import static com.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * Класс для хранения расписаний
 *
 * @author vaddya
 * @see StudySchedule
 */
public class StudySchedules {

    private final Map<WeekType, StudySchedule> schedules;

    public StudySchedules(LessonRepository lessons) {
        schedules = new EnumMap<>(WeekType.class);
        schedules.put(ODD, new StudySchedule(ODD, lessons));
        schedules.put(EVEN, new StudySchedule(EVEN, lessons));
    }

    public StudySchedule get(WeekType weekType) {
        return schedules.get(weekType);
    }

    public void swapWeekTypes() {
        schedules.get(ODD).setWeekType(EVEN);
        schedules.get(EVEN).setWeekType(ODD);
        schedules.put(EVEN, schedules.put(ODD, schedules.get(EVEN)));
    }

    public Lesson findLesson(UUID id) {
        try {
            return schedules.get(ODD).findLesson(id);
        } catch (NoSuchLessonException e) {
            return schedules.get(EVEN).findLesson(id);
        }
    }

    public void updateLesson(Lesson lesson) {
        try {
            DayOfWeek day = schedules.get(ODD).findLessonDay(lesson.getId());
            schedules.get(ODD).updateLesson(day, lesson);
        } catch (NoSuchLessonException e) {
            DayOfWeek day = schedules.get(EVEN).findLessonDay(lesson.getId());
            schedules.get(EVEN).updateLesson(day, lesson);
        }
    }

    public void removeLesson(UUID id) {
        try {
            schedules.get(ODD).removeLesson(id);
        } catch (NoSuchLessonException e) {
            schedules.get(EVEN).removeLesson(id);
        }
    }
}