package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;
import ru.vaddya.schedule.core.exceptions.NoSuchTaskException;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * Найти занятие по ID
     *
     * @param id UUID занятия
     * @return запрашиваемое занятие
     * @throws NoSuchLessonException если указан несуществующий ID
     */
    Lesson findLesson(UUID id);

    /**
     * Найти занятие по дню недели и порядковому номеру
     *
     * @param day   день недели
     * @param index порядковый номер
     * @return запрашиваемое занятие
     * @throws IndexOutOfBoundsException если указан неверный индекс
     */
    Lesson findLesson(DaysOfWeek day, int index);

    /**
     * Получить учебный день по дню недели
     *
     * @param day день недели
     * @return запрашиваемый учебный день
     */
    List<Lesson> getLessons(DaysOfWeek day);

    /**
     * Получить все учебные дни и все занятие в эти дни
     *
     * @return Карта, ключами которой являются элекменты DaysOfWeek,
     * а значениями являются списки занятий в этот день
     */
    Map<DaysOfWeek, List<Lesson>> getAllLessons();

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
     * Добавить задание в список заданий
     *
     * @param task добавляемоое задание
     */
    void addTask(Task task);

    /**
     * Получить задание по ID
     *
     * @param id UUID задания
     * @return запрашиваемое задание
     * @throws NoSuchTaskException если указан несуществующий ID
     */
    Task findTask(UUID id);

    /**
     * Получить задание по порядковому номеру
     *
     * @param index порядковый номер
     * @return запрашиваемое задание
     * @throws IndexOutOfBoundsException если указан неверный индекс
     */
    Task findTask(int index);

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
}
