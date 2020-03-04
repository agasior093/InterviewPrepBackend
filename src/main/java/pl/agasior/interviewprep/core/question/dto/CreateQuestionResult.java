package pl.agasior.interviewprep.core.question.dto;

import lombok.Getter;

@Getter
public class CreateQuestionResult {

    private String questionId;

    public CreateQuestionResult(String questionId) {
        this.questionId = questionId;
    }
}
