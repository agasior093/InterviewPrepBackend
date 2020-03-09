package pl.agasior.interviewprep.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
public class Tag extends Identity{
    private final String value;
    private Integer occurrences;
}
