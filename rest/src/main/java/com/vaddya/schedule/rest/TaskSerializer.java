package com.vaddya.schedule.rest;

import com.google.gson.*;
import com.vaddya.schedule.core.tasks.Task;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 06, 2017
 */
public class TaskSerializer implements JsonSerializer<Task>, JsonDeserializer<Task> {

    private static final DateTimeFormatter DATE_FORMAT = ofPattern("dd-MM-yyyy");

    private static final String ID = "id";
    private static final String SUBJECT = "subject";
    private static final String TYPE = "type";
    private static final String DEADLINE = "deadline";
    private static final String TEXT_TASK = "textTask";
    private static final String IS_COMPLETE = "isComplete";

    @Override
    public JsonElement serialize(Task src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add(ID, new JsonPrimitive(src.getId().toString()));
        object.add(SUBJECT, new JsonPrimitive(src.getSubject()));
        object.add(TYPE, new JsonPrimitive(src.getType().toString()));
        object.add(DEADLINE, new JsonPrimitive(DATE_FORMAT.format(src.getDeadline())));
        object.add(TEXT_TASK, new JsonPrimitive(src.getTextTask()));
        object.add(IS_COMPLETE, new JsonPrimitive(src.isComplete()));
        return object;
    }

    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject src = json.getAsJsonObject();
        return new Task.Builder()
                .id(src.get(ID).getAsString())
                .subject(src.get(SUBJECT).getAsString())
                .type(src.get(TYPE).getAsString())
                .deadline(DATE_FORMAT.parse(src.get(DEADLINE).getAsString()))
                .textTask(src.get(TEXT_TASK).getAsString())
                .isComplete(src.get(IS_COMPLETE).getAsBoolean())
                .build();
    }

}