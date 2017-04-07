package com.vaddya.schedule.rest.controllers;

import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.utils.WeekTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static java.time.LocalDate.from;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * com.vaddya.schedule.rest.controllers at smart-schedule
 *
 * @author vaddya
 * @since April 07, 2017
 */
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController extends Controller {

    @RequestMapping(method = GET, produces = "application/json")
    public ResponseEntity<String> getWeek(@RequestParam(required = false) String startDate) {
        if (startDate != null) {
            try {
                LocalDate date = from(DATE_FORMAT.parse(startDate));
                StudyWeek week = schedule.getWeek(WeekTime.of(date));
                return getBodyResponse(OK, gson.toJson(week));
            } catch (Exception e) {
                return getMessageResponse(BAD_REQUEST, "Date format is invalid");
            }
        }
        return getBodyResponse(OK, gson.toJson(schedule.getCurrentWeek()));
    }

    @RequestMapping(value = "{date}", method = GET, produces = "application/json")
    public ResponseEntity<String> getDay(@PathVariable String date) {
        try {
            LocalDate localDate = from(DATE_FORMAT.parse(date));
            StudyDay day = schedule.getWeek(WeekTime.of(localDate)).getDay(localDate.getDayOfWeek());
            return getBodyResponse(OK, gson.toJson(day));
        } catch (Exception e) {
            return getMessageResponse(BAD_REQUEST, "Date format is invalid");
        }
    }

}