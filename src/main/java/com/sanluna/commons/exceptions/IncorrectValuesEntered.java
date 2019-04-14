package com.sanluna.commons.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class IncorrectValuesEntered extends RuntimeException {

    public IncorrectValuesEntered(String message) {
        super(message);
    }
}
