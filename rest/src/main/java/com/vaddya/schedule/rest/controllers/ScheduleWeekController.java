package com.vaddya.schedule.rest.controllers;

import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.utils.WeekType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "swap", method = POST, produces = "application/json")
    public ResponseEntity<String> swapSchedules() {
        schedule.swapSchedules();
        return getResponse(NO_CONTENT);
    }

    @RequestMapping(value = "{weekType}", method = GET, produces = "application/json")
    public ResponseEntity<String> getWeek(@PathVariable String weekType) {
        try {
            WeekType type = WeekType.valueOf(weekType.toUpperCase());
            StudySchedule studySchedule = schedule.getSchedule(type);
            return getBodyResponse(OK, gson.toJson(studySchedule));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Week type is invalid");
        }
    }

    @RequestMapping(value = "{weekType}", method = DELETE, produces = "application/json")
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

}