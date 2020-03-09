package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Builder
public class CreateQuestionRequest {

    @NotBlank(message = "Content must not be blank")
    private final String content;

    @NotBlank(message = "Answer must not be blank")
    private final String answer;

    @NotNull(message = "Tags may not be null")
    @Size(min = 1, max = 5, message = "Tags must contain at least 1 and at most 5 elements")
    private final Set<String> tags;

}
