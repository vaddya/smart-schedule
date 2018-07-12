package com.vaddya.schedule.dynamo.serializers;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.changes.ChangeType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChangeSerializer implements DynamoSerializer<Change> {

    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String LESSON = "lesson";

    private final LessonSerializer lessonSerializer = new LessonSerializer();

    @Override
    public Map<String, AttributeValue> serialize(Change change) {
        return new HashMap<String, AttributeValue>() {{
            put(ID, new AttributeValue().withS(change.getId().toString()));
            put(TYPE, new AttributeValue().withS(change.getChangeType().toString()));
            put(DATE, LocalDateSerializer.serialize(change.getDate()));
            put(LESSON, new AttributeValue().withM(lessonSerializer.serialize(change.getLesson())));
        }};
    }

    @Override
    public Change deserialize(Map<String, AttributeValue> attr) {
        try {
            return new Change(
                    UUID.fromString(attr.get(ID).getS()),
                    ChangeType.valueOf(attr.get(TYPE).getS()),
                    LocalDateSerializer.deserialize(attr.get(DATE)),
                    lessonSerializer.deserialize(attr.get(LESSON).getM())
            );
        } catch (Exception e) {
            return null;
        }
    }
}
