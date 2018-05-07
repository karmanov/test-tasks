package com.cakes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown if requested cake not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CakeNotFoundException extends RuntimeException {
}
