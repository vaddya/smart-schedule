package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;

/**
 * Интерфейс приложения Smart Schedule
 *
 * @author vaddya
 */
public interface ScheduleAPI {

    /**
     * Добавить занятие в расписание
     *
     * @param day    день недели
     * @param lesson добавляемое занятие
     */
    void addLesson(DaysOfWeek day, Lesson lesson);

    /**
     * Обновить информацию о занятии
     *
     * @param day    день недели
     * @param lesson обновляемое занятие
     */
    void updateLesson(DaysOfWeek day, Lesson lesson);

    /**
     * Изменить день занятия
     *
     * @param from   исходный день недели
     * @param to     новый день недели
     * @param lesson обновляемое занятие
     */
    void changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson);

    /**
     * Удалить занятие из расписания
     *
     * @param day    день недели
     * @param lesson удаляемое занятие
     */
    void removeLesson(DaysOfWeek day, Lesson lesson);

    /**
     * Получить занятие по дню недели и ID занятия
     *
     * @param day день недели
     * @param id  UUID занятия
     * @return запрашиваемое занятие
     */
    Lesson getLesson(DaysOfWeek day, String id);

    /**
     * Получить все учебные дни
     *
     * @return запрашиваемые учебные дни
     */
    List<StudyDay> getAllDays();

    /**
     * Получить учебный день по дню недели
     *
     * @param day день недели
     * @return запрашиваемый учебный день
     */
    StudyDay getDay(DaysOfWeek day);

    /**
     * Добавить задание в список заданий
     *
     * @param task добавляемоое задание
     */
    void addTask(Task task);

    /**
     * Получить задания по ID
     *
     * @param id UUID задания
     * @return запрашиваемое задание
     */
    Task getTask(String id);

    /**
     * Отметить задание как выполненное
     *
     * @param task выполненное задание
     */
    void completeTask(Task task);

    /**
     * Обновить данные задания
     *
     * @param task измененное задание
     */
    void updateTask(Task task);

    /**
     * Удалить задание
     *
     * @param task удаляемое задание
     */
    void removeTask(Task task);

    /**
     * Получить все задания
     *
     * @return запрашиваемые задания
     */
    List<Task> getTasks();

    /**
     * Получить активные задания (не отмеченные как выполненные)
     *
     * @return активные задания
     */
    List<Task> getActiveTasks();

    /**
     * Получить законченные задания (отмеченные как выполненные)
     *
     * @return выполненные задания
     */
    List<Task> getCompletedTasks();

    /**
     * Получить просроченные задания (deadline которых раньше текущей даты)
     *
     * @return просроченные задания
     */
    List<Task> getOverdueTasks();
}
