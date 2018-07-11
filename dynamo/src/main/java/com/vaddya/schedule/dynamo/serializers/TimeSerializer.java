package com.vaddya.schedule.dynamo.serializers;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.vaddya.schedule.core.utils.Time;

import java.time.LocalDate;

public final class TimeSerializer {

    public static Time deserialize(AttributeValue attr) {
        return Time.from(attr.getS());
    }

    public static AttributeValue serialize(Time time) {
        return new AttributeValue().withS(time.toString());
    }

}
