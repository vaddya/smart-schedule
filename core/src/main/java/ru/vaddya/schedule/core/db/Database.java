package ru.vaddya.schedule.core.db;

import ru.vaddya.schedule.core.db.orchestrate.OrchestrateDB;
import ru.vaddya.schedule.core.lessons.ChangedLesson;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс взаимодействия с базой данных
 *
 * @author vaddya
 */
public interface Database {

    static Database getConnection() {
        return OrchestrateDB.getConnection();
    }


    Map<DayOfWeek, List<Lesson>> getLessons(WeekType week);

    boolean addLesson(WeekType week, DayOfWeek day, Lesson lesson);

    boolean updateLesson(WeekType week, DayOfWeek day, Lesson lesson);

    boolean changeLessonDay(WeekType week, DayOfWeek from, DayOfWeek to, Lesson lesson);

    boolean removeLesson(WeekType week, DayOfWeek day, Lesson lesson);


    List<ChangedLesson> getChanges(LocalDate date);

    boolean addChange(ChangedLesson lesson);


    List<Task> getTasks();

    boolean addTask(Task task);

    boolean updateTask(Task task);

    boolean removeTask(Task task);
}
