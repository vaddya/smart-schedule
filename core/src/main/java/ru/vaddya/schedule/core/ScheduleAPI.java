package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by Vadim on 10/6/2016.
 */
public interface ScheduleAPI {

    /**
     * Добавить урок в расписание
     * @param day день недели
     * @param lesson добавляемый урок
     */
    void addLesson(DaysOfWeek day, Lesson lesson);

    /**
     * Удалить урок из расписания
     * @param day день недели
     * @param lesson удаляемый урок
     */
    void removeLesson(DaysOfWeek day, Lesson lesson);

    /**
     * Добавить задачу в список задач
     * @param task добавляемая задача
     */
    void addTask(Task task);

    /**
     * Получить задания по ID
     * @param uuid UUID
     * @return запрашиваемая задача
     */
    Task getTask(UUID uuid);

    /**
     * Отметить задачу как выполненную
     * @param task выполненная задача
     */
    void completeTask(Task task);

    /**
     * Обновить данные задачи
     * @param task измененная задача
     */
    void updateTask(Task task);

    /**
     * Удалить задачу
     * @param task удаляемая задача
     */
    void removeTask(Task task);

    /**
     * Получить урок по дню недели и номеру урока
     * @param day день недели
     * @param index порядковый номер урока
     * @return запрашиваемый урок
     */
    Lesson getLessonByDay(DaysOfWeek day, int index);

    /**
     * Получить все учебные дни
     * @return запрашиваемые учебные дни
     */
    List<StudyDay> getAllDays();

    /**
     * Получить учебный день
     * @param day день недели
     * @return запрашиваемый учебный день
     */
    StudyDay getDay(DaysOfWeek day);

    /**
     * Получить все задачи
     * @return задачи
     */
    List<Task> getTasks();

    /**
     * Получить активные задачи (не отмеченные как выполненные)
     * @return активные задачи
     */
    List<Task> getActiveTasks();

    /**
     * Получить законченные задачи (отмеченные как выполненные)
     * @return выполненные задачи
     */
    List<Task> getCompletedTasks();

    /**
     * Получить просроченные задачи (deadline которых раньше текущей даты)
     * @return просроченные задачи
     */
    List<Task> getOverdueTasks();
}
