package com.vaddya.schedule.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;

/**
 * com.vaddya.schedule.database.mongo at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MongoDatabase implements Database {

    private static final String DATABASE_NAME = "smart-schedule";

    private final MongoClient client;

    public MongoDatabase(String host) {
        this.client = new MongoClient(new MongoClientURI(host));
    }

    @Override
    public TaskRepository getTaskRepository() {
        return new MongoTaskRepository(client.getDatabase(DATABASE_NAME));
    }

    @Override
    public LessonRepository getLessonRepository() {
        return new MongoLessonRepository();
    }
}
