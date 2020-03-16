package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.dto.exceptions.UnauthorizedAccessException;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.entities.Status;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.services.tag.TagCreator;
import pl.agasior.interviewprep.services.user.AuthenticationFacade;

import java.time.LocalDateTime;

@Service
public class QuestionCreator {
    private final QuestionRepository questionRepository;
    private final TagCreator tagCreator;
    private final AuthenticationFacade authenticationFacade;

    QuestionCreator(QuestionRepository questionRepository, TagCreator tagCreator, AuthenticationFacade authenticationFacade) {
        this.questionRepository = questionRepository;
        this.tagCreator = tagCreator;
        this.authenticationFacade = authenticationFacade;
    }

    public Question createQuestion(CreateQuestionRequest command) {
        final var question = buildQuestion(command);
        question.getTags().forEach(tagCreator::createIfAbsent);
        return questionRepository.save(question);
    }

    private Question buildQuestion(final CreateQuestionRequest command) {
        final var user = authenticationFacade.getLoggedUser().orElseThrow(UnauthorizedAccessException::new);
        return Question.builder()
                .answer(command.getAnswer())
                .content(command.getContent())
                .tags(command.getTags())
                .userId(user.getUsername())
                .creationDate(LocalDateTime.now())
                .status(determineStatus(user))
                .build();
    }

    private Status determineStatus(UserDto userDto) {
        return userDto.getRoles().contains(Role.Admin) ? Status.Approved : Status.Pending;
    }
}
