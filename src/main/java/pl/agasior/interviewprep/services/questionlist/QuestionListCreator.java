package pl.agasior.interviewprep.services.questionlist;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.CreateQuestionListRequest;
import pl.agasior.interviewprep.dto.exceptions.QuestionNotFoundException;
import pl.agasior.interviewprep.dto.exceptions.UnauthorizedAccessException;
import pl.agasior.interviewprep.entities.QuestionList.QuestionList;
import pl.agasior.interviewprep.repositories.QuestionListRepository;
import pl.agasior.interviewprep.services.question.QuestionReader;
import pl.agasior.interviewprep.services.user.AuthenticationFacade;

@Service
public class QuestionListCreator {
    private final QuestionListRepository repository;
    private final AuthenticationFacade authenticationFacade;
    private final QuestionReader questionReader;

    public QuestionListCreator(QuestionListRepository repository, AuthenticationFacade authenticationFacade, QuestionReader questionReader) {
        this.repository = repository;
        this.authenticationFacade = authenticationFacade;
        this.questionReader = questionReader;
    }

    public QuestionList createQuestionList(CreateQuestionListRequest request) {
        final var user = authenticationFacade.getLoggedUser().orElseThrow(UnauthorizedAccessException::new);
        request.getQuestionIds().forEach(id -> questionReader.findById(id).orElseThrow(() -> new QuestionNotFoundException(id)));
        return repository.save(QuestionList.builder()
                .questionIds(request.getQuestionIds())
                .userId(user.getUsername())
                .build());
    }
}
