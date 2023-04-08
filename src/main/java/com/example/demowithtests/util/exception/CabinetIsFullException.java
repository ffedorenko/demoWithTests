package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class CabinetIsFullException extends RuntimeException {
    public CabinetIsFullException(String message) {
        super(message);
    }
}
