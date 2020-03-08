package pl.agasior.interviewprep.dto.exceptions;

public class EmptyTitleException extends RuntimeException {
    public EmptyTitleException() {
        super("Title must not be empty");
    }
}
