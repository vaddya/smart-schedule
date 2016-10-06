package ru.vaddya.schedule.core;

import java.util.List;

/**
 * Created by Vadim on 10/6/2016.
 */
public interface ScheduleAPI {
    void addLesson(DaysOfWeek day, Lesson lesson);
    void removeLesson(DaysOfWeek day, Lesson lesson);
    void addTask(Task task);
    void completeTask(Task task);
    void removeTask(Task task);
    Task getTaskByText(String taskText);
    StudyDay getDay(DaysOfWeek day);
    StudyWeek getWeek();
    List<Task> getActiveTasks();
    List<Task> getCompletedTasks();
    List<Task> getOverdueTasks();
}
