package pl.agasior.interviewprep.services.tag;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.repositories.TagRepository;

import java.util.function.Supplier;

@Service
public class TagCreator {
    private final TagRepository repository;

    public TagCreator(final TagRepository repository) {
        this.repository = repository;
    }

    public Tag createIfAbsent(String tagValue) {
        var existingTag = repository.findAll().stream().filter(t -> t.getValue().equals(tagValue)).findFirst();
        return existingTag.map(this::incrementOccurrences).orElseGet(save(tagValue));
    }

    private Supplier<Tag> save(String tagValue) {
        final var tag = Tag.builder().value(tagValue).occurrences(1).build();
        return () -> repository.save(tag);
    }

    private Tag incrementOccurrences(Tag tag) {
        final var updatedTag = Tag.builder().value(tag.getValue()).occurrences(tag.getOccurrences() + 1).build();
        return repository.save(updatedTag);
    }
}
