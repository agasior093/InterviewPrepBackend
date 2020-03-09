package pl.agasior.interviewprep.services.tag;

import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.repositories.TagRepository;
import pl.agasior.interviewprep.services.InMemoryRepository;

import java.util.UUID;

public class InMemoryTagRepository extends InMemoryRepository<Tag> implements TagRepository {
    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
