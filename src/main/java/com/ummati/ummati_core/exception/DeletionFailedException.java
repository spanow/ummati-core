package com.ummati.ummati_core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeletionFailedException extends RuntimeException {

    public DeletionFailedException(String message) {
        super(message);
    }
}
