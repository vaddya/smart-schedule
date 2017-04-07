package com.vaddya.schedule.rest.controllers;

import com.google.gson.Gson;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.rest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * com.vaddya.schedule.rest.controllers at smart-schedule
 *
 * @author vaddya
 * @since April 07, 2017
 */
public class Controller {

    public static final DateTimeFormatter DATE_FORMAT = ofPattern("dd-MM-yyyy");

    @Autowired
    protected Gson gson;

    @Autowired
    protected SmartSchedule schedule;

    protected ResponseEntity<String> getResponse(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    protected ResponseEntity<String> getBodyResponse(HttpStatus status, String body) {
        return new ResponseEntity<>(body, status);
    }

    protected ResponseEntity<String> getMessageResponse(HttpStatus status, String message) {
        Response response = new Response(status, message);
        return new ResponseEntity<>(gson.toJson(response), response.getStatus());
    }
}
