package com.vaddya.schedule.database.mongo.serializers;

import com.google.gson.*;
import com.vaddya.schedule.core.utils.Time;

import java.lang.reflect.Type;

/**
 * com.vaddya.schedule.database.mongo.serializers at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class TimeSerializer implements JsonSerializer<Time>, JsonDeserializer<Time> {

    @Override
    public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Time.from(json.getAsString());
    }

}