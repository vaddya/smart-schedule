package com.vaddya.schedule.rest.responses;

import org.springframework.http.HttpStatus;

/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 06, 2017
 */
public class Response {

    private int code;
    private HttpStatus status;
    private String message;

    public Response(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}