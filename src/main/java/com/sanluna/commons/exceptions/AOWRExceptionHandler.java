package com.sanluna.commons.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AOWRExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({AlreadyExistsException.class,
            DataIntegrityViolationException.class})
    public void springHandleAlreadyExist(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), "The object already exists");
    }

    @ExceptionHandler({IncorrectValuesEntered.class})
    public void springHandleIncorerectValues(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "You have entered invalid values");
    }

    @ExceptionHandler({Exception.class})
    public void springHandleTheRest(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong!");
    }

}
