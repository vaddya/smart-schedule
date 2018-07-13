package com.vaddya.schedule.dynamo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;

import java.util.ArrayList;

public class DynamoDatabase implements Database {

    private final AmazonDynamoDB client;

    public DynamoDatabase() {
        this.client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

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
                new ArrayList<AttributeDefinition>() {{
                    add(new AttributeDefinition(key, ScalarAttributeType.S));
                }},
                table,
                new ArrayList<KeySchemaElement>() {{
                    add(new KeySchemaElement(key, KeyType.HASH));
                }},
                new ProvisionedThroughput(5L, 5L)
        ));
    }

    static void deleteTableIfExists(AmazonDynamoDB client, String table) {
        TableUtils.deleteTableIfExists(client, new DeleteTableRequest(table));
    }

}
