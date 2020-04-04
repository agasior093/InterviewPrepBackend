package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Builder
public class CreateQuestionListRequest {
    @NotNull
    @Size(min = 1)
    private final Set<@NotBlank String> questionIds;
}

