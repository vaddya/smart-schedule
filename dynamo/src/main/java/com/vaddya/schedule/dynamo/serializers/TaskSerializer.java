package com.vaddya.schedule.dynamo.serializers;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.vaddya.schedule.core.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskSerializer implements DynamoSerializer<Task> {

    public static final String ID = "id";
    public static final String SUBJECT = "subject";
    public static final String TYPE = "type";
    public static final String DEADLINE = "deadline";
    public static final String TEXT = "text";
    public static final String COMPLETE = "complete";

    public Map<String, AttributeValue> serialize(Task task) {
        return new HashMap<String, AttributeValue>() {{
            put(ID, new AttributeValue().withS(task.getId().toString()));
            put(SUBJECT, new AttributeValue().withS(task.getSubject()));
            put(TYPE, new AttributeValue().withS(task.getType().toString()));
            put(DEADLINE, LocalDateSerializer.serialize(task.getDeadline()));
            put(TEXT, new AttributeValue().withS(task.getTextTask()));
            put(COMPLETE, new AttributeValue().withBOOL(task.isComplete()));
        }};
    }

    public Task deserialize(Map<String, AttributeValue> attr) {
        try {
            return new Task.Builder()
                    .id(attr.get(ID).getS())
                    .subject(attr.get(SUBJECT).getS())
                    .type(attr.get(TYPE).getS())
                    .deadline(LocalDateSerializer.deserialize(attr.get(DEADLINE)))
                    .textTask(attr.get(TEXT).getS())
                    .isComplete(attr.get(COMPLETE).getBOOL())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
