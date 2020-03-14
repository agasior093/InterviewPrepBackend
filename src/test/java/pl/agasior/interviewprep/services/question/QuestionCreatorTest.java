package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.MongoQuestionRepository;
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.RequestFactory;
import pl.agasior.interviewprep.testutils.ResponseParser;

import java.util.Set;
import java.util.function.Consumer;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class QuestionCreatorTest {

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private DatabasePreparer databasePreparer;

    @Autowired
    private MongoQuestionRepository questionRepository;

    private final MockMvc mockMvc;

    QuestionCreatorTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    void clearDatabase() {
        databasePreparer.clear();
    }

    @Test
    void createQuestion() throws Exception {
        final var command = CreateQuestionRequest.builder()
                .content("testContent")
                .answer("testAnswer")
                .tags(Set.of("testTag1", "testTag2")).build();

        final var result = mockMvc.perform(requestFactory.createQuestion(command))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseParser.getStringValue(result, "id")
                .ifPresentOrElse(id -> questionRepository.findById(id)
                                .ifPresentOrElse(doAssertions(command), () -> Assertions.fail("Question was not found in database")),
                        () -> Assertions.fail("ID parameter was not found in response body"));
    }

    private Consumer<Question> doAssertions(final CreateQuestionRequest command) {
        return question -> {
            Assertions.assertEquals(command.getAnswer(), question.getAnswer());
            Assertions.assertEquals(command.getContent(), question.getContent());
            Assertions.assertEquals(command.getTags(), question.getTags());
        };
    }
}
