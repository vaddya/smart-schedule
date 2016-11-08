package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.StudyDay;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.tasks.StudyTasks;

import java.time.DayOfWeek;

/**
 * Интерфейс приложения Smart SmartSchedule
 *
 * @author vaddya
 */
public interface Schedule {

    /**
     * Получить учебный день по дню недели
     *
     * @param day день недели
     * @return учебный день
     */
    StudyDay getDay(DayOfWeek day);

    /**
     * Получить учебную неделю, содержашаю учебные дни
     *
     * @return учебную неделю
     */
    StudyWeek getWeek();

    /**
     * Получить учебные задания
     *
     * @return учебные задания
     */
    StudyTasks getTasks();

}
