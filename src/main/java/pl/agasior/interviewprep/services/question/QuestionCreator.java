package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.services.tag.TagCreator;

import java.time.LocalDateTime;

@Service
public class QuestionCreator {
    private final QuestionRepository questionRepository;
    private final TagCreator tagCreator;

    QuestionCreator(QuestionRepository questionRepository, TagCreator tagCreator) {
        this.questionRepository = questionRepository;
        this.tagCreator = tagCreator;
    }

    public Question createQuestion(CreateQuestionRequest command) {
        final var question = buildQuestion(command);
        question.getTags().forEach(tagCreator::createIfAbsent);
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
