package com.vaddya.schedule.dynamo.serializers;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.vaddya.schedule.core.lessons.Lesson;

import java.util.HashMap;
import java.util.Map;

public class LessonSerializer implements DynamoSerializer<Lesson> {

    public static final String ID = "id";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String SUBJECT = "subject";
    public static final String TYPE = "type";
    public static final String PLACE = "place";
    public static final String TEACHER = "teacher";

    @Override
    public Map<String, AttributeValue> serialize(Lesson lesson) {
        return new HashMap<String, AttributeValue>() {{
            put(ID, new AttributeValue().withS(lesson.getId().toString()));
            put(START_TIME, TimeSerializer.serialize(lesson.getStartTime()));
            put(END_TIME, TimeSerializer.serialize(lesson.getEndTime()));
            put(SUBJECT, new AttributeValue().withS(lesson.getSubject()));
            put(TYPE, new AttributeValue().withS(lesson.getType().toString()));
            put(PLACE, new AttributeValue().withS(lesson.getPlace()));
            put(TEACHER, new AttributeValue().withS(lesson.getTeacher()));
        }};
    }

    @Override
    public Lesson deserialize(Map<String, AttributeValue> attr) {
        try {
            return new Lesson.Builder()
                    .id(attr.get(ID).getS())
                    .startTime(TimeSerializer.deserialize(attr.get(START_TIME)))
                    .endTime(TimeSerializer.deserialize(attr.get(END_TIME)))
                    .subject(attr.get(SUBJECT).getS())
                    .type(attr.get(TYPE).getS())
                    .place(attr.get(PLACE).getS())
                    .teacher(attr.get(TEACHER).getS())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
