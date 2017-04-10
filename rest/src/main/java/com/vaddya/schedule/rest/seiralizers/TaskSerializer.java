package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.*;
import com.vaddya.schedule.core.tasks.Task;

import java.lang.reflect.Type;

import static com.vaddya.schedule.rest.controllers.TasksController.DATE_FORMAT;

/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 06, 2017
 */
public class TaskSerializer implements JsonSerializer<Task>, JsonDeserializer<Task> {

    private static final String ID = "id";
    private static final String SUBJECT = "subject";
    private static final String TYPE = "type";
    private static final String DEADLINE = "deadline";
    private static final String TEXT_TASK = "textTask";
    private static final String IS_COMPLETE = "isComplete";

    @Override
    public JsonElement serialize(Task src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, src.getId().toString());
        object.addProperty(SUBJECT, src.getSubject());
        object.addProperty(TYPE, src.getType().toString());
        object.addProperty(DEADLINE, DATE_FORMAT.format(src.getDeadline()));
        object.addProperty(TEXT_TASK, src.getTextTask());
        object.addProperty(IS_COMPLETE, src.isComplete());
        return object;
    }

    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject src = json.getAsJsonObject();
        Task.Builder builder = new Task.Builder()
                .subject(src.get(SUBJECT).getAsString())
                .type(src.get(TYPE).getAsString())
                .deadline(DATE_FORMAT.parse(src.get(DEADLINE).getAsString()))
                .textTask(src.get(TEXT_TASK).getAsString())
                .isComplete(src.get(IS_COMPLETE).getAsBoolean());
        JsonElement id = src.get(ID);
        if (id != null) {
            builder.id(src.get(ID).getAsString());
        }
        return builder.build();
    }

}