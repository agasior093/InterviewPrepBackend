package pl.agasior.interviewprep.services.question;

import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.services.InMemoryRepository;

import java.util.UUID;

public class InMemoryQuestionRepository extends InMemoryRepository<Question> implements QuestionRepository {
    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
