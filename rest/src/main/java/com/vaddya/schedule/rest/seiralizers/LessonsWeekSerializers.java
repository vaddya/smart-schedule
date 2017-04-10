package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.*;
import com.vaddya.schedule.core.lessons.StudyWeek;

import java.lang.reflect.Type;
import java.time.DayOfWeek;

/**
 * com.vaddya.schedule.rest.seiralizers at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class LessonsWeekSerializers implements JsonSerializer<StudyWeek> {

    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String LESSONS = "lessons";

    @Override
    public JsonElement serialize(StudyWeek src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (DayOfWeek day : DayOfWeek.values()) {
            JsonObject object = new JsonObject();
            object.addProperty(DAY_OF_WEEK, day.toString());
            JsonElement lessons = context.serialize(src.get(day));
            object.add(LESSONS, lessons);
            array.add(object);
        }
        return array;
    }

}