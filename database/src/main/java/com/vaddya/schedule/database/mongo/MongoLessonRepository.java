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
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

/**
 * Хранилище уроков, хранящееся в MongoDB
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
    public Optional<Lesson> findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        return ofNullable(parseLesson(document));
    }

    @Override
    public List<Lesson> findAll(WeekType week, DayOfWeek day) {
        Bson filter = and(eq("week", week.toString()),
                eq("day", day.toString()));
        return collection.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(this::parseLesson)
                .collect(toList());
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> findAll(WeekType week) {
        Map<DayOfWeek, List<Lesson>> map = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, findAll(week, day));
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
    public void delete(WeekType week, DayOfWeek day, Lesson lesson) {
        collection.deleteOne(eq("_id", lesson.getId().toString()));
    }

    @Override
    public void deleteAll() {
        collection.drop();
    }

    private Document fromLesson(Lesson lesson) {
        String json = new Gson().toJson(lesson);
        json = json.replace("id", "_id");
        return Document.parse(json);
    }

    private Lesson parseLesson(Document document) {
        if (document == null) {
            return null;
        }
        document.put("id", document.remove("_id"));
        return new Gson().fromJson(document.toJson(), Lesson.class);
    }

}