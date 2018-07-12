package com.vaddya.schedule.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.dynamo.serializers.LessonSerializer;
import com.vaddya.schedule.dynamo.serializers.TaskSerializer;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import static com.vaddya.schedule.dynamo.DynamoDatabase.createTableIfNotExists;

public class DynamoLessonRepository implements LessonRepository {

    public static final String TABLE = "lessons";

    private static final String TYPE_OF_WEEK = "typeOfWeek";

    private static final String DAY_OF_WEEK = "dayOfWeek";

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
        // TODO: querys
        return client.scan(TABLE, new HashMap<>())
                .getItems()
                .stream()
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TypeOfWeek> findTypeOfWeek(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<DayOfWeek> findDayOfWeek(UUID id) {
        return Optional.empty();
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
        AttributeValueUpdate update = new AttributeValueUpdate(new AttributeValue(LessonSerializer.TYPE), AttributeAction.PUT);
        client.updateItem(TABLE, getKey(lesson.getId()), new HashMap<String, AttributeValueUpdate>() {{
            put(LessonSerializer.TYPE, update);
        }});
    }

    @Override
    public void saveDayOfWeek(Lesson lesson, DayOfWeek day) {

    }

    @Override
    public void swapWeeks() {

    }

    @Override
    public void delete(UUID id) {
        client.deleteItem(TABLE, getKey(id));
    }

    @Override
    public void deleteAll(TypeOfWeek week) {

    }

    @Override
    public void deleteAll(TypeOfWeek week, DayOfWeek day) {

    }

    private Map<String, AttributeValue> getKey(UUID id) {
        return new HashMap<String, AttributeValue>() {{
            put(TaskSerializer.ID, new AttributeValue().withS(id.toString()));
        }};
    }
}
