package com.vaddya.schedule.rest.responses;

import org.springframework.http.HttpStatus;

/**
 * Класс для представления HTTP ответа о созданном ресурсе
 *
 * @author vaddya
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
