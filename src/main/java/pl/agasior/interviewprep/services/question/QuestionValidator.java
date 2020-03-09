package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Component;
import pl.agasior.interviewprep.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.dto.UpdateQuestionCommand;
import pl.agasior.interviewprep.dto.exceptions.EmptyAnswerException;
import pl.agasior.interviewprep.dto.exceptions.EmptyContentException;
import pl.agasior.interviewprep.dto.exceptions.InvalidTagsException;
import pl.agasior.interviewprep.dto.exceptions.MissingIdOnUpdateException;

import java.util.Set;

//TODO - great place to use Vavr Validation
@Component
class QuestionValidator {
    private static final Integer MIN_TAGS_NUMBER = 1;
    private static final Integer MAX_TAGS_NUMBER = 5;

    void validateQuestionCreation(CreateQuestionCommand command) {
        validateContent(command.getContent());
        validateAnswer(command.getAnswer());
        validateTags(command.getTags());
    }

    void validateQuestionUpdate(UpdateQuestionCommand command) {
        validateId(command.getId());
        validateContent(command.getContent());
        validateAnswer(command.getAnswer());
        validateTags(command.getTags());
    }

    private void validateId(final String id) {
        if(id == null || id.isBlank()) throw new MissingIdOnUpdateException();
    }

    private void validateTags(final Set<String> tags) {
        if (tags == null || tags.size() < MIN_TAGS_NUMBER || tags.size() > MAX_TAGS_NUMBER)
            throw new InvalidTagsException();
    }

    private void validateAnswer(final String answer) {
        if (answer == null || answer.isBlank()) throw new EmptyAnswerException();
    }

    private void validateContent(final String content) {
        if (content == null || content.isBlank()) throw new EmptyContentException();
    }
}
