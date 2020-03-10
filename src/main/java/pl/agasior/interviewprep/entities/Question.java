package pl.agasior.interviewprep.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document
@Getter
public class Question extends Identity {
    private final String content;
    private final String answer;
    private final LocalDateTime creationDate;
    private final String userId;
    private final Set<String> tags;

    @Builder
    Question(final String id, final String content, final String answer, final LocalDateTime creationDate, final String userId, final Set<String> tags) {
        super(id);
        this.content = content;
        this.answer = answer;
        this.creationDate = creationDate;
        this.userId = userId;
        this.tags = tags;
    }
}
