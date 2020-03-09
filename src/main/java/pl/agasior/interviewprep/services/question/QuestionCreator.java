package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.dto.CreateQuestionResult;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.time.LocalDateTime;

@Service
public class QuestionCreator {
    private final QuestionRepository questionRepository;
    private final QuestionValidator validator;

    QuestionCreator(final QuestionRepository questionRepository, final QuestionValidator validator) {
        this.questionRepository = questionRepository;
        this.validator = validator;
    }

    public CreateQuestionResult createQuestion(CreateQuestionCommand command) {
        validator.validateQuestionCreation(command);
        final var question = buildQuestion(command);
        final var savedQuestion = questionRepository.save(question);
        return new CreateQuestionResult(savedQuestion.getId());
    }

    private Question buildQuestion(final CreateQuestionCommand command) {
        return Question.builder()
                .answer(command.getAnswer())
                .content(command.getContent())
                .tags(command.getTags())
                .userId("admin") //need to get user from security context
                .creationDate(LocalDateTime.now())
                .build();
    }
}
