package com.vaddya.schedule.rest.controllers;

import com.google.gson.Gson;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.rest.responses.Response;
import com.vaddya.schedule.rest.responses.ResponseCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Класс, содержащий общие для контроллеров поля и методы
 *
 * @author vaddya
 */
public class Controller {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final String JSON = MediaType.APPLICATION_JSON_VALUE;

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

    protected ResponseEntity<String> getResponseCreated(HttpStatus status, String message, String baseUrl, UUID id) {
        ResponseCreated response = new ResponseCreated(status, message, baseUrl + "/" + id);
        return new ResponseEntity<>(gson.toJson(response), response.getStatus());
    }

}