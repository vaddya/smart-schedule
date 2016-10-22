package ru.vaddya.schedule.core.io.orchestrate;

import io.orchestrate.client.*;
import io.orchestrate.client.jsonpatch.JsonPatch;
import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.io.Database;
import ru.vaddya.schedule.core.utils.DaysOfWeek;
import ru.vaddya.schedule.core.utils.LessonType;

import java.util.logging.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vadim on 10/21/2016.
 */
public class OrchestrateDB implements Database {

    private static void lessons() {
        Client client = new OrchestrateClient("key");
        client.kv("lessons", "wednesday")
                .put(new LessonPOJO(DaysOfWeek.WEDNESDAY.toString(), "10:00", "11:30", "Программирование", "LAB", "9-309", "Вылегжанина К.Д."))
                .get();

        KvList<LessonPOJO> results =
                client.listCollection("lessons")
                        .get(LessonPOJO.class)
                        .get();

        for (KvObject<LessonPOJO> obj : results) {
            System.out.println(obj.getKey());
        }
    }

    private static void tasks() {
        Client client = new OrchestrateClient("key");
        client.kv("tasks", "1")
                .put(new TaskPOJO("Высшая математика", "PRACTICE", "25.10.2016", "№3, №4", false))
                .get();

        KvList<TaskPOJO> results =
                client.listCollection("tasks")
                        .get(TaskPOJO.class)
                        .get();

        for (KvObject<TaskPOJO> obj : results) {
            System.out.println(obj.getKey());
        }
    }

    public static void main(String[] args) {
        OrchestrateDB db = new OrchestrateDB();
        //lessons();
//        Task task = new Task.Builder()
//                .subject("Программирование")
//                .type(LessonType.LAB)
//                .deadline("31.12.2016")
//                .textTask("Выполнить курсовую работу")
//                .isComplete(false)
//                .build();
//        db.addTask(task);

        for (Task t : db.getTasks()) {
            System.out.println(t);
        }
    }

    private Client client = new OrchestrateClient("e34b621e-515c-4588-86ac-ae306894bdee");
    private Logger log = Logger.getLogger(getClass().toString());

    private static final String TASKS = "tasks";
    private static final String LESSONS = "lessons";

    @Override
    public List<Lesson> getLessons(DaysOfWeek day) {
        return null;
    }

    @Override
    public boolean addLesson(DaysOfWeek day, Lesson lesson) {
        return false;
    }

    @Override
    public boolean updateLesson(DaysOfWeek day, Lesson lesson) {
        return false;
    }

    @Override
    public boolean changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson) {
        return false;
    }

    @Override
    public boolean removeLesson(DaysOfWeek day, Lesson lesson) {
        return client
                .kv(LESSONS, lesson.getId())
                .delete()
                .get();
    }

    @Override
    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        KvList<TaskPOJO> response = client.listCollection(TASKS)
                .limit(100)
                .get(TaskPOJO.class)
                .get();
        for (KvObject<TaskPOJO> obj : response) {
            TaskPOJO pojo = obj.getValue();
            Task task = new Task.Builder()
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
                .get(10, TimeUnit.SECONDS);
        log.info(metadata.toString());
        log.info(metadata.getKey());
        log.info(metadata.getRef());
        return true;
    }

    @Override
    public boolean updateTask(Task task) {
        return addTask(task);
    }

    @Override
    public boolean removeTask(Task task) {
        return client
                .kv(TASKS, task.getId())
                .delete()
                .get();
    }
}