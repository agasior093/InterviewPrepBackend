package pl.agasior.interviewprep.dto.exceptions;

public class EmptyAnswerException extends RuntimeException {
    public EmptyAnswerException() {
        super("Answer must not be empty");
    }
}
