package pl.agasior.interviewprep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Value
@Builder
public class GetQuestionsByTagsRequest {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @NotNull(message = "There must be at least one tag to filter by")
    @Size(min = 1)
    Set<@NotBlank String> tagsToFilterBy;
}
