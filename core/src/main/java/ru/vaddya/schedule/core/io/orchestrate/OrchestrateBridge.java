package ru.vaddya.schedule.core.io.orchestrate;

import io.orchestrate.client.Client;
import io.orchestrate.client.KvList;
import io.orchestrate.client.KvObject;
import io.orchestrate.client.OrchestrateClient;

/**
 * Created by Vadim on 10/21/2016.
 */
public class OrchestrateBridge {

    public static void main(String[] args) {
        lessons();
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
}