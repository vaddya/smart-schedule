package com.vaddya.schedule.rest.controllers;

import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.schedule.ScheduleDay;
import com.vaddya.schedule.core.schedule.ScheduleWeek;
import com.vaddya.schedule.core.utils.LocalWeek;
import com.vaddya.schedule.rest.Paths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;

import static java.time.LocalDate.from;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * com.vaddya.schedule.rest.controllers at smart-schedule
 *
 * @author vaddya
 * @since April 07, 2017
 */
@RestController
@RequestMapping(Paths.SCHEDULE)
@CrossOrigin(origins = "*")
public class ScheduleController extends Controller {

    @RequestMapping(method = GET, produces = JSON)
    public ResponseEntity<String> getCurrentWeek() {
        return getBodyResponse(OK, gson.toJson(schedule.getCurrentWeek()));
    }

    @RequestMapping(value = "week/{date}", method = GET, produces = JSON)
    public ResponseEntity<String> getWeek(@PathVariable String date) {
        try {
            LocalDate localDate = from(DATE_FORMAT.parse(date));
            ScheduleWeek week = schedule.getWeek(LocalWeek.from(localDate));
            return getBodyResponse(OK, gson.toJson(week));
        } catch (DateTimeException e) {
            return getMessageResponse(BAD_REQUEST, "Date format is invalid");
        }
    }

    @RequestMapping(value = "{date}", method = GET, produces = JSON)
    public ResponseEntity<String> getDay(@PathVariable String date) {
        try {
            LocalDate localDate = from(DATE_FORMAT.parse(date));
            ScheduleDay day = schedule.getDay(localDate);
            return getBodyResponse(OK, gson.toJson(day));
        } catch (DateTimeException e) {
            return getMessageResponse(BAD_REQUEST, "Date is invalid");
        }
    }

    @RequestMapping(value = "{date}", method = POST, consumes = JSON, produces = JSON)
    public ResponseEntity<String> createLessonForDate(@PathVariable String date,
                                                      @RequestBody String body) {
        try {
            LocalDate localDate = from(DATE_FORMAT.parse(date));
            ScheduleDay day = schedule.getDay(localDate);
            Lesson lesson = gson.fromJson(body, Lesson.class);
            Change change = day.addLesson(lesson);
            return getResponseCreated(CREATED, "Change created", Paths.CHANGES, change.getId());
        } catch (DateTimeException e) {
            return getMessageResponse(BAD_REQUEST, "Date format is invalid");
        } catch (Throwable e) {
            return getMessageResponse(BAD_REQUEST, "Request is invalid");
        }
    }

    @RequestMapping(value = "{date}", method = DELETE, produces = JSON)
    public ResponseEntity<String> deleteLessonsForDate(@PathVariable String date) {
        try {
            LocalDate localDate = from(DATE_FORMAT.parse(date));
            ScheduleDay day = schedule.getDay(localDate);
            day.removeAllLessons();
            return getResponse(NO_CONTENT);
        } catch (DateTimeException e) {
            return getMessageResponse(BAD_REQUEST, "Date format is invalid");
        }
    }

}