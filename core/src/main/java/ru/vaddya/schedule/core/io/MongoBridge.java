package ru.vaddya.schedule.core.io;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.utils.LessonType;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * Created by Vadim on 10/9/2016.
 */
public class MongoBridge {
    public static void main(String[] args) {
        MongoClient mongo = new MongoClient("localhost", 27017);
        System.out.println(mongo.getAddress());
        MongoDatabase database = mongo.getDatabase("Schedule");
        MongoCollection<Document> tasks = database.getCollection("tasks");
        tasks.drop();

        System.out.println(tasks.count());

        Task task = new Task.Builder()
                .subject("Программирование")
                .lessonType(LessonType.LAB)
                .deadline("31.12.2016")
                .textTask("Выполнить курсовую работу")
                .isComplete(false)
                .build();

        tasks.insertOne(createDocument(task));

        MongoCursor<Document> iterator = tasks.find().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toJson(new JsonWriterSettings(JsonMode.SHELL, true)));
        }

        System.out.println(tasks.count());
    }

    private static Document createDocument(Task task) {
        Document doc = new Document();
        doc.append("_id", task.getId().toString());
        doc.append("subject", task.getSubject());
        doc.append("lessonType", task.getLessonType().toString());
        doc.append("deadline", task.getDeadline());
        doc.append("textTask", task.getTextTask());
        doc.append("isComplete", task.isComplete());
        return doc;
    }
}
