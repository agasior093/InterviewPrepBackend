package pl.agasior.interviewprep.services.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.repositories.TagRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TagReaderTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagReader tagReader;

    @Test
    void displayTagsInDescendingOccurrenceOrder() {
        final var lowestOccurrences = Tag.builder().value("testTagA").occurrences(4).build();
        final var midOccurrences = Tag.builder().value("testTagB").occurrences(8).build();
        final var highestOccurrences = Tag.builder().value("testTagC").occurrences(12).build();
        Mockito.when(tagRepository.findAll()).thenReturn(List.of(midOccurrences, highestOccurrences, lowestOccurrences));

        final var result = tagReader.getTagsByOccurrences();

        Assertions.assertEquals(result.get(0), highestOccurrences);
        Assertions.assertEquals(result.get(1), midOccurrences);
        Assertions.assertEquals(result.get(2), lowestOccurrences);
    }
}
