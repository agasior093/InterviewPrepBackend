package pl.agasior.interviewprep.services.tag;

import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.repositories.TagRepository;

import java.util.function.Supplier;

public class TagCreator {
    private final TagRepository repository;

    TagCreator(final TagRepository repository) {
        this.repository = repository;
    }

    public Tag createIfAbsent(Tag tag) {
        var existingTag = repository.findAll().stream().filter(t -> t.getValue().equals(tag.getValue())).findFirst();
        return existingTag.map(this::incrementOccurrences).orElseGet(save(tag));
    }

    private Supplier<Tag> save(Tag tag) {
        tag.setOccurrences(1);
        return () -> repository.save(tag);
    }

    private Tag incrementOccurrences(Tag tag) {
        var currentValue = tag.getOccurrences() != null ? tag.getOccurrences() : 1;
        tag.setOccurrences(++currentValue);
        return repository.save(tag);
    }
}