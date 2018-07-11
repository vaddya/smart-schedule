package com.vaddya.schedule.dynamo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;

public class DynamoDatabase implements Database {

    private static final String TASKS = "tasks";
    private static final String LESSONS = "lessons";
    private static final String CHANGES = "changes";

    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();

    private final DynamoDB db = new DynamoDB(client);

    @Override
    public TaskRepository getTaskRepository() {
        return new DynamoTaskRepository(db.getTable(TASKS));
    }

    @Override
    public LessonRepository getLessonRepository() {
        return new DynamoLessonRepository();
    }

    @Override
    public ChangeRepository getChangeRepository() {
        return new DynamoChangeRepository();
    }
}
