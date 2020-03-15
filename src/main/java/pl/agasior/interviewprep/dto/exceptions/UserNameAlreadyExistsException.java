package pl.agasior.interviewprep.dto.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException() {
        super("Username already exists");
    }
}
