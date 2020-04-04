package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.dto.GetQuestionsByTagsRequest;
import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.FakeUserReader;
import pl.agasior.interviewprep.testutils.RequestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionReaderTest {
    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private DatabasePreparer databasePreparer;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private FakeUserReader fakeUserReader;

    private final MockMvc mockMvc;

    QuestionReaderTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    @AfterAll
    void clearDatabase() {
        databasePreparer.clear();
    }

    @Nested
    class ReadQuestion {

        @Test
        void findQuestionsByTags() throws Exception {
            final var user = UserDto.builder().username("admin").roles(Set.of(Role.Admin, Role.User)).build();
            fakeUserReader.provideUser(user);

            final var command = CreateQuestionRequest.builder()
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2")).build();

            final var command2 = CreateQuestionRequest.builder()
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2", "testTag3")).build();

            mockMvc.perform(requestFactory.createQuestion(command))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            mockMvc.perform(requestFactory.createQuestion(command2))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            questionRepository.findByTags(new GetQuestionsByTagsRequest(Collections.singletonList("testTag1")))
                            .forEach(quest -> Assertions.assertTrue(quest.getTags().contains("testTag1")));

            Assertions.assertEquals(2, questionRepository.findByTags(
                    new GetQuestionsByTagsRequest(Collections.singletonList("testTag1"))).size());

            Assertions.assertEquals(1, questionRepository.findByTags(
                    new GetQuestionsByTagsRequest(Arrays.asList("testTag1", "testTag3"))).size());
        }
    }

}
