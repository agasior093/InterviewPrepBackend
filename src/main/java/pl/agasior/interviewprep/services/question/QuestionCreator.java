package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.dto.CreateQuestionResult;
import pl.agasior.interviewprep.dto.exceptions.EmptyAnswerException;
import pl.agasior.interviewprep.dto.exceptions.EmptyContentException;
import pl.agasior.interviewprep.dto.exceptions.EmptyTitleException;
import pl.agasior.interviewprep.dto.exceptions.InvalidTagsException;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.time.LocalDateTime;

@Service
public class QuestionCreator {
    private static final Integer MIN_TAGS_NUMBER = 1;
    private static final Integer MAX_TAGS_NUMBER = 5;

    private final QuestionRepository questionRepository;

    QuestionCreator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public CreateQuestionResult createQuestion(CreateQuestionCommand command) {
        validate(command);
        final var question = buildQuestion(command);
        final var savedQuestion = questionRepository.save(question);
        return new CreateQuestionResult(savedQuestion.getId());
    }

    private Question buildQuestion(final CreateQuestionCommand command) {
        return Question.builder()
                .title(command.getTitle())
                .answer(command.getAnswer())
                .content(command.getContent())
                .tags(command.getTags())
                .userId("admin") //need to get user from security context
                .creationDate(LocalDateTime.now())
                .build();
    }

    private void validate(CreateQuestionCommand command) {
        if (command.getTitle() == null || command.getTitle().isBlank()) throw new EmptyTitleException();
        if (command.getContent() == null || command.getContent().isBlank()) throw new EmptyContentException();
        if (command.getAnswer() == null || command.getAnswer().isBlank()) throw new EmptyAnswerException();
        if (command.getTags() == null || command.getTags().size() < MIN_TAGS_NUMBER || command.getTags().size() > MAX_TAGS_NUMBER)
            throw new InvalidTagsException();
    }
}
