package com.vaddya.schedule.dynamo;

import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DynamoTaskRepository implements TaskRepository {

    private final Table table;

    public DynamoTaskRepository(Table table) {
        this.table = table;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findAll() {
        table.scan();
        return null;
    }

    @Override
    public void insert(Task task) {

    }

    @Override
    public void save(Task task) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public void delete(Task task) {

    }

    @Override
    public void deleteAll() {

    }

}
