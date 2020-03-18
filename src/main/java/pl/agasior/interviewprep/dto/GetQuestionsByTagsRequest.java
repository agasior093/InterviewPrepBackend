package pl.agasior.interviewprep.dto;

import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Value
public class GetQuestionsByTagsRequest {
    @NotNull(message = "There must be at least one tag to filter by")
    @Size(min = 1)
    List<@NotBlank String> tagsToFilterBy;
}
