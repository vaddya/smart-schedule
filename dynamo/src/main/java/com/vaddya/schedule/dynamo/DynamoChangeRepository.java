package com.vaddya.schedule.dynamo;

import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.database.ChangeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DynamoChangeRepository implements ChangeRepository {

    @Override
    public Optional<Change> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Change> findAll() {
        return null;
    }

    @Override
    public List<Change> findAll(LocalDate date) {
        return null;
    }

    @Override
    public void insert(Change change) {

    }

    @Override
    public void save(Change change) {

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
    public void delete(Change change) {

    }

    @Override
    public void deleteAll() {

    }
}
