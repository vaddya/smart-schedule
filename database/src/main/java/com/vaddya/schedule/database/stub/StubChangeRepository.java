package com.vaddya.schedule.database.stub;

import com.vaddya.schedule.core.lessons.ChangedLesson;
import com.vaddya.schedule.database.ChangeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * com.vaddya.schedule.database.stub at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class StubChangeRepository implements ChangeRepository {

    @Override
    public List<ChangedLesson> findByDate(LocalDate date) {
        return new ArrayList<>();
    }

    @Override
    public boolean insert(ChangedLesson lesson) {
        return false;
    }

    @Override
    public boolean delete(ChangedLesson lesson) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
