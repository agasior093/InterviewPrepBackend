package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pl.agasior.interviewprep.entities.Tag;

import java.util.List;

@Repository
public class TagRepository {
    private final MongoTemplate mongoTemplate;

    TagRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Tag> findAll() {
        return mongoTemplate.findAll(Tag.class);
    }

    public Tag save(Tag tag) {
        return mongoTemplate.save(tag);
    }
}
