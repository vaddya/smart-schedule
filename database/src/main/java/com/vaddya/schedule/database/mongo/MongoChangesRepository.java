package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.exceptions.NoSuchChangeException;
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
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

/**
 * Хранилище изменений, хранящееся в MongoDB
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MongoChangesRepository implements ChangeRepository {

    private final MongoCollection<Document> collection;
    private Gson gson;

    public MongoChangesRepository(MongoCollection<Document> collection, Gson gson) {
        this.collection = collection;
        this.gson = gson;
    }

    @Override
    public Optional<Change> findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        return ofNullable(parseChange(document));
    }

    @Override
    public List<Change> findAll() {
        return collection.find()
                .into(new ArrayList<>())
                .stream()
                .map(this::parseChange)
                .collect(toList());
    }

    @Override
    public List<Change> findAll(LocalDate date) {
        Bson filter = and(eq("date.year", date.getYear()),
                eq("date.month", date.getMonthValue()),
                eq("date.day", date.getDayOfMonth()));
        return collection.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(this::parseChange)
                .collect(toList());
    }

    @Override
    public void insert(Change change) {
        Bson filter = eq("lesson.id", change.getLesson().getId().toString());
        Document document = fromChange(change);
        Document existed = collection.find(filter).first();
        if (existed != null) {
            collection.deleteOne(filter);
        }
        collection.insertOne(document);
    }

    @Override
    public void save(Change change) {
        Document document = fromChange(change);
        UpdateResult result = collection.replaceOne(eq("_id", change.getId().toString()), document);
        if (result.getModifiedCount() == 0) {
            throw new NoSuchChangeException(change.getId());
        }
    }

    @Override
    public void delete(Change change) {
        collection.deleteOne(eq("_id", change.getId().toString()));
    }

    @Override
    public void deleteAll() {
        collection.drop();
    }

    @Override
    public boolean isEmpty() {
        return collection.count() == 0;
    }

    @Override
    public long size() {
        return collection.count();
    }

    private Document fromChange(Change change) {
        String json = gson.toJson(change);
        json = json.replaceFirst("id", "_id");
        return Document.parse(json);
    }

    private Change parseChange(Document document) {
        if (document == null) {
            return null;
        }
        document.put("id", document.remove("_id"));
        return gson.fromJson(document.toJson(), Change.class);
    }

}