package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pl.agasior.interviewprep.entities.User;

import java.util.Optional;

@Repository
public class UserRepository {
    private final MongoTemplate mongoTemplate;

    UserRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User save(User user) {
        return mongoTemplate.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(mongoTemplate.findOne(QueryFactory.usernameQuery(username), User.class));
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(mongoTemplate.findOne(QueryFactory.emailQuery(email), User.class));
    }
}

