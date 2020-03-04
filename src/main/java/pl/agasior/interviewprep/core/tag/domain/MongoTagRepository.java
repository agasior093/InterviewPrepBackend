package pl.agasior.interviewprep.core.tag.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class MongoTagRepository implements TagRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Tag> findAll() {
        return mongoTemplate.findAll(Tag.class);
    }

    @Override
    public Tag save(Tag tag) {
        return mongoTemplate.save(tag);
    }
}
