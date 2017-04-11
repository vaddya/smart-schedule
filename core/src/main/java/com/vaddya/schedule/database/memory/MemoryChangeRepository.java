package com.vaddya.schedule.database.memory;

import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.exceptions.NoSuchChangeException;
import com.vaddya.schedule.database.ChangeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * Хранилище изменений, хранящееся в памяти
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MemoryChangeRepository implements ChangeRepository {

    private List<Change> changes = new ArrayList<>();

    @Override
    public Optional<Change> findById(UUID id) {
        return changes.stream()
                .filter(change -> change.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Change> findAll() {
        return new ArrayList<>(changes);
    }

    @Override
    public List<Change> findAll(LocalDate date) {
        return changes.stream()
                .filter(change -> change.getDate().equals(date))
                .collect(toList());
    }

    @Override
    public void insert(Change change) {
        changes.add(change);
    }

    @Override
    public void save(Change change) {
        Optional<Change> optional = findById(change.getId());
        if (optional.isPresent()) {
            changes.remove(optional.get());
            changes.add(change);
        }
        throw new NoSuchChangeException(change.getId());
    }

    @Override
    public boolean isEmpty() {
        return changes.isEmpty();
    }

    @Override
    public long size() {
        return changes.size();
    }

    @Override
    public void delete(Change change) {
        changes.remove(change);
    }

    @Override
    public void deleteAll() {
        changes.clear();
    }

}