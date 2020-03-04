package pl.agasior.interviewprep.core.tag.domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class TagReader {
    private final TagRepository repository;

    TagReader(final TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getTagsByOccurrences() {
        return repository.findAll().stream().sorted(Comparator.comparingInt(Tag::getOccurrences).reversed()).collect(Collectors.toList());
    }
}
