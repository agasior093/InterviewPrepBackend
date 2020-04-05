package pl.agasior.interviewprep.dto.exceptions;

public class DuplicatedQuestionFrequencyException extends RuntimeException {
    public DuplicatedQuestionFrequencyException(String userId) {
        super(userId + " is already assigned to this question frequency");
    }
}
