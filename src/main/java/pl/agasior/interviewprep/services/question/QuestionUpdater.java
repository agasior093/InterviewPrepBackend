package pl.agasior.interviewprep.services.question;

import com.cloudinary.utils.StringUtils;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UpdateQuestionRequest;
import pl.agasior.interviewprep.dto.exceptions.QuestionNotFoundException;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.services.tag.TagCreator;

@Service
public class QuestionUpdater {
    private final QuestionRepository questionRepository;
    private final TagCreator tagCreator;

    QuestionUpdater(final QuestionRepository questionRepository, final TagCreator tagCreator) {
        this.questionRepository = questionRepository;
        this.tagCreator = tagCreator;
    }

    public Question updateQuestion(UpdateQuestionRequest command) {
        return questionRepository.findById(command.getId())
                .map(question -> update(question, command))
                .orElseThrow(() -> new QuestionNotFoundException(command.getId()));
    }

    private Question update(Question question, UpdateQuestionRequest updateCommand) {
        if(updateCommand.getTags() != null) updateCommand.getTags().forEach(tagCreator::createIfAbsent);
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
