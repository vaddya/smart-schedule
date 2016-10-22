package ru.vaddya.schedule.core.io.orchestrate;

import io.orchestrate.client.*;
import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.io.Database;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Vadim on 10/21/2016.
 */
public class OrchestrateDB implements Database {

    private static Client client = new OrchestrateClient("e34b621e-515c-4588-86ac-ae306894bdee");
    private static Logger log = Logger.getLogger("OrchestrateDB");

    private static final String TASKS = "tasks";
    private static final String LESSONS = "lessons";
    private static final int LIMIT = 100;

    @Override
    public List<Lesson> getLessons(DaysOfWeek day) {
        List<Lesson> list = new ArrayList<>();
        KvList<LessonPOJO> response = client.listCollection(LESSONS)
                .limit(LIMIT)
                .get(LessonPOJO.class)
                .get();
        for (KvObject<LessonPOJO> obj : response) {
            LessonPOJO pojo = obj.getValue();
            if (pojo.getDay().equals(day.toString())) {
                log.fine("Lesson was read: " + pojo);
                Lesson lesson = new Lesson.Builder()
                        .id(obj.getKey())
                        .startTime(pojo.getStartTime())
                        .endTime(pojo.getEndTime())
                        .subject(pojo.getSubject())
                        .type(pojo.getType())
                        .place(pojo.getPlace())
                        .teacher(pojo.getTeacher())
                        .build();
                list.add(lesson);
            }
        }
        return list;
    }

    @Override
    public boolean addLesson(DaysOfWeek day, Lesson lesson) {
        KvMetadata metadata = client
                .kv(LESSONS, lesson.getId())
                .put(LessonPOJO.of(day, lesson))
                .get();
        log.fine("Lesson was added: " + metadata.toString());
        return true;
    }

    @Override
    public boolean updateLesson(DaysOfWeek day, Lesson lesson) {
        return addLesson(day, lesson);
    }

    @Override
    public boolean changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson) {
        removeLesson(from, lesson);
        addLesson(to, lesson);
        return false;
    }

    @Override
    public boolean removeLesson(DaysOfWeek day, Lesson lesson) {
        boolean res = client
                .kv(LESSONS, lesson.getId())
                .delete()
                .get();
        if (res) {
            log.fine("Lesson " + lesson.getSubject() + " was removed");
        } else {
            log.warning("Lesson " + lesson.getSubject() + " wasn't removed");
        }
        return res;
    }

    @Override
    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        KvList<TaskPOJO> response = client.listCollection(TASKS)
                .limit(LIMIT)
                .get(TaskPOJO.class)
                .get();
        for (KvObject<TaskPOJO> obj : response) {
            TaskPOJO pojo = obj.getValue();
            log.fine("Task was read: " + pojo);
            Task task = new Task.Builder()
                    .id(obj.getKey())
                    .subject(pojo.getSubject())
                    .type(pojo.getType())
                    .deadline(pojo.getDeadline())
                    .textTask(pojo.getTextTask())
                    .isComplete(pojo.isComplete())
                    .build();
            list.add(task);
        }
        return list;
    }

    @Override
    public boolean addTask(Task task) {
        KvMetadata metadata = client
                .kv(TASKS, task.getId())
                .put(TaskPOJO.of(task))
                .get();
        log.fine("Task was added: " + metadata.toString());
        return true;
    }

    @Override
    public boolean updateTask(Task task) {
        return addTask(task);
    }

    @Override
    public boolean removeTask(Task task) {
        boolean res = client
                .kv(TASKS, task.getId())
                .delete()
                .get();
        if (res) {
            log.fine("Task " + task.getTextTask() + " was removed");
        } else {
            log.warning("Task " + task.getTextTask() + " wasn't removed");
        }
        return res;
    }
}