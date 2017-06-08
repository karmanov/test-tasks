package com.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UrlFormatException extends RuntimeException {

    public UrlFormatException(String url) {
        super("URL has not valid format: " + url);
    }
}
