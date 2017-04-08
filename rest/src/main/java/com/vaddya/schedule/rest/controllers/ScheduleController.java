package com.vaddya.schedule.rest.controllers;

import com.vaddya.schedule.core.lessons.Change;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.utils.WeekTime;
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
@RequestMapping("/api/schedule")
public class ScheduleController extends Controller {

    @RequestMapping(method = GET, produces = JSON)
    public ResponseEntity<String> getWeek(@RequestParam(required = false) String startDate) {
        if (startDate != null) {
            try {
                LocalDate date = from(DATE_FORMAT.parse(startDate));
                StudyWeek week = schedule.getWeek(WeekTime.of(date));
                return getBodyResponse(OK, gson.toJson(week));
            } catch (DateTimeException e) {
                return getMessageResponse(BAD_REQUEST, "Date format is invalid");
            }
        }
        return getBodyResponse(OK, gson.toJson(schedule.getCurrentWeek()));
    }

    @RequestMapping(value = "{date}", method = GET, produces = JSON)
    public ResponseEntity<String> getDay(@PathVariable String date) {
        try {
            LocalDate localDate = from(DATE_FORMAT.parse(date));
            StudyDay day = schedule.getDay(localDate);
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
            StudyDay day = schedule.getDay(localDate);
            Lesson lesson = gson.fromJson(body, Lesson.class);
            Change change = day.addLesson(lesson);
            return getMessageResponse(CREATED, "Lesson created, change id=" + change.getId());
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
            StudyDay day = schedule.getDay(localDate);
            day.removeAllLessons();
            return getResponse(NO_CONTENT);
        } catch (DateTimeException e) {
            return getMessageResponse(BAD_REQUEST, "Date format is invalid");
        }
    }

}