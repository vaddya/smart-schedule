package com.vaddya.schedule.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;
import org.bson.Document;

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

    public MongoDatabase(MongoClient client) {
        this.client = client;
    }

    @Override
    public TaskRepository getTaskRepository() {
        return new MongoTaskRepository(getCollection(TASKS));
    }

    @Override
    public LessonRepository getLessonRepository() {
        return new MongoLessonRepository(getCollection(LESSONS));
    }

    @Override
    public ChangeRepository getChangeRepository() {
        return new MongoChangesRepository(getCollection(CHANGES));
    }

    private MongoCollection<Document> getCollection(String name) {
        return client.getDatabase(DB).getCollection(name);
    }
}
