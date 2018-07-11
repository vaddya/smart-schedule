package com.vaddya.schedule.dynamo.serializers;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * com.vaddya.schedule.database.mongo at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public final class LocalDateSerializer {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate deserialize(AttributeValue attr) {
        return LocalDate.parse(attr.getS(), DATE_FORMAT);
    }

    public static AttributeValue serialize(LocalDate date) {
        return new AttributeValue().withS(DATE_FORMAT.format(date));
    }
}
