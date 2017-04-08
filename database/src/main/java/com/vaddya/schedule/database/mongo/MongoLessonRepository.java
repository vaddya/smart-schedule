package com.vaddya.schedule.database.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.vaddya.schedule.core.exceptions.DuplicateIdException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.database.LessonRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;
import static com.vaddya.schedule.core.utils.TypeOfWeek.*;
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
    private final Gson gson;

    public MongoLessonRepository(MongoCollection<Document> collection, Gson gson) {
        this.collection = collection;
        this.gson = gson;
    }

    @Override
    public Optional<Lesson> findById(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        return ofNullable(parseLesson(document));
    }

    @Override
    public List<Lesson> findAll(TypeOfWeek week, DayOfWeek day) {
        Bson filter = and(
                or(eq("week", week.toString()), eq("week", BOTH.toString())),
                eq("day", day.toString()));
        return collection.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(this::parseLesson)
                .collect(toList());
    }

    @Override
    public Optional<TypeOfWeek> findTypeOfWeek(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        try {
            return Optional.of(TypeOfWeek.valueOf(document.get("week").toString()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<DayOfWeek> findDayOfWeek(UUID id) {
        Document document = collection.find(eq("_id", id.toString())).first();
        try {
            return Optional.of(DayOfWeek.valueOf(document.get("day").toString()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public void insert(TypeOfWeek week, DayOfWeek day, Lesson lesson) {
        Document document = fromLesson(lesson);
        document.append("week", week.toString()).append("day", day.toString());
        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            throw new DuplicateIdException(lesson.getId());
        }
    }

    @Override
    public void save(Lesson lesson) {
        Document old = collection.find(eq("_id", lesson.getId().toString())).first();
        Document document = fromLesson(lesson);
        document.append("week", old.get("week")).append("day", old.get("day"));
        collection.replaceOne(eq("_id", lesson.getId().toString()), document);
    }

    @Override
    public void swapWeeks() {
        collection.updateMany(eq("week", ODD.toString()), set("week", ODD.toString()));
        collection.updateMany(eq("week", EVEN.toString()), set("week", EVEN.toString()));
        collection.updateMany(eq("week", "TEMP"), set("week", "EVEN"));
    }

    @Override
    public void delete(UUID id) {
        collection.deleteOne(eq("_id", id.toString()));
    }

    @Override
    public void deleteAll(TypeOfWeek week, DayOfWeek day) {
        if (week == BOTH) {
            deleteAll(ODD, day);
            deleteAll(EVEN, day);
            return;
        }
        collection.deleteMany(and(eq("week", week.toString()), eq("day", day.toString())));
    }

    @Override
    public void deleteAll(TypeOfWeek week) {
        if (week == BOTH) {
            deleteAll(ODD);
            deleteAll(EVEN);
            return;
        }
        collection.deleteMany(eq("week", week.toString()));
    }

    private Document fromLesson(Lesson lesson) {
        String json = gson.toJson(lesson);
        json = json.replaceFirst("id", "_id");
        return Document.parse(json);
    }

    private Lesson parseLesson(Document document) {
        if (document == null) {
            return null;
        }
        document.put("id", document.remove("_id"));
        return gson.fromJson(document.toJson(), Lesson.class);
    }

}