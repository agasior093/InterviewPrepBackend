package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;
import pl.agasior.interviewprep.entities.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class UpdateQuestionStatusRequest {
    @NotBlank(message = "ID must not be blank")
    private final String id;

    @NotNull
    private final Status status;
}
