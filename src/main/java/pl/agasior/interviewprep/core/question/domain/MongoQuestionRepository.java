package pl.agasior.interviewprep.core.question.domain;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

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
    public Optional<Question> find(final String id) {
        return Optional.empty();
    }

    @Override
    public Collection<Question> findAll() {
        return mongoTemplate.findAll(Question.class);
    }
}
