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

    private static final String START_DATE = "startDate";
    private static final String TYPE = "type";
    private static final String DAYS = "days";

    @Override
    public JsonElement serialize(StudyWeek src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(START_DATE, DATE_FORMAT.format(src.getWeekTime().getDateOf(MONDAY)));
        object.addProperty(TYPE, src.getWeekType().toString());
        JsonElement days = context.serialize(src.getAllDays().values());
        object.add(DAYS, days);
        return object;
    }

}