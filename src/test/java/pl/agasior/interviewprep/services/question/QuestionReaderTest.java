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
import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.FakeUserReader;
import pl.agasior.interviewprep.testutils.RequestFactory;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionReaderTest {

    public static final String TEST_CONTENT = "testContent";
    public static final String TEST_ANSWER = "testAnswer";
    public static final String TEST_TAG_1 = "testTag1";
    public static final String TEST_TAG_2 = "testTag2";
    public static final String TEST_TAG_3 = "testTag3";

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

    @BeforeEach
    void createUser() {
        final var user = UserDto.builder().username("admin").roles(Set.of(Role.Admin, Role.User)).build();
        fakeUserReader.provideUser(user);
    }

    @Test
    void findQuestionsByTags() throws Exception {
        mockMvc.perform(requestFactory.createQuestion(getCreateQuestionRequest(Set.of(TEST_TAG_1, TEST_TAG_2))))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(requestFactory.createQuestion(getCreateQuestionRequest(Set.of(TEST_TAG_1, TEST_TAG_2, TEST_TAG_3))))
                .andDo(print())
                .andExpect(status().isOk());

        questionRepository.findByTags(Set.of(TEST_TAG_1))
                .forEach(question -> Assertions.assertTrue(question.getTags().contains(TEST_TAG_1)));

        Assertions.assertEquals(2, questionRepository.findByTags(Set.of(TEST_TAG_1)).size());

        Assertions.assertEquals(1, questionRepository.findByTags(Set.of(TEST_TAG_1, TEST_TAG_3)).size());
    }

    private CreateQuestionRequest getCreateQuestionRequest(Set<String> testSet) {
        return CreateQuestionRequest.builder()
                .content(TEST_CONTENT)
                .answer(TEST_ANSWER)
                .tags(testSet).build();
    }

}
