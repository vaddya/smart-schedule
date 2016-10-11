package ru.vaddya.schedule.core.io;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.utils.DaysOfWeek;
import ru.vaddya.schedule.core.utils.LessonType;

import java.util.*;

import java.util.stream.Collectors;

/**
 * Created by Vadim on 10/9/2016.
 */
public class MongoBridge {
    public static void main(String[] args) {
        MongoClient mongo = new MongoClient("localhost", 27017);
        System.out.println(mongo.getAddress());
        MongoDatabase database = mongo.getDatabase("Schedule");
        //MongoCollection<Document> tasks = database.getCollection("Tasks");
        MongoCollection<Document> schedule = database.getCollection("Schedule");
        //tasks.drop();

        System.out.println(schedule.count());

//        Task task = new Task.Builder()
//                .subject("Высшая математика")
//                .lessonType(LessonType.PRACTICE)
//                .deadline("25.10.2016")
//                .textTask("№3, №4")
//                .isComplete(false)
//                .build();

        //tasks.insertOne(createDocument(task));

//        MongoCursor<Document> iterator1 = tasks.find().iterator();
//        while (iterator1.hasNext()) {
//            System.out.println(iterator1.next().toJson(new JsonWriterSettings(JsonMode.SHELL, true)));
//        }


        /*
        	"monday": [{
			"startTime": "14:00",
			"endTime": "15:30",
			"subject": "Программирование",
			"lessonType": "LAB",
			"place": "9 корпус, 309",
			"teacher": "Вылегжанина К.Д."
		}, {
			"startTime": "16:00",
			"endTime": "17:30",
			"subject": "Высшая математика",
			"lessonType": "LECTURE",
			"place": "ГЗ, 237",
			"teacher": "Панкрашова А.Г."
         */
        Lesson lesson1 = new Lesson.Builder()
                .startTime("10:00")
                .endTime("11:30")
                .subject("Программирование")
                .lessonType(LessonType.LECTURE)
                .teacher("Глухих М.И.")
                .build();

        Lesson lesson2 = new Lesson.Builder()
                .startTime("16:00")
                .endTime("17:30")
                .subject("Высшая математика")
                .lessonType(LessonType.LECTURE)
                .teacher("Панкрашова А.Г.")
                .build();

        Lesson lesson3 = new Lesson.Builder()
                .startTime("12:00")
                .endTime("13:30")
                .subject("Вычислительная математика")
                .lessonType(LessonType.PRACTICE)
                .teacher("Цыган В.Н.")
                .build();

        Lesson lesson4 = new Lesson.Builder()
                .startTime("14:00")
                .endTime("15:30")
                .subject("Вычислительная математика")
                .lessonType(LessonType.PRACTICE)
                .teacher("Вылегжанина К.Д.")
                .build();

        schedule.drop();
//        schedule.insertOne(createDocument(DaysOfWeek.TUESDAY, Arrays.asList(lesson2, lesson3)));

        Map<String, Object> map = new HashMap<>();
        for (DaysOfWeek day : DaysOfWeek.values()) {
            map.put(day.toString(), createObject(Arrays.asList(lesson1, lesson2)));
        }
        schedule.insertOne(new Document(map));

        MongoCursor<Document> iterator2 = schedule.find().iterator();
//        while (iterator2.hasNext()) {
//            System.out.println(iterator2.next().toJson(new JsonWriterSettings(JsonMode.SHELL, true)));
//        }
        System.out.println(schedule.find().first().get(DaysOfWeek.FRIDAY.toString()));

        System.out.println(schedule.count());
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

    private static Document createDocument(DaysOfWeek day, List<Lesson> lessons) {
        Document document = new Document();
        BasicDBList list = new BasicDBList();
        for (Lesson lesson : lessons) {
            list.add(createObject(lesson));
        }
        document.append(day.toString(), list);
        return document;
    }

    private static BasicDBObject createObject(DaysOfWeek day, List<Lesson> lessons) {
        BasicDBObject object = new BasicDBObject();
        BasicDBList list = lessons
                .stream()
                .map(MongoBridge::createObject)
                .collect(Collectors.toCollection(BasicDBList::new));
        object.append(day.toString(), list);
        return object;
    }

    private static BasicDBList createObject(List<Lesson> lessons) {
        BasicDBList list = lessons
                .stream()
                .map(MongoBridge::createObject)
                .collect(Collectors.toCollection(BasicDBList::new));
        return list;
    }

    private static DBObject createObject(Lesson lesson) {
        BasicDBObject object = new BasicDBObject();
        object.append("startTime", lesson.getStartTime().toString());
        object.append("endTime", lesson.getEndTime().toString());
        object.append("subject", lesson.getSubject());
        object.append("lessonType", lesson.getLessonType().toString());
        object.append("place", lesson.getPlace());
        object.append("teacher", lesson.getTeacher());
        return object;
    }
}
