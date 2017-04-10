package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.*;
import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.changes.ChangeType;
import com.vaddya.schedule.core.lessons.Lesson;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.UUID;

import static com.vaddya.schedule.rest.controllers.Controller.DATE_FORMAT;

/**
 * com.vaddya.schedule.rest.seiralizers at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class ChangeSerializer implements JsonSerializer<Change>, JsonDeserializer<Change> {

    private static final String ID = "id";
    private static final String CHANGE_TYPE = "changeType";
    private static final String DATE = "date";
    private static final String LESSON = "lesson";

    @Override
    public JsonElement serialize(Change src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(ID, src.getId().toString());
        object.addProperty(CHANGE_TYPE, src.getChangeType().toString());
        object.addProperty(DATE, DATE_FORMAT.format(src.getDate()));
        JsonElement lesson = context.serialize(src.getLesson());
        object.add(LESSON, lesson);
        return object;
    }

    @Override
    public Change deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        LocalDate date = LocalDate.from(DATE_FORMAT.parse(object.get(DATE).getAsString()));
        ChangeType changeType = ChangeType.valueOf(object.get(CHANGE_TYPE).getAsString());
        Lesson lesson = context.deserialize(object.get(LESSON), Lesson.class);
        JsonElement element = object.get(ID);
        if (element != null) {
            UUID id = UUID.fromString(element.getAsString());
            return new Change(id, changeType, date, lesson);
        }
        return new Change(changeType, date, lesson);
    }

}