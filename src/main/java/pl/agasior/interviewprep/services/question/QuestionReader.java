package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.GetQuestionsByTagsRequest;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionReader {

    private final QuestionRepository questionRepository;

    QuestionReader(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByTags(GetQuestionsByTagsRequest request) {
        return questionRepository.findByTags(request);
    }

    public Optional<Question> findById(String id) {
        return questionRepository.findById(id);
    }
}
