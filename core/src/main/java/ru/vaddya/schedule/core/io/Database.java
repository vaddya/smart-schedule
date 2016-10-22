package ru.vaddya.schedule.core.io;

import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.io.orchestrate.OrchestrateDB;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;

/**
 * Created by Vadim on 10/22/2016.
 */
public interface Database {

    // TODO: 10/23/2016 хорошо ли это?
    static Database get() {
        return new OrchestrateDB();
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
