package com.vaddya.schedule.rest.seiralizers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vaddya.schedule.core.schedule.ScheduleWeek;

import java.lang.reflect.Type;

import static com.vaddya.schedule.rest.controllers.Controller.DATE_FORMAT;
import static java.time.DayOfWeek.MONDAY;

/**
 * com.vaddya.schedule.rest.seiralizers at smart-schedule
 *
 * @author vaddya
 * @since April 07, 2017
 */
public class StudyWeekSerializer implements JsonSerializer<ScheduleWeek> {

    private static final String START_DATE = "startDate";
    private static final String TYPE = "type";
    private static final String DAYS = "days";

    @Override
    public JsonElement serialize(ScheduleWeek src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(START_DATE, DATE_FORMAT.format(src.getWeek().getDateOf(MONDAY)));
        object.addProperty(TYPE, src.getTypeOfWeek().toString());
        JsonElement days = context.serialize(src.getAllDays().values());
        object.add(DAYS, days);
        return object;
    }

}