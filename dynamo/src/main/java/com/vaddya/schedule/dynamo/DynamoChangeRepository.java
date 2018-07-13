package com.vaddya.schedule.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.dynamo.serializers.ChangeSerializer;
import com.vaddya.schedule.dynamo.serializers.LocalDateSerializer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.vaddya.schedule.dynamo.DynamoDatabase.createTableIfNotExists;
import static com.vaddya.schedule.dynamo.DynamoDatabase.deleteTableIfExists;

public class DynamoChangeRepository implements ChangeRepository {

    private static final String TABLE = "changes";

    private static final String DATE_PARAM = String.format(":%sParam", ChangeSerializer.DATE);

    private final AmazonDynamoDB client;

    private final ChangeSerializer serializer;

    public DynamoChangeRepository(AmazonDynamoDB client) {
        createTableIfNotExists(client, TABLE, ChangeSerializer.ID);
        this.client = client;
        this.serializer = new ChangeSerializer();
    }

    @Override
    public Optional<Change> findById(UUID id) {
        Map<String, AttributeValue> change = client.getItem(TABLE, getKey(id)).getItem();
        return Optional.ofNullable(serializer.deserialize(change));
    }

    @Override
    public List<Change> findAll() {
        return client.scan(TABLE, new HashMap<>())
                .getItems()
                .stream()
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Change> findAll(LocalDate date) {
        return client.scan(new ScanRequest()
                .withTableName(TABLE)
                .withFilterExpression(String.format("%s = %s", ChangeSerializer.DATE, DATE_PARAM))
                .withExpressionAttributeValues(new HashMap<String, AttributeValue>() {{
                    put(DATE_PARAM, LocalDateSerializer.serialize(date));
                }}))
                .getItems()
                .stream()
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public void insert(Change change) {
        client.putItem(TABLE, serializer.serialize(change));
    }

    @Override
    public void save(Change change) {
        insert(change);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public long size() {
        return client.describeTable(TABLE).getTable().getItemCount();
    }

    @Override
    public void delete(Change change) {
        client.deleteItem(TABLE, getKey(change.getId()));
    }

    @Override
    public void deleteAll() {
        deleteTableIfExists(client, TABLE);
        createTableIfNotExists(client, TABLE, ChangeSerializer.ID);
    }

    private Map<String, AttributeValue> getKey(UUID id) {
        return new HashMap<String, AttributeValue>() {{
            put(ChangeSerializer.ID, new AttributeValue().withS(id.toString()));
        }};
    }
}
