package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.Schedule;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.tasks.StudyTasks;

/**
 * Интерфейс приложения Smart Schedule
 *
 * @author vaddya
 */
public interface SmartSchedule {

    /**
     * Получить расписание на текующую неделю
     *
     * @return расписание на текущую неделю
     */
    Schedule getCurrentSchedule();

    /**
     * Получить расписание на нечетную неделю
     *
     * @return раписание на нечетную неделю
     */
    Schedule getOddSchedule();

    /**
     * Получить расписание на четную неделю
     *
     * @return раписание на четную неделю
     */
    Schedule getEvenSchedule();

    /**
     * Поменять местами порядок расписаний (четная - нечетная)
     */
    void swapSchedules();

    /**
     * Получить текущую учебную неделю, содержашаю учебные дни
     *
     * @return текущая учебная неделя
     */
    StudyWeek getCurrentWeek();

    /**
     * Получить учебные задания
     *
     * @return учебные задания
     */
    StudyTasks getTasks();

}
