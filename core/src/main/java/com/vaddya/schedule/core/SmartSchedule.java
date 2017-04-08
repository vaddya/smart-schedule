package com.vaddya.schedule.core;

import com.vaddya.schedule.core.changes.StudyChanges;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.core.schedule.ScheduleDay;
import com.vaddya.schedule.core.schedule.ScheduleWeek;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.utils.LocalWeek;
import com.vaddya.schedule.core.utils.TypeOfWeek;

import java.time.LocalDate;

/**
 * Интерфейс приложения Smart Schedule
 *
 * @author vaddya
 */
public interface SmartSchedule {

    /**
     * Получить учебные задания
     */
    StudyTasks getTasks();

    /**
     * Получить занятия
     */
    StudyLessons getLessons();

    /**
     * Поменять местами порядок расписаний (четная - нечетная)
     */
    void swapTypesOfWeeks();

    /**
     * Получить текущую учебную неделю, содержащую учебные дни
     */
    ScheduleWeek getCurrentWeek();

    /**
     * Получить заданную учебную неделю, содержащую учебные дни
     */
    ScheduleWeek getWeek(LocalWeek week);

    /**
     * Получить учебный день по дате
     */
    ScheduleDay getDay(LocalDate date);

    /**
     * Получить изменения занятий
     */
    StudyChanges getChanges();

    /**
     * Получить текущий тип недели
     */
    TypeOfWeek getCurrentTypeOfWeek();

    /**
     * Получить тип недели
     */
    TypeOfWeek getTypeOfWeek(LocalWeek week);

}