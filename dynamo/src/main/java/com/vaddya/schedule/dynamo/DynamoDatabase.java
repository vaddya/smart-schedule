package com.vaddya.schedule.dynamo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;

public class DynamoDatabase implements Database {

    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .build();

    @Override
    public TaskRepository getTaskRepository() {
        return new DynamoTaskRepository(client);
    }

    @Override
    public LessonRepository getLessonRepository() {
        return new DynamoLessonRepository(client);
    }

    @Override
    public ChangeRepository getChangeRepository() {
        return new DynamoChangeRepository(client);
    }
}
