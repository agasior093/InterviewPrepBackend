package pl.agasior.interviewprep.services.tag;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.repositories.TagRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagReader {
    private final TagRepository repository;

    TagReader(final TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getTagsByOccurrences() {
        return repository.findAll().stream().sorted(Comparator.comparingInt(Tag::getOccurrences).reversed()).collect(Collectors.toList());
    }
}
