package com.vaddya.schedule.android.rest;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.LocalDate;

import java.lang.reflect.Type;

import static com.vaddya.schedule.android.model.Storage.DATE_FORMAT;

/**
 * com.vaddya.schedule.android.rest at android
 *
 * @author vaddya
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return DATE_FORMAT.parseDateTime(json.getAsString()).toLocalDate();
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(DATE_FORMAT.print(src));
    }

}