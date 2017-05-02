package com.vaddya.schedule.rest.responses;

import org.springframework.http.HttpStatus;

//TODO старый путь
/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class ResponseCreated extends Response {

    private final String url;

    public ResponseCreated(HttpStatus status, String message, String url) {
        super(status, message);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
