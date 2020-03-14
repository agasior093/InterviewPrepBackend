package pl.agasior.interviewprep.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@EqualsAndHashCode
@Getter
@Builder
public class Tag {
    @Id
    private String id;
    private final String value;
    private final Integer occurrences;
}
