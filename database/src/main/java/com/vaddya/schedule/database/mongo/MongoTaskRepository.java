package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.TaskRepository;
import com.vaddya.schedule.database.exception.DuplicateIdException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

/**
 * Хранилище заданий, хранящееся в MongoDB
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MongoTaskRepository implements TaskRepository {

    private final MongoCollection<Document> collection;

    public MongoTaskRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        return ofNullable(parseTask(document));
    }

    @Override
    public List<Task> findAll() {
        return collection.find()
                .into(new ArrayList<>())
                .stream()
                .map(this::parseTask)
                .collect(toList());
    }

    @Override
    public void insert(Task task) {
        Document document = fromTask(task);
        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            throw new DuplicateIdException("Duplicate ID: " + task.getId());
        }
    }

    @Override
    public void save(Task task) {
        Document document = fromTask(task);
        UpdateResult result = collection.replaceOne(eq("_id", task.getId().toString()), document);
        if (result.getModifiedCount() == 0) {
            throw new NoSuchTaskException("No task with ID: " + task.getId());
        }
    }

    @Override
    public void delete(Task task) {
        DeleteResult result = collection.deleteOne(eq("_id", task.getId().toString()));
        if (result.getDeletedCount() == 0) {
            throw new NoSuchTaskException("No task with ID: " + task.getId());
        }
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

    private Document fromTask(Task task) {
        String json = new Gson().toJson(task);
        json = json.replace("id", "_id");
        return Document.parse(json);
    }

    private Task parseTask(Document document) {
        if (document == null) {
            return null;
        }
        document.put("id", document.remove("_id"));
        return new Gson().fromJson(document.toJson(), Task.class);
    }

}