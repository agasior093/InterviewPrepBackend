package pl.agasior.interviewprep.services.question;

import com.cloudinary.utils.StringUtils;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UpdateQuestionRequest;
import pl.agasior.interviewprep.dto.UpdateQuestionStatusRequest;
import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.dto.exceptions.DuplicatedQuestionFrequencyException;
import pl.agasior.interviewprep.dto.exceptions.QuestionNotFoundException;
import pl.agasior.interviewprep.dto.exceptions.UnauthorizedAccessException;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.services.tag.TagCreator;
import pl.agasior.interviewprep.services.user.AuthenticationFacade;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionUpdater {
    private final QuestionRepository questionRepository;
    private final TagCreator tagCreator;
    private final AuthenticationFacade authenticationFacade;

    QuestionUpdater(final QuestionRepository questionRepository, final TagCreator tagCreator, final AuthenticationFacade authenticationFacade) {
        this.questionRepository = questionRepository;
        this.tagCreator = tagCreator;
        this.authenticationFacade = authenticationFacade;
    }

    public Question updateQuestion(UpdateQuestionRequest request) {
        return questionRepository.findById(request.getId())
                .map(question -> update(question, request))
                .orElseThrow(() -> new QuestionNotFoundException(request.getId()));
    }

    public Question updateQuestion(UpdateQuestionStatusRequest request) {
        return questionRepository.findById(request.getId())
                .map(question -> update(question, request))
                .orElseThrow(() -> new QuestionNotFoundException(request.getId()));
    }

    public Question updateQuestion(String questionId) {
        final var user = authenticationFacade.getLoggedUser().orElseThrow(UnauthorizedAccessException::new);
        return questionRepository.findById(questionId)
                .map(question -> update(question, user))
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    private Question update(Question question, UpdateQuestionRequest updateRequest) {
        if (updateRequest.getTags() != null) updateRequest.getTags().forEach(tagCreator::createIfAbsent);
        return questionRepository.save(Question.builder()
                .id(question.getId())
                .creationDate(question.getCreationDate())
                .userId(question.getUserId())
                .content(StringUtils.isBlank(updateRequest.getContent()) ? question.getContent() : updateRequest.getContent())
                .answer(StringUtils.isBlank(updateRequest.getAnswer()) ? question.getAnswer() : updateRequest.getAnswer())
                .tags(updateRequest.getTags() == null ? question.getTags() : updateRequest.getTags())
                .build());
    }

    private Question update(Question question, UpdateQuestionStatusRequest updateRequest) {
        return questionRepository.save(Question.builder()
                .id(question.getId())
                .creationDate(question.getCreationDate())
                .userId(question.getUserId())
                .content(question.getContent())
                .answer(question.getAnswer())
                .tags(question.getTags())
                .status(updateRequest.getStatus())
                .build());
    }

    private Question update(Question question, UserDto user) {
        return questionRepository.save(Question.builder()
                .id(question.getId())
                .creationDate(question.getCreationDate())
                .userId(question.getUserId())
                .content(question.getContent())
                .answer(question.getAnswer())
                .tags(question.getTags())
                .status(question.getStatus())
                .frequencyUserIds(updateFrequency(question, user))
                .build());
    }

    private Set<String> updateFrequency(final Question question, final UserDto user) {
        final var currentFrequency = question.getFrequencyUserIds() != null ? question.getFrequencyUserIds() : new HashSet<String>();
        if (currentFrequency.contains(user.getUsername())) {
            throw new DuplicatedQuestionFrequencyException(user.getUsername());
        } else {
            currentFrequency.add(user.getUsername());
        }
        return currentFrequency;
    }
}
