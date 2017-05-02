package com.vaddya.schedule.rest.responses;

import org.springframework.http.HttpStatus;

/**
 * Класс для представления HTTP ответа
 *
 * @author vaddya
 */
public class Response {

    private final int code;
    private final HttpStatus status;
    private final String message;

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
