package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UpdateQuestionCommand;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

@Service
public class QuestionUpdater {
    private final QuestionRepository questionRepository;
    private final QuestionValidator validator;

    QuestionUpdater(final QuestionRepository questionRepository, final QuestionValidator validator) {
        this.questionRepository = questionRepository;
        this.validator = validator;
    }

    public Question updateQuestion(UpdateQuestionCommand command) {
        validator.validateQuestionUpdate(command);
        final var question = Question.builder()
                .id(command.getId())
                .content(command.getContent())
                .answer(command.getAnswer())
                .tags(command.getTags())
                .build();
        return questionRepository.save(question);
    }

}
