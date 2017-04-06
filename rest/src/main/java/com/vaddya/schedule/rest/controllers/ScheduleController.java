package com.vaddya.schedule.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return getBodyResponse(OK, gson.toJson(schedule.getCurrentWeek().getAllDays()));
    }

}