package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pl.agasior.interviewprep.entities.Question;

import java.util.List;

@Repository
class MongoQuestionRepository implements QuestionRepository {
    private final MongoTemplate mongoTemplate;

    MongoQuestionRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Question save(final Question question) {
        return mongoTemplate.save(question);
    }

    @Override
    public Question update(final Question question) {
        return null;
    }

    @Override
    public List<Question> findAll() {
        return mongoTemplate.findAll(Question.class);
    }
}
