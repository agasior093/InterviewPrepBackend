package pl.agasior.interviewprep.entities.QuestionList;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Builder
@Getter
public class QuestionList {
    @Id
    private String id;
    private final String userId;
    private final Set<String> questionIds;
}
