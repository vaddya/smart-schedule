package com.vaddya.schedule.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;
import org.bson.Document;

/**
 * com.vaddya.schedule.database.mongo at smart-schedule
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

    public MongoDatabase(String host) {
        this.client = new MongoClient(new MongoClientURI(host));
    }

    @Override
    public TaskRepository getTaskRepository() {
        MongoCollection<Document> collection = client.getDatabase(DB).getCollection(TASKS);
        return new MongoTaskRepository(collection);
    }

    @Override
    public LessonRepository getLessonRepository() {
        MongoCollection<Document> collection = client.getDatabase(DB).getCollection(LESSONS);
        return new MongoLessonRepository(collection);
    }

    @Override
    public ChangeRepository getChangesRepository() {
        MongoCollection<Document> collection = client.getDatabase(DB).getCollection(CHANGES);
        return new MongoChangesRepository(collection);
    }
}
