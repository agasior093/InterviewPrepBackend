package pl.agasior.interviewprep.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode
@Document
@Getter
@Builder
public class Question {
    private String id;
    private final String content;
    private final String answer;
    private final LocalDateTime creationDate;
    private final String userId;
    private final Set<String> tags;
    private final Status status;
    /**
     * id of users who encountered this question on their  interviews
     */
    private final Set<String> frequencyUserIds;
}
