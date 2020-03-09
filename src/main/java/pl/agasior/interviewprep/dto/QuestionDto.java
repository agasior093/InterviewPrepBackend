package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;
import pl.agasior.interviewprep.entities.Tag;

import java.util.Set;

@Getter
@Builder
public class QuestionDto {
    private final String id;
    private final String title;
    private final String content;
    private final String answer;
    private final String userId;
    private final Set<Tag> tags;
}
