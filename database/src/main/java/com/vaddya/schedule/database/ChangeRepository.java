package com.vaddya.schedule.database;

import com.vaddya.schedule.core.changes.Change;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * com.vaddya.schedule.database at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public interface ChangeRepository {

    Optional<Change> findById(UUID id);

    List<Change> findAll();

    List<Change> findAll(LocalDate date);

    void insert(Change change);

    void save(Change change);

    boolean isEmpty();

    long size();

    void delete(Change change);

    void deleteAll();

}