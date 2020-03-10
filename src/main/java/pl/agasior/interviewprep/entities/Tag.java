package pl.agasior.interviewprep.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document
@Getter
public class Tag extends Identity {
    private final String value;
    private final Integer occurrences;

    @Builder
    Tag(final String id, final String value, final Integer occurrences) {
        super(id);
        this.value = value;
        this.occurrences = occurrences;
    }
}
