package pl.agasior.interviewprep.dto.exceptions;

public class PasswordDoesNotMatchException extends RuntimeException {
    public PasswordDoesNotMatchException() {
        super("Provided passwords are not the same");
    }
}
