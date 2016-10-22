package ru.vaddya.schedule.core.io.orchestrate;

import io.orchestrate.client.Client;
import io.orchestrate.client.KvList;
import io.orchestrate.client.KvObject;
import io.orchestrate.client.OrchestrateClient;
import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.StudyWeek;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.io.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadim on 10/21/2016.
 */
public class OrchestrateDB implements Database {

    public static void main(String[] args) {
        //lessons();
        for (Task task : new OrchestrateDB().getTasks()) {
            System.out.println(task);
        }
    }

    private static void lessons() {
        Client client = new OrchestrateClient("key");
        client.kv("lessons", "wednesday")
                .put(new LessonPOJO("10:00", "11:30", "Программирование", "LAB", "9-309", "Вылегжанина К.Д."))
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

    Client client = new OrchestrateClient("key");

    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        KvList<TaskPOJO> response = client.listCollection("tasks")
                .limit(100)
                .get(TaskPOJO.class)
                .get();
        for (KvObject<TaskPOJO> obj : response) {
            TaskPOJO pojo = obj.getValue();
            Task task = new Task.Builder()
                    .subject(pojo.getSubject())
                    .lessonType(pojo.getType())
                    .deadline(pojo.getDeadline())
                    .textTask(pojo.getTextTask())
                    .isComplete(pojo.isComplete())
                    .build();
            list.add(task);
        }
        return list;
    }

    @Override
    public StudyWeek getStudyWeek() {
        return null;
    }

    @Override
    public boolean addLesson(Lesson lesson) {
        return false;
    }

    @Override
    public boolean addTask(Task task) {
        return false;
    }

    @Override
    public boolean completeTask(Task task) {
        return false;
    }

    @Override
    public boolean removeTask(Task task) {
        return false;
    }
}