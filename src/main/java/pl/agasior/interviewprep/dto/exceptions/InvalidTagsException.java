package pl.agasior.interviewprep.dto.exceptions;

public class InvalidTagsException extends RuntimeException {
    public InvalidTagsException() {
        super("Question must contain at least 1 and at most 5 tags");
    }
}
