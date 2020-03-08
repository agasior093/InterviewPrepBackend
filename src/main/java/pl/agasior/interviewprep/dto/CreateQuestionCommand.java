package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CreateQuestionCommand {
    private final Set<String> tags;
    private final String title;
    private final String content;
    private final String answer;
}
