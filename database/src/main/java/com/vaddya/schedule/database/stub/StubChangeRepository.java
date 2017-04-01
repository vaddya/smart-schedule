package com.vaddya.schedule.database.stub;

import com.vaddya.schedule.core.lessons.ChangedLesson;
import com.vaddya.schedule.database.ChangeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * com.vaddya.schedule.database.stub at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class StubChangeRepository implements ChangeRepository {

    @Override
    public ChangedLesson findById(UUID id) {
        return null;
    }

    @Override
    public List<ChangedLesson> findAll(LocalDate date) {
        return new ArrayList<>();
    }

    @Override
    public void insert(ChangedLesson lesson) {

    }

    @Override
    public void delete(ChangedLesson lesson) {

    }

    @Override
    public void deleteAll() {

    }
}
