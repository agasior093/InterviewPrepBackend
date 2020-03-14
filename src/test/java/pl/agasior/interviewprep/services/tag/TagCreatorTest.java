package pl.agasior.interviewprep.services.tag;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.dto.UpdateQuestionRequest;
import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.repositories.TagRepository;
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.RequestFactory;
import pl.agasior.interviewprep.testutils.ResponseParser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagCreatorTest {

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private DatabasePreparer databasePreparer;

    @Autowired
    private TagRepository tagRepository;

    private final MockMvc mockMvc;

    TagCreatorTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    @AfterAll
    void clearDatabase() {
        databasePreparer.clear();
    }

    private static final String TAG_A_VALUE = "testTagA";
    private static final String TAG_B_VALUE = "testTagB";



    @Test
    void createNewWithUniqueValue() throws Exception {
        mockMvc.perform(requestFactory.createQuestion(createQuestionRequest(TAG_A_VALUE)))
                .andDo(print())
                .andExpect(status().isOk());

        final var savedTags = tagRepository.findAll();
        final var savedTag = savedTags.get(0);

        Assertions.assertEquals(1, savedTags.size());
        Assertions.assertEquals(TAG_A_VALUE, savedTag.getValue());
        Assertions.assertEquals(1, savedTag.getOccurrences());
    }

    @Test
    void incrementOccurrencesOfExisting() throws Exception {
        mockMvc.perform(requestFactory.createQuestion(createQuestionRequest(TAG_A_VALUE)))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(requestFactory.createQuestion(createQuestionRequest(TAG_A_VALUE)))
                .andDo(print())
                .andExpect(status().isOk());

        final var savedTags = tagRepository.findAll();

        Assertions.assertEquals(1, savedTags.size());
        savedTags.forEach(tag -> Assertions.assertEquals(2, tag.getOccurrences()));
    }

    @Test
    void incrementOccurrencesOfExistingAndCreateNewWithUniqueValue() throws Exception {
        final var mvcResult = mockMvc.perform(requestFactory.createQuestion(createQuestionRequest(TAG_A_VALUE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseParser.getStringValue(mvcResult, "id").ifPresentOrElse(id -> {
            try {
                mockMvc.perform(requestFactory.updateQuestion(updateQuestionRequest(id, Set.of(TAG_A_VALUE, TAG_B_VALUE))))
                        .andDo(print())
                        .andExpect(status().isOk());
            } catch (Exception e) {
                Assertions.fail(e);
            }
        }, () -> Assertions.fail("ID not found"));


        final var savedTags = tagRepository.findAll();

        Assertions.assertEquals(2, savedTags.size());
        getTagByValue(savedTags, TAG_A_VALUE).ifPresentOrElse(tag -> Assertions.assertEquals(2, tag.getOccurrences()), () -> Assertions.fail("Tag not found"));
        getTagByValue(savedTags, TAG_B_VALUE).ifPresentOrElse(tag -> Assertions.assertEquals(1, tag.getOccurrences()), () -> Assertions.fail("Tag not found"));
    }

    private Optional<Tag> getTagByValue(List<Tag> tags, String value) {
        return tags.stream().filter(tag -> value.equals(tag.getValue())).findFirst();
    }

    private CreateQuestionRequest createQuestionRequest(String... tagValues) {
        return CreateQuestionRequest.builder()
                .content("testContent")
                .answer("testAnswer")
                .tags(Set.of(tagValues)).build();
    }

    private UpdateQuestionRequest updateQuestionRequest(String id, Set<String> tags) {
        return UpdateQuestionRequest.builder()
                .id(id)
                .content("testContent")
                .answer("testAnswer")
                .tags(tags).build();
    }

}