package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.Schedule;
import ru.vaddya.schedule.core.lessons.StudyDay;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.tasks.StudyTasks;

import java.time.DayOfWeek;

/**
 * Интерфейс приложения Smart Schedule
 *
 * @author vaddya
 */
public interface SmartSchedule {


    Schedule getOddSchedule();

    Schedule getEvenSchedule();

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
