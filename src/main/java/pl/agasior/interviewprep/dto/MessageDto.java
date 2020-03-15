package pl.agasior.interviewprep.dto;

import lombok.Getter;

@Getter
public class MessageDto {
    private final String message;

    public MessageDto(final String message) {
        this.message = message;
    }
}
