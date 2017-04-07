package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.*;
import com.vaddya.schedule.core.schedule.StudySchedule;

import java.lang.reflect.Type;
import java.time.DayOfWeek;

/**
 * com.vaddya.schedule.rest.seiralizers at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class StudyScheduleSerializer implements JsonSerializer<StudySchedule> {

    @Override
    public JsonElement serialize(StudySchedule src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (DayOfWeek day : DayOfWeek.values()) {
            JsonObject object = new JsonObject();
            object.addProperty("dayOfWeek", day.toString());
            JsonElement lessons = context.serialize(src.getLessons(day));
            object.add("lessons", lessons);
            array.add(object);
        }
        return array;
    }

}