package com.vaddya.schedule.database;

import com.vaddya.schedule.core.lessons.ChangedLesson;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * com.vaddya.schedule.database at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public interface ChangeRepository {

    ChangedLesson findById(UUID id);

    List<ChangedLesson> findAll(LocalDate date);

    void insert(ChangedLesson lesson);

    void delete(ChangedLesson lesson);

    void deleteAll();

}