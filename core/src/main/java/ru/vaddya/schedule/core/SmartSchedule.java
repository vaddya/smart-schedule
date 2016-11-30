package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.schedule.StudySchedule;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.utils.WeekTime;
import ru.vaddya.schedule.core.utils.WeekType;

/**
 * Интерфейс приложения Smart StudySchedule
 *
 * @author vaddya
 */
public interface SmartSchedule {

    /**
     * Получить расписание на текующую неделю
     */
    StudySchedule getCurrentSchedule();

    /**
     * Получить расписание на заданную неделю
     */
    StudySchedule getSchedule(WeekType weekType);

    /**
     * Поменять местами порядок расписаний (четная - нечетная)
     */
    void swapSchedules();

    /**
     * Получить текущую учебную неделю, содержашаю учебные дни
     */
    StudyWeek getCurrentWeek();

    /**
     * Получить заданную учебную неделю, содержашаю учебные дни
     */
    StudyWeek getWeek(WeekTime weekTime);

    /**
     * Получить учебные задания
     */
    StudyTasks getTasks();

}
