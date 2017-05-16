package io.karmanov.watermark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WatermarkingAlreadyInProgressException extends RuntimeException {

    public WatermarkingAlreadyInProgressException(String ticketId) {
        super("Watermarking of requested document is already in progress with ticket id: " + ticketId);
    }
}
