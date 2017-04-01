package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.core.lessons.ChangedLesson;
import com.vaddya.schedule.database.ChangeRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    public ChangedLesson findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        if (document != null) {
            return parseChangedLesson(document);
        }
        return null;
    }

    @Override
    public List<ChangedLesson> findAll(LocalDate date) {
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
    public void insert(ChangedLesson lesson) {
        Document document = fromChangedLesson(lesson);
        collection.insertOne(document);
    }

    @Override
    public void delete(ChangedLesson lesson) {
        collection.deleteOne(eq("_id", lesson.getId().toString()));
    }

    @Override
    public void deleteAll() {
        collection.drop();
    }

    private Document fromChangedLesson(ChangedLesson lesson) {
        String json = new Gson().toJson(lesson);
        json = json.replaceFirst("id", "_id");
        return Document.parse(json);
    }

    private ChangedLesson parseChangedLesson(Document document) {
        document.put("id", document.remove("_id"));
        return new Gson().fromJson(document.toJson(), ChangedLesson.class);
    }
}
