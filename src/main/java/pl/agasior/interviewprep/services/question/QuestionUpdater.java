package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UpdateQuestionCommand;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

@Service
public class QuestionUpdater {
    private final QuestionRepository questionRepository;

    public QuestionUpdater(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question updateQuestion(UpdateQuestionCommand command) {
        return null;
    }
}
