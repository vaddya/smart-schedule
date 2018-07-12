package com.vaddya.schedule.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;

public class DynamoDatabase implements Database {

    private final AmazonDynamoDB client;

    public DynamoDatabase(AmazonDynamoDB client) {
        this.client = client;
    }

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

    static void createTableIfNotExists(AmazonDynamoDB client, String table, String key) {
        TableUtils.createTableIfNotExists(client, new CreateTableRequest(
                Utils.listOf(new AttributeDefinition(key, ScalarAttributeType.S)),
                table,
                Utils.listOf(new KeySchemaElement(key, KeyType.HASH)),
                new ProvisionedThroughput(5L, 5L)
        ));
    }

    static void deleteTableIfExists(AmazonDynamoDB client, String table) {
        TableUtils.deleteTableIfExists(client, new DeleteTableRequest(table));
    }

}
