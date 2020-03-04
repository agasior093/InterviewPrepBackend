package pl.agasior.interviewprep.core.question.query;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.agasior.interviewprep.configuration.database.MongoCollections;

@Getter
@Builder
@Document(collection = MongoCollections.QUESTION)
public class QuestionDto {
    private String id;
    private String title;
    private String content;
    private String answer;
    private String userId;
}
