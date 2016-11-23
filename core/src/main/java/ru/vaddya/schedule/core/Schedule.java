package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.StudyDay;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.tasks.StudyTasks;

import java.time.DayOfWeek;

/**
 * Интерфейс приложения Smart Schedule
 *
 * @author vaddya
 */
public interface Schedule {

    /**
     * Получить учебный день на текущей неделе
     *
     * @param day день недели
     * @return учебный день
     */
    StudyDay getDay(DayOfWeek day);

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
