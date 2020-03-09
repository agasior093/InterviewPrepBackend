package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UpdateQuestionCommand {
    private final String id;
    private final String content;
    private final String answer;
    private final Set<String> tags;
}
