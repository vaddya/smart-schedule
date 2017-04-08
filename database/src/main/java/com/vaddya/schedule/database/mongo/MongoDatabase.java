package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.core.utils.Time;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;
import com.vaddya.schedule.database.mongo.serializers.LocalDateSerializer;
import com.vaddya.schedule.database.mongo.serializers.TimeSerializer;
import org.bson.Document;

import java.time.LocalDate;

/**
 * База данных MongoDB
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MongoDatabase implements Database {

    private static final String DB = "smart-schedule";
    private static final String TASKS = "tasks";
    private static final String LESSONS = "lessons";
    private static final String CHANGES = "changes";

    private final MongoClient client;
    private final Gson gson;

    public MongoDatabase(MongoClient client) {
        this.client = client;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
    }

    @Override
    public TaskRepository getTaskRepository() {
        return new MongoTaskRepository(getCollection(TASKS), gson);
    }

    @Override
    public LessonRepository getLessonRepository() {
        return new MongoLessonRepository(getCollection(LESSONS), gson);
    }

    @Override
    public ChangeRepository getChangeRepository() {
        return new MongoChangesRepository(getCollection(CHANGES), gson);
    }

    private MongoCollection<Document> getCollection(String name) {
        return client.getDatabase(DB).getCollection(name);
    }

}