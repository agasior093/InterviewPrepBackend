package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Builder
public class UpdateQuestionRequest {
    @NotBlank(message = "ID must not be blank")
    private final String id;

    private final String content;
    private final String answer;

    @Size(min = 1, max = 5, message = "Tags must contain at least 1 and at most 5 elements")
    private final Set<String> tags;
}
