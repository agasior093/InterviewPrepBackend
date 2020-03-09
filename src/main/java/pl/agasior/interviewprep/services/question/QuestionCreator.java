package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.time.LocalDateTime;

@Service
public class QuestionCreator {
    private final QuestionRepository questionRepository;

    QuestionCreator(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(CreateQuestionRequest command) {
        final var question = buildQuestion(command);
        return questionRepository.save(question);
    }

    private Question buildQuestion(final CreateQuestionRequest command) {
        return Question.builder()
                .answer(command.getAnswer())
                .content(command.getContent())
                .tags(command.getTags())
                .userId("admin") //need to get user from security context
                .creationDate(LocalDateTime.now())
                .build();
    }
}
