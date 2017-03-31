package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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

    private static final String COLLECTION_NAME = "tasks";

    private final MongoCollection<Document> collection;

    public MongoTaskRepository(MongoDatabase database) {
        collection = database.getCollection(COLLECTION_NAME);
    }

    @Override
    public Task findTask(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        if (document != null) {
            return parseTask(document);
        }
        return null;
    }

    @Override
    public List<Task> findAllTasks() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<Task> tasks = new ArrayList<>();
        documents.forEach(d -> tasks.add(parseTask(d)));
        return tasks;
    }

    @Override
    public void addTask(Task task) {
        String json = new Gson().toJson(task);
        json = json.replace("id", "_id");
        Document document = Document.parse(json);
        collection.insertOne(document);
    }

    @Override
    public void updateTask(Task task) {
        String json = new Gson().toJson(task);
        json = json.replace("id", "_id");
        Document document = Document.parse(json);
        collection.replaceOne(eq("_id", task.getId().toString()), document);
    }

    @Override
    public void removeTask(Task task) {
        collection.deleteOne(eq("_id", task.getId().toString()));
    }

    private Task parseTask(Document document) {
        return new Gson().fromJson(document.toJson(), Task.class);
    }
}
