package pl.agasior.interviewprep.services.question;

import com.cloudinary.utils.StringUtils;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UpdateQuestionRequest;
import pl.agasior.interviewprep.dto.exceptions.QuestionNotFoundException;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

@Service
public class QuestionUpdater {
    private final QuestionRepository questionRepository;

    QuestionUpdater(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question updateQuestion(UpdateQuestionRequest command) {
        return questionRepository.findById(command.getId())
                .map(question -> update(question, command))
                .orElseThrow(() -> new QuestionNotFoundException(command.getId()));
    }

    private Question update(Question question, UpdateQuestionRequest updateCommand) {
        return questionRepository.save(Question.builder()
                .id(question.getId())
                .creationDate(question.getCreationDate())
                .userId(question.getUserId())
                .content(StringUtils.isBlank(updateCommand.getContent()) ? question.getContent() : updateCommand.getContent())
                .answer(StringUtils.isBlank(updateCommand.getAnswer()) ? question.getAnswer() : updateCommand.getAnswer())
                .tags(updateCommand.getTags() == null ? question.getTags() : updateCommand.getTags())
                .build());
    }
}
