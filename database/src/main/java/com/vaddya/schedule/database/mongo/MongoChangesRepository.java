package com.vaddya.schedule.database.mongo;

import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.core.lessons.ChangedLesson;
import com.vaddya.schedule.database.ChangeRepository;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * com.vaddya.schedule.database.mongo at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MongoChangesRepository implements ChangeRepository {

    private final MongoCollection<Document> collection;

    public MongoChangesRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public List<ChangedLesson> findByDate(LocalDate date) {
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
