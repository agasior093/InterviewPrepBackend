package pl.agasior.interviewprep.testutils;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.entities.User;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class DatabasePreparer {
    private final MongoClient mongoClient;

    @Value("${mongodb.database}")
    private String databaseName;

    DatabasePreparer(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @PostConstruct
    void validateDatabase() {
        if (!databaseName.contains("test")) throw new IllegalArgumentException("Attempt to drop dev database!");
    }

    public void clear() {
        final var database = mongoClient.getDatabase(databaseName);
        for (String collectionName : database.listCollectionNames()) {
            database.getCollection(collectionName).drop();
        }
    }

}