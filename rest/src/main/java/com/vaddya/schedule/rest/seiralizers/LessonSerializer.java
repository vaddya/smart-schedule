package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.*;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.Time;

import java.lang.reflect.Type;

/**
 * com.vaddya.schedule.rest.seiralizers at smart-schedule
 *
 * @author vaddya
 * @since April 07, 2017
 */
public class LessonSerializer implements JsonSerializer<Lesson>, JsonDeserializer<Lesson> {

    private static final String ID = "id";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String SUBJECT = "subject";
    private static final String TYPE = "type";
    private static final String PLACE = "place";
    private static final String TEACHER = "teacher";

    @Override
    public JsonElement serialize(Lesson src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, src.getId().toString());
        object.addProperty(START_TIME, src.getStartTime().toString());
        object.addProperty(END_TIME, src.getEndTime().toString());
        object.addProperty(SUBJECT, src.getSubject());
        object.addProperty(TYPE, src.getType().toString());
        object.addProperty(PLACE, src.getPlace());
        object.addProperty(TEACHER, src.getTeacher());
        return object;
    }

    @Override
    public Lesson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Lesson.Builder builder = new Lesson.Builder()
                .startTime(Time.from(object.get(START_TIME).getAsString()))
                .endTime(Time.from(object.get(END_TIME).getAsString()))
                .subject(object.get(SUBJECT).getAsString())
                .type(object.get(TYPE).getAsString())
                .place(object.get(PLACE).getAsString())
                .teacher(object.get(TEACHER).getAsString());
        JsonElement element = object.get(ID);
        if (element != null) {
            builder.id(element.getAsString());
        }
        return builder.build();
    }

}