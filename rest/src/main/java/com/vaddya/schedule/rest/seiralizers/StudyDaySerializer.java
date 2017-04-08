package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.*;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.schedule.ScheduleDay;

import java.lang.reflect.Type;

import static com.vaddya.schedule.rest.controllers.Controller.DATE_FORMAT;

/**
 * com.vaddya.schedule.rest.seiralizers at smart-schedule
 *
 * @author vaddya
 * @since April 07, 2017
 */
public class StudyDaySerializer implements JsonSerializer<ScheduleDay> {

    private static final String DATE = "date";
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String LESSONS = "lessons";

    @Override
    public JsonElement serialize(ScheduleDay src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(DATE, DATE_FORMAT.format(src.getDate()));
        object.addProperty(DAY_OF_WEEK, src.getDate().getDayOfWeek().toString());
        JsonArray lessons = new JsonArray();
        for (Lesson lesson : src.getLessons()) {
            JsonElement element = context.serialize(lesson);
            lessons.add(element);
        }
        object.add(LESSONS, lessons);
        return object;
    }

}