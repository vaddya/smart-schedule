package com.vaddya.schedule.dynamo.serializers;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;

public interface DynamoSerializer<T> {

    Map<String, AttributeValue> serialize(T obj);

    T deserialize(Map<String, AttributeValue> attr);

}
