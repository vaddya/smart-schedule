package ru.vaddya.schedule.core.io;

import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.io.orchestrate.OrchestrateDB;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;

/**
 * Интерфейс взаимодействия с базой данных
 *
 * @author vaddya
 */
public interface Database {

    // TODO: 10/23/2016 Хорошо ли это
    static Database getConnection() {
        return OrchestrateDB.getConnection();
    }

    List<Lesson> getLessons(DaysOfWeek day);

    boolean addLesson(DaysOfWeek day, Lesson lesson);

    boolean updateLesson(DaysOfWeek day, Lesson lesson);

    boolean changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson);

    boolean removeLesson(DaysOfWeek day, Lesson lesson);

    List<Task> getTasks();

    boolean addTask(Task task);

    boolean updateTask(Task task);

    boolean removeTask(Task task);
}
