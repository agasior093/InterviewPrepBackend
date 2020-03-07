package pl.agasior.interviewprep.core.question.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.agasior.interviewprep.configuration.database.MongoCollections;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = MongoCollections.QUESTION)
@Getter
@Builder
class Question {
    @Id
    private String id;
    private final String content;
    private final String answer;
    private final LocalDateTime creationDate;
    private final String userId;
    private final String ratingId; //
    private final Set<String> tags;
}
