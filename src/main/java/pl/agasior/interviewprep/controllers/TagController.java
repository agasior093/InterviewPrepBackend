package pl.agasior.interviewprep.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.services.tag.TagReader;

import java.util.List;

@RestController
@RequestMapping("/tag")
class TagController {
    private final TagReader tagReader;

    TagController(TagReader tagReader) {
        this.tagReader = tagReader;
    }

    @GetMapping
    ResponseEntity<List<Tag>> getTags() {
        return ResponseEntity.ok(tagReader.getTagsByOccurrences());
    }
}
