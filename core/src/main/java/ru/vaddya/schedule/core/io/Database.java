package ru.vaddya.schedule.core.io;

import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.StudyWeek;
import ru.vaddya.schedule.core.Task;

import java.util.List;

/**
 * Created by Vadim on 10/22/2016.
 */
public interface Database {

    List<Task> getTasks();

    StudyWeek getStudyWeek();

    boolean addLesson(Lesson lesson);

    boolean addTask(Task task);

    boolean completeTask(Task task);

    boolean removeTask(Task task);
}
