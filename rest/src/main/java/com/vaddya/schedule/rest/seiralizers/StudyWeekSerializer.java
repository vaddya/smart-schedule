package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vaddya.schedule.core.lessons.StudyWeek;

import java.lang.reflect.Type;

import static com.vaddya.schedule.rest.controllers.Controller.DATE_FORMAT;
import static java.time.DayOfWeek.MONDAY;

/**
 * com.vaddya.schedule.rest.seiralizers at smart-schedule
 *
 * @author vaddya
 * @since April 07, 2017
 */
public class StudyWeekSerializer implements JsonSerializer<StudyWeek> {

    @Override
    public JsonElement serialize(StudyWeek src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("startDate", DATE_FORMAT.format(src.getWeekTime().getDateOf(MONDAY)));
        object.addProperty("type", src.getWeekType().toString());
        JsonElement days = context.serialize(src.getAllDays());
        object.add("days", days);
        return object;
    }

}