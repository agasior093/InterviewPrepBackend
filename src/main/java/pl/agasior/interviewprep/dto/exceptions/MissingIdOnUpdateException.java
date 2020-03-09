package pl.agasior.interviewprep.dto.exceptions;

public class MissingIdOnUpdateException extends RuntimeException {
    public MissingIdOnUpdateException() {
        super("QuestionId is required for update operation");
    }
}
