package pl.agasior.interviewprep.core.tag.domain;

import pl.agasior.interviewprep.core.tag.dto.TagDto;

import java.util.List;
import java.util.stream.Collectors;

public class TagFacade {
    private final TagCreator creator;
    private final TagReader reader;

    TagFacade(TagRepository repository) {
        this.creator = new TagCreator(repository);
        this.reader = new TagReader(repository);
    }

    public List<TagDto> findTagsByOccurrences() {
        return reader.getTagsByOccurrences().stream().map(Tag::toDto).collect(Collectors.toList());
    }

    public TagDto createIfAbsent(TagDto dto) {
        var tag = creator.createIfAbsent(Tag.fromDto(dto));
        return tag.toDto();
    }
}

