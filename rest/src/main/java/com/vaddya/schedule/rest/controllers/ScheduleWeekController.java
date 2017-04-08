package com.vaddya.schedule.rest.controllers;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.exception.DuplicateIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/schedule/weeks")
public class ScheduleWeekController extends Controller {

    @RequestMapping(value = "swap", method = POST, produces = JSON)
    public ResponseEntity<String> swapSchedules() {
        schedule.swapSchedules();
        return getResponse(NO_CONTENT);
    }

    @RequestMapping(value = "{weekType}", method = GET, produces = JSON)
    public ResponseEntity<String> getWeek(@PathVariable String weekType) {
        try {
            WeekType type = WeekType.valueOf(weekType.toUpperCase());
            StudySchedule studySchedule = schedule.getSchedule(type);
            return getBodyResponse(OK, gson.toJson(studySchedule));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type is invalid");
        }
    }

    @RequestMapping(value = "{weekType}", method = DELETE, produces = JSON)
    public ResponseEntity<String> deleteLessonsForWeek(@PathVariable String weekType) {
        try {
            WeekType type = WeekType.valueOf(weekType.toUpperCase());
            StudySchedule studySchedule = schedule.getSchedule(type);
            studySchedule.removeAllLessons();
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type is invalid");
        }
    }

    @RequestMapping(value = "{weekType}/{dayOfWeek}", method = GET, produces = JSON)
    public ResponseEntity<String> getDay(@PathVariable String weekType,
                                         @PathVariable String dayOfWeek) {
        try {
            WeekType type = WeekType.valueOf(weekType.toUpperCase());
            DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            List<Lesson> lessons = schedule.getSchedule(type).getLessons(day);
            return getBodyResponse(OK, gson.toJson(lessons));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type or day of week is invalid");
        }
    }

    @RequestMapping(value = "{weekType}/{dayOfWeek}", method = POST, produces = JSON)
    public ResponseEntity<String> getDay(@PathVariable String weekType,
                                         @PathVariable String dayOfWeek,
                                         @RequestBody String body) {
        try {
            WeekType type = WeekType.valueOf(weekType.toUpperCase());
            DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            Lesson lesson = gson.fromJson(body, Lesson.class);
            schedule.getSchedule(type).addLesson(day, lesson);
            return getMessageResponse(CREATED, "Lesson created, id=" + lesson.getId());
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type or day of week is invalid");
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
            WeekType type = WeekType.valueOf(weekType.toUpperCase());
            DayOfWeek day = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            schedule.getSchedule(type).removeAllLessons(day);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type is invalid");
        }
    }

}