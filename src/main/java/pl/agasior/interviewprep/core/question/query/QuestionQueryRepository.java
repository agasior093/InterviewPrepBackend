package pl.agasior.interviewprep.core.question.query;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionQueryRepository {
    private final MongoTemplate mongoTemplate;

    QuestionQueryRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<QuestionDto> findAll() {
        return mongoTemplate.findAll(QuestionDto.class);
    }
}
