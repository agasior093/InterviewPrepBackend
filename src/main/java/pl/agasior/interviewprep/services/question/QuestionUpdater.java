package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UpdateQuestionCommand;
import pl.agasior.interviewprep.dto.exceptions.QuestionNotFoundException;
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
        return questionRepository.findById(command.getId())
                .map(question -> update(question, command))
                .orElseThrow(() -> new QuestionNotFoundException(command.getId()));
    }

    private Question update(Question question, UpdateQuestionCommand updateCommand) {
        return questionRepository.save(Question.builder()
                .id(question.getId())
                .creationDate(question.getCreationDate())
                .userId(question.getUserId())
                .content(updateCommand.getContent())
                .answer(updateCommand.getAnswer())
                .tags(updateCommand.getTags())
                .build());
    }

}
