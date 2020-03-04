package pl.agasior.interviewprep.core.tag.domain;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TagRepository {
    List<Tag> findAll();
    Tag save(Tag tag);
}
