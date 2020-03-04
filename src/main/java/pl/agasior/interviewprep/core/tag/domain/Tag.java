package pl.agasior.interviewprep.core.tag.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.agasior.interviewprep.core.tag.dto.TagDto;

@Document
@Data
@Builder
class Tag {
    @Id
    private String id;
    private String value;
    private Integer occurrences;

    TagDto toDto() {
        return new TagDto(value);
    }

    static Tag fromDto(TagDto dto) {
        return Tag.builder().value(dto.getValue()).build();
    }
}
