package pl.agasior.interviewprep.dto.exceptions;

public class AuthenticationException extends RuntimeException {
    private final String message;

    public AuthenticationException(Exception exception) {
        this.message = exception.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
