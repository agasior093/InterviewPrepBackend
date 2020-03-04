package pl.agasior.interviewprep.core.question.domain;

import pl.agasior.interviewprep.core.question.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.core.question.dto.CreateQuestionResult;

public class QuestionFacade {
    private final QuestionCreator creator;

    QuestionFacade(QuestionRepository repository) {
        this.creator = new QuestionCreator(repository);
    }

    public CreateQuestionResult createQuestion(CreateQuestionCommand command) {
        return creator.createQuestion(command);
    }
}
