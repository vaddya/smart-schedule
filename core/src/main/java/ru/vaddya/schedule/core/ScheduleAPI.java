package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;
import ru.vaddya.schedule.core.exceptions.NoSuchTaskException;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/*TODO: Общие замечания:
Мне кажется такая плотная интеграция с базой данных оказывает отрицательное влияние на проект. В ядре для того,
чтобы удобнее работать с бд появляется много методов для работы со строками, выполняются всякие конвертации строк.
Мне кажется это лучше вынести из ядра в отдельный слой. Пусть будет что-нибудь типа прокси, который перехватывает все
вызовы методов api, конвертирует все что нужно в строки и отправляет в бд и подсовывает данные для классов ядра когда
это нужно. Мне кажется, что было бы здорово отделить всю логику взаимодействия с бд от ядра. Кроме того,
мне немного не нравятся методы, которые подтверждают какие-либо действия типа обновления урока или завершения задания
(отмечены ниже отдельно todoшками). Так как сейчас неплохо,но если вынести эти методы из api в соответсвующие классы,
было бы удобнее: api содержало бы меньше методов и не казалось таким сложным. Но я не знаю реально ли это все сделать так,
и при этом реализовать достаточно прозрачную работу с бд.
 */

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


    //TODO: Может было бы удобнее выдавать объект класса StudyDay?
    //Может копию того, что внутри лежит, или что-нибудь похожее с ограниченной фуннциональностью,
    //чтобы пользователь не поламал... Мне кажется так было бы удобнее.
    /**
     * Получить учебный день по дню недели
     *
     * @param day день недели
     * @return запрашиваемый учебный день
     */
    List<Lesson> getLessons(DaysOfWeek day);

    //TODO: Аналогично предыдущему методу.
    /**
     * Получить все учебные дни и все занятие в эти дни
     *
     * @return Карта, ключами которой являются элекменты DaysOfWeek,
     * а значениями являются списки занятий в этот день
     */
    Map<DaysOfWeek, List<Lesson>> getAllLessons();

    //TODO: Если честно, мне бы хотелось обновлять урок как-нибудь так: Lesson.update().
    //Мне кажется так было бы удобнее и удалось бы разгрузить api, убрав часть методов.
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

    //TODO: Хотелось бы уметь удалять урок по UUID или по индексу, аналогично поиску.
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

    //TODO Аналогично урокам, хотелось получать что-нибудь более специфичное.
    //Кроме того класс StudyTask может выдавать списки с разными типами тасок, что тоже удобно и сможет упростить api.
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

    //TODO: Аналогично урокам хотелось бы завершать задание Task.complete(). Тоже самое с методами ниже.
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
    //TODO: Также хотелось бы уметь удалять по UUID и индексу.
    /**
     * Удалить задание
     *
     * @param task удаляемое задание
     */
    void removeTask(Task task);
}
