package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.core.lessons.Change;
import com.vaddya.schedule.database.ChangeRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static java.util.stream.Collectors.toList;

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
    public Optional<Change> findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        return Optional.ofNullable(parseChangedLesson(document));
    }

    @Override
    public List<Change> findAll(LocalDate date) {
        Bson filter = and(eq("date.year", date.getYear()),
                eq("date.month", date.getMonthValue()),
                eq("date.day", date.getDayOfMonth()));
        return collection.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(this::parseChangedLesson)
                .collect(toList());
    }

    @Override
    public void insert(Change lesson) {
        Document document = fromChangedLesson(lesson);
        collection.insertOne(document);
    }

    @Override
    public void delete(Change lesson) {
        collection.deleteOne(eq("_id", lesson.getId().toString()));
    }

    @Override
    public void deleteAll() {
        collection.drop();
    }

    @Override
    public boolean isEmpty() {
        return collection.count() != 0;
    }

    @Override
    public long size() {
        return collection.count();
    }

    private Document fromChangedLesson(Change lesson) {
        String json = new Gson().toJson(lesson);
        json = json.replaceFirst("id", "_id");
        return Document.parse(json);
    }

    private Change parseChangedLesson(Document document) {
        if (document == null) {
            return null;
        }
        document.put("id", document.remove("_id"));
        return new Gson().fromJson(document.toJson(), Change.class);
    }
}
