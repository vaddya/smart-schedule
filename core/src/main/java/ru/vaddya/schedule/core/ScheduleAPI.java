package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Vadim on 10/6/2016.
 */
public interface ScheduleAPI {
    void addLesson(DaysOfWeek day, Lesson lesson);
    void removeLesson(DaysOfWeek day, Lesson lesson);
    void addTask(Task task);
    void completeTask(Task task);
    void removeTask(Task task);
    Lesson getLessonByDay(DaysOfWeek day, int i);
    Task getTaskByText(String taskText) throws NoSuchElementException;
    StudyDay getDay(DaysOfWeek day);
    StudyWeek getWeek();
    List<Task> getTasks();
    List<Task> getActiveTasks();
    List<Task> getCompletedTasks();
    List<Task> getOverdueTasks();
}
