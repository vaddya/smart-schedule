package com.vaddya.schedule.rest.controllers;

import com.google.gson.JsonSyntaxException;
import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * Контроллер для уроков
 *
 * @author vaddya
 */
@RestController
@RequestMapping(Paths.LESSONS)
public class LessonsController extends Controller {

    @GetMapping(value = "{id}", produces = JSON)
    public ResponseEntity<String> getLesson(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Lesson lesson = schedule.getLessons().findById(uuid);
            return getBodyResponse(OK, gson.toJson(lesson));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Lesson ID format is invalid");
        } catch (NoSuchLessonException e) {
            return getMessageResponse(NOT_FOUND, "Lesson does not exist");
        }
    }

    @PutMapping(value = "/{id}", consumes = JSON, produces = JSON)
    public ResponseEntity<String> updateTask(@PathVariable String id,
                                             @RequestBody String body) {
        try {
            Lesson lesson = gson.fromJson(body, Lesson.class);
            UUID pathId = UUID.fromString(id);
            if (!lesson.getId().equals(pathId)) {
                return getMessageResponse(BAD_REQUEST, "ID in path and in body do not match");
            }
            schedule.getLessons().updateLesson(lesson);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Lesson ID format is invalid");
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Lesson syntax is invalid");
        } catch (NoSuchLessonException e) {
            return getMessageResponse(NOT_FOUND, "Lesson does not exist");
        }
    }

    @DeleteMapping(value = "/{id}", produces = JSON)
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            schedule.getLessons().removeLesson(uuid);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Lesson ID format is invalid");
        } catch (NoSuchLessonException e) {
            return getMessageResponse(NOT_FOUND, "Lesson does not exist");
        }
    }

}