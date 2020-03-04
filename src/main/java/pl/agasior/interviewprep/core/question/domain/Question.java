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
    private String content;
    private String answer;
    private LocalDateTime creationDate;
    private String userId;
    private String ratingId;
    private Set<String> tags;
}
