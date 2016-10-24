package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;
import java.util.Map;

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
     * @param day день недели
     * @param i   порядковый номер занятия
     */
    void removeLesson(DaysOfWeek day, int i);

    /**
     * Получить занятие по дню недели и ID занятия
     *
     * @param day день недели
     * @param id  UUID занятия
     * @return запрашиваемое занятие
     */
    Lesson getLesson(DaysOfWeek day, String id);

    /**
     * Получить все учебные дни и все занятие в эти дни
     *
     * @return Карта, ключами которой являются элекменты DaysOfWeek,
     * а значениями являются списки занятий в этот день
     */
    Map<DaysOfWeek, List<Lesson>> getAllLessons();

    /**
     * Получить учебный день по дню недели
     *
     * @param day день недели
     * @return запрашиваемый учебный день
     */
    List<Lesson> getLessons(DaysOfWeek day);

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
     * Удалить задание
     *
     * @param i порядковый номер задания
     */
    void removeTask(int i);

    /**
     * Получить все задания
     *
     * @return запрашиваемые задания
     */
    List<Task> getAllTasks();

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
