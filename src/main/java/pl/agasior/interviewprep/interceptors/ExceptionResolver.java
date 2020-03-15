package pl.agasior.interviewprep.interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.agasior.interviewprep.dto.exceptions.*;

@ControllerAdvice
class ExceptionResolver {

    @ExceptionHandler(QuestionNotFoundException.class)
    ResponseEntity<String> handleNotFound(Exception exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            UserNameAlreadyExistsException.class
    })
    ResponseEntity<String> handleConflictState(Exception exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    ResponseEntity<String> handleBadRequest(Exception exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
