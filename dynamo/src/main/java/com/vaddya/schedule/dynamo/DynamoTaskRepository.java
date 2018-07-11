package com.vaddya.schedule.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.TaskRepository;
import com.vaddya.schedule.dynamo.serializers.TaskSerializer;

import java.util.*;
import java.util.stream.Collectors;

public class DynamoTaskRepository implements TaskRepository {

    private static final String TABLE = "tasks";

    private final AmazonDynamoDB client;

    private final TaskSerializer serializer;

    public DynamoTaskRepository(AmazonDynamoDB client) {
        this.client = client;
        this.serializer = new TaskSerializer();
    }

    private Map<String, AttributeValue> getKey(UUID id) {
        return new HashMap<String, AttributeValue>() {{
           put(TaskSerializer.ID, new AttributeValue().withS(id.toString()));
        }};
    }

    @Override
    public Optional<Task> findById(UUID id) {
        GetItemResult res = client.getItem(TABLE, getKey(id));
        return Optional.ofNullable(serializer.deserialize(res.getItem()));
    }

    @Override
    public List<Task> findAll() {
        return client.scan(TABLE, new HashMap<>())
                .getItems()
                .stream()
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public void insert(Task task) {
        client.putItem(TABLE, serializer.serialize(task));
    }

    @Override
    public void save(Task task) {
        insert(task); // overwrite
    }

    @Override
    public boolean isEmpty() {
        return size() > 0L;
    }

    @Override
    public long size() {
        return client.describeTable(TABLE).getTable().getItemCount();
    }

    @Override
    public void delete(Task task) {
        client.deleteItem(TABLE, getKey(task.getId()));
    }

    @Override
    public void deleteAll() {
        client.deleteTable(TABLE);
    }

}
