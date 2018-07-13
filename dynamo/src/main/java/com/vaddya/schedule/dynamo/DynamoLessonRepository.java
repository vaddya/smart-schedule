package com.vaddya.schedule.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.dynamo.serializers.LessonSerializer;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import static com.vaddya.schedule.dynamo.DynamoDatabase.createTableIfNotExists;

public class DynamoLessonRepository implements LessonRepository {

    private static final String TABLE = "lessons";

    private static final String TYPE_OF_WEEK = "typeOfWeek";
    private static final String TYPE_OF_WEEK_PARAM = String.format(":%sParam", TYPE_OF_WEEK);

    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String DAY_OF_WEEK_PARAM = String.format(":%sParam", DAY_OF_WEEK);

    private static final String BOTH_WEEK_PARAM = String.format(":%sParam", TypeOfWeek.BOTH);

    private final AmazonDynamoDB client;

    private final LessonSerializer serializer;

    public DynamoLessonRepository(AmazonDynamoDB client) {
        createTableIfNotExists(client, TABLE, LessonSerializer.ID);
        this.client = client;
        this.serializer = new LessonSerializer();
    }

    @Override
    public Optional<Lesson> findById(UUID id) {
        GetItemResult res = client.getItem(TABLE, getKey(id));
        return Optional.ofNullable(serializer.deserialize(res.getItem()));
    }

    @Override
    public List<Lesson> findAll() {
        return client.scan(TABLE, new HashMap<>())
                .getItems()
                .stream()
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Lesson> findAll(TypeOfWeek week, DayOfWeek day) {
        return client.scan(new ScanRequest()
                .withTableName(TABLE)
                .withFilterExpression(String.format("%s IN (%s, %s) AND %s = %s",
                        TYPE_OF_WEEK, TYPE_OF_WEEK_PARAM, BOTH_WEEK_PARAM, DAY_OF_WEEK, DAY_OF_WEEK_PARAM))
                .withExpressionAttributeValues(new HashMap<String, AttributeValue>() {{
                    put(TYPE_OF_WEEK_PARAM, new AttributeValue().withS(week.toString()));
                    put(BOTH_WEEK_PARAM, new AttributeValue().withS(TypeOfWeek.BOTH.toString()));
                    put(DAY_OF_WEEK_PARAM, new AttributeValue().withS(day.toString()));
                }}))
                .getItems()
                .stream()
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TypeOfWeek> findTypeOfWeek(UUID id) {
        try {
            return Optional.of(TypeOfWeek.valueOf(client.getItem(TABLE, getKey(id)).getItem().get(TYPE_OF_WEEK).getS()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<DayOfWeek> findDayOfWeek(UUID id) {
        try {
            return Optional.of(DayOfWeek.valueOf(client.getItem(TABLE, getKey(id)).getItem().get(DAY_OF_WEEK).getS()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void insert(TypeOfWeek week, DayOfWeek day, Lesson lesson) {
        Map<String, AttributeValue> attr = serializer.serialize(lesson);
        attr.put(TYPE_OF_WEEK, new AttributeValue().withS(week.toString()));
        attr.put(DAY_OF_WEEK, new AttributeValue().withS(day.toString()));
        client.putItem(TABLE, attr);
    }

    @Override
    public void save(Lesson lesson) {
        UUID id = lesson.getId();
        TypeOfWeek type = findTypeOfWeek(id).orElseThrow(() -> new NoSuchLessonException(id));
        DayOfWeek day = findDayOfWeek(id).orElseThrow(() -> new NoSuchLessonException(id));
        insert(type, day, lesson);
    }

    @Override
    public void saveTypeOfWeek(Lesson lesson, TypeOfWeek week) {
        client.updateItem(new UpdateItemRequest()
                .withTableName(TABLE)
                .withKey(getKey(lesson.getId()))
                .withUpdateExpression(String.format("set %s = %s", TYPE_OF_WEEK, TYPE_OF_WEEK_PARAM))
                .withExpressionAttributeValues(new HashMap<String, AttributeValue>() {{
                    put(TYPE_OF_WEEK_PARAM, new AttributeValue().withS(week.toString()));
                }})
        );
    }

    @Override
    public void saveDayOfWeek(Lesson lesson, DayOfWeek day) {
        client.updateItem(TABLE, getKey(lesson.getId()), new HashMap<String, AttributeValueUpdate>() {{
            put(DAY_OF_WEEK, new AttributeValueUpdate(new AttributeValue(DAY_OF_WEEK), AttributeAction.PUT));
        }});
    }

    @Override
    public void swapWeeks() {
        // TODO
    }

    @Override
    public void delete(UUID id) {
        client.deleteItem(TABLE, getKey(id));
    }

    @Override
    public void deleteAll(TypeOfWeek week) {
        client.deleteItem(new DeleteItemRequest()
                .withTableName(TABLE)
                .withConditionExpression(String.format("%s = %s", TYPE_OF_WEEK, TYPE_OF_WEEK_PARAM))
                .withExpressionAttributeValues(new HashMap<String, AttributeValue>() {{
                    put(TYPE_OF_WEEK_PARAM, new AttributeValue().withS(week.toString()));
                }})
        );
    }

    @Override
    public void deleteAll(TypeOfWeek week, DayOfWeek day) {
        client.deleteItem(new DeleteItemRequest()
                .withTableName(TABLE)
                .withConditionExpression(String.format("%s = %s AND %s = %s",
                        TYPE_OF_WEEK, TYPE_OF_WEEK_PARAM, DAY_OF_WEEK, DAY_OF_WEEK_PARAM))
                .withExpressionAttributeValues(new HashMap<String, AttributeValue>() {{
                    put(TYPE_OF_WEEK_PARAM, new AttributeValue().withS(week.toString()));
                    put(DAY_OF_WEEK_PARAM, new AttributeValue().withS(day.toString()));
                }})
        );
    }

    private Map<String, AttributeValue> getKey(UUID id) {
        return new HashMap<String, AttributeValue>() {{
            put(LessonSerializer.ID, new AttributeValue().withS(id.toString()));
        }};
    }
}
