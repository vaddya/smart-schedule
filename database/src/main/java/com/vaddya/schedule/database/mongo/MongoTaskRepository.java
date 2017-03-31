package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.TaskRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * com.vaddya.schedule.database at smart-schedule
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
    public Task findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        if (document != null) {
            return parseTask(document);
        }
        return null;
    }

    @Override
    public List<Task> findAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<Task> tasks = new ArrayList<>();
        documents.forEach(d -> tasks.add(parseTask(d)));
        return tasks;
    }

    @Override
    public void insert(Task task) {
        Document document = fromTask(task);
        collection.insertOne(document);
    }

    @Override
    public void save(Task task) {
        Document document = fromTask(task);
        collection.replaceOne(eq("_id", task.getId().toString()), document);
    }

    @Override
    public void delete(Task task) {
        collection.deleteOne(eq("_id", task.getId().toString()));
    }

    private Document fromTask(Task task) {
        String json = new Gson().toJson(task);
        json = json.replace("id", "_id");
        return Document.parse(json);
    }

    private Task parseTask(Document document) {
        document.put("id", document.remove("_id"));
        return new Gson().fromJson(document.toJson(), Task.class);
    }
}
