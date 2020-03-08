package pl.agasior.interviewprep.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.agasior.interviewprep.dto.TagDto;

@Document
@Data
@Builder
public class Tag {
    @Id
    private String id;
    private final String value;
    private Integer occurrences;

    TagDto toDto() {
        return new TagDto(value);
    }

    static Tag fromDto(TagDto dto) {
        return Tag.builder().value(dto.getValue()).build();
    }
}
