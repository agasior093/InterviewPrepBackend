package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pl.agasior.interviewprep.dto.GetQuestionsByTagsRequest;
import pl.agasior.interviewprep.entities.Question;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepository  {
    private final MongoTemplate mongoTemplate;

    QuestionRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Question save(final Question question) {
        return mongoTemplate.save(question);
    }

    public List<Question> findAll() {
        return mongoTemplate.findAll(Question.class);
    }

    public Optional<Question> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findOne(QueryFactory.idQuery(id), Question.class));
    }

    public List<Question> findByTags(GetQuestionsByTagsRequest request) {
        return mongoTemplate.find(QueryFactory.tagsToFilterBy(request.getTagsToFilterBy()), Question.class);
    }
}
