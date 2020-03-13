package pl.agasior.interviewprep.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.agasior.interviewprep.dto.exceptions.QuestionNotFoundException;

@ControllerAdvice
class ExceptionResolver {

    @ExceptionHandler(QuestionNotFoundException.class)
    ResponseEntity<String> questionNotFound(Exception exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
