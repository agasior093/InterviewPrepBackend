package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pl.agasior.interviewprep.entities.Tag;

import java.util.List;

@Repository
class MongoTagRepository implements TagRepository {
    private final MongoTemplate mongoTemplate;

    MongoTagRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return mongoTemplate.findAll(Tag.class);
    }

    @Override
    public Tag save(Tag tag) {
        return mongoTemplate.save(tag);
    }
}
