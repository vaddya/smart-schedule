package com.vaddya.schedule.rest.controllers;

import com.vaddya.schedule.core.exceptions.DuplicateIdException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.rest.Paths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * com.vaddya.schedule.rest.controllers at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
@RestController
@RequestMapping(Paths.WEEKS)
@CrossOrigin(origins = "*")
public class WeeksController extends Controller {

    @RequestMapping(value = "swap", method = POST, produces = JSON)
    public ResponseEntity<String> swapSchedules() {
        schedule.swapTypesOfWeeks();
        return getResponse(NO_CONTENT);
    }

    @RequestMapping(value = "{weekType}", method = GET, produces = JSON)
    public ResponseEntity<String> getWeek(@PathVariable String weekType) {
        try {
            TypeOfWeek week = TypeOfWeek.valueOf(weekType.toUpperCase());
            StudyWeek studySchedule = schedule.getLessons().findAll(week);
            return getBodyResponse(OK, gson.toJson(studySchedule));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type is invalid");
        }
    }

    @RequestMapping(value = "{weekType}", method = DELETE, produces = JSON)
    public ResponseEntity<String> deleteLessonsForWeek(@PathVariable String weekType) {
        try {
            TypeOfWeek week = TypeOfWeek.valueOf(weekType.toUpperCase());
            schedule.getLessons().removeAllLessons(week);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type is invalid");
        }
    }

    @RequestMapping(value = "{weekType}/{dayOfWeek}", method = GET, produces = JSON)
    public ResponseEntity<String> getDay(@PathVariable String weekType,
                                         @PathVariable String dayOfWeek) {
        try {
            TypeOfWeek week = TypeOfWeek.valueOf(weekType.toUpperCase());
            DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            List<Lesson> lessons = schedule.getLessons().findAll(week).get(day);
            return getBodyResponse(OK, gson.toJson(lessons));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type or day from week is invalid");
        }
    }

    @RequestMapping(value = "{weekType}/{dayOfWeek}", method = POST, consumes = JSON, produces = JSON)
    public ResponseEntity<String> getDay(@PathVariable String weekType,
                                         @PathVariable String dayOfWeek,
                                         @RequestBody String body) {
        try {
            TypeOfWeek week = TypeOfWeek.valueOf(weekType.toUpperCase());
            DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            Lesson lesson = gson.fromJson(body, Lesson.class);
            schedule.getLessons().addLesson(week, day, lesson);
            return getResponseCreated(CREATED, "Lesson created", Paths.LESSONS, lesson.getId());
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type or day from week is invalid");
        } catch (DuplicateIdException e) {
            return getMessageResponse(CONFLICT, "Lesson with specified ID already exists");
        } catch (Throwable e) {
            return getMessageResponse(BAD_REQUEST, "Request is invalid");
        }
    }

    @RequestMapping(value = "{weekType}/{dayOfWeek}", method = DELETE, produces = JSON)
    public ResponseEntity<String> deleteLessonsForDay(@PathVariable String weekType,
                                                      @PathVariable String dayOfWeek) {
        try {
            TypeOfWeek week = TypeOfWeek.valueOf(weekType.toUpperCase());
            DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            schedule.getLessons().removeAllLessons(week, day);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type is invalid");
        }
    }

}