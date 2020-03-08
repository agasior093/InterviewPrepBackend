package pl.agasior.interviewprep.repositories;

import pl.agasior.interviewprep.entities.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> findAll();

    Tag save(Tag tag);
}
