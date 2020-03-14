package pl.agasior.interviewprep.dto.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String id) {
        super("Question with id " + id + " does not exists");
    }
}
