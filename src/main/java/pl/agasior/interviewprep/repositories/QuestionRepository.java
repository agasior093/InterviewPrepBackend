package pl.agasior.interviewprep.repositories;

import pl.agasior.interviewprep.entities.Question;

import java.util.List;

public interface QuestionRepository {
    Question save(Question question);
    Question update(Question question);
    List<Question> findAll();
}
