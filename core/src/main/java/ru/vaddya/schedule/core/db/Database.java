package ru.vaddya.schedule.core.db;

import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.db.orchestrate.OrchestrateDB;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Интерфейс взаимодействия с базой данных
 *
 * @author vaddya
 */
public interface Database {

    static Database getConnection() {
        return OrchestrateDB.getConnection();
    }

    List<Lesson> getLessons(DayOfWeek day);

    boolean addLesson(DayOfWeek day, Lesson lesson);

    boolean updateLesson(DayOfWeek day, Lesson lesson);

    boolean changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson);

    boolean removeLesson(DayOfWeek day, Lesson lesson);

    List<Task> getTasks();

    boolean addTask(Task task);

    boolean updateTask(Task task);

    boolean removeTask(Task task);
}
