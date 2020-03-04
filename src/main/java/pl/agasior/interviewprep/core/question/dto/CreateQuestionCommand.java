package pl.agasior.interviewprep.core.question.dto;

import lombok.Value;

import java.util.Set;

@Value
public class CreateQuestionCommand {
    private Set<String> tags;
    private String title;
    private String content;
    private String answer;
}
