package pl.agasior.interviewprep.core.question.domain;

import pl.agasior.interviewprep.core.question.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.core.question.dto.CreateQuestionResult;

import java.time.LocalDateTime;

class QuestionCreator {
    private final QuestionRepository questionRepository;

    QuestionCreator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    CreateQuestionResult createQuestion(CreateQuestionCommand command) {
        final var question = buildQuestion(command);
        final var savedQuestion = questionRepository.save(question);
        return new CreateQuestionResult(question.getId());
    }

    private Question buildQuestion(final CreateQuestionCommand command) {
        return Question.builder()
                    .answer(command.getTitle())
                    .answer(command.getAnswer())
                    .content(command.getContent())
                    .tags(command.getTags())
                    .userId("admin")
                    .creationDate(LocalDateTime.now())
                    .build();
    }
}
