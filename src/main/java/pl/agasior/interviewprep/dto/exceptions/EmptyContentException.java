package pl.agasior.interviewprep.dto.exceptions;

public class EmptyContentException extends RuntimeException {
    public EmptyContentException() {
        super("Content must not be empty");
    }
}
