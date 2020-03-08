package pl.agasior.interviewprep.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document
@Getter
@Builder
public class Question {
    @Id
    private String id;
    private final String title;
    private final String content;
    private final String answer;
    private final LocalDateTime creationDate;
    private final String userId;
    private final String ratingId;
    private final Set<String> tags;
}
