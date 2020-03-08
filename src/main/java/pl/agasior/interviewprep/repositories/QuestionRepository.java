package pl.agasior.interviewprep.repositories;

import pl.agasior.interviewprep.entities.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);
    Question update(Question question);
    List<Question> findAll();
    Optional<Question> findById(String id);
}
