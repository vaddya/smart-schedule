package com.vaddya.schedule.dynamo.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.vaddya.schedule.core.tasks.Task;

public class TaskConverter implements DynamoDBTypeConverter<String, Task> {

    @Override
    public String convert(Task task) {
        return task.toString();
    }

    @Override
    public Task unconvert(String string) {
        return new Task.Builder().build();
    }
}
