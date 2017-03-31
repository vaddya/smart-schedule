package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.DayOfWeek;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * com.vaddya.schedule.database.mongo at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MongoLessonRepository implements LessonRepository {

    private final MongoCollection<Document> collection;

    public MongoLessonRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public Lesson findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        if (document != null) {
            return parseLesson(document);
        }
        return null;
    }

    @Override
    public List<Lesson> findAllByWeekAndDay(WeekType week, DayOfWeek day) {
        Bson filter = and(eq("week", week.toString()), eq("day", day.toString()));
        List<Document> documents = collection.find(filter).into(new ArrayList<>());
        List<Lesson> lessons = new ArrayList<>();
        documents.forEach(d -> lessons.add(parseLesson(d)));
        return lessons;
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> findAll(WeekType week) {
        Map<DayOfWeek, List<Lesson>> map = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, findAllByWeekAndDay(week, day));
        }
        return map;
    }

    @Override
    public void insert(WeekType week, DayOfWeek day, Lesson lesson) {
        Document document = fromLesson(lesson);
        document.append("week", week.toString()).append("day", day.toString());
        collection.insertOne(document);
    }

    @Override
    public void save(WeekType week, DayOfWeek day, Lesson lesson) {
        Document document = fromLesson(lesson);
        document.append("week", week.toString()).append("day", day.toString());
        collection.replaceOne(eq("_id", lesson.getId().toString()), document);
    }

    @Override
    public void delete(Lesson lesson) {
        collection.deleteOne(eq("_id", lesson.getId().toString()));
    }

    private Document fromLesson(Lesson lesson) {
        String json = new Gson().toJson(lesson);
        json = json.replace("id", "_id");
        return Document.parse(json);
    }

    private Lesson parseLesson(Document document) {
        document.put("id", document.remove("_id"));
        return new Gson().fromJson(document.toJson(), Lesson.class);
    }
}