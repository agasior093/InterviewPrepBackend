package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.FakeUserReader;
import pl.agasior.interviewprep.testutils.RequestFactory;

import java.util.Set;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private QuestionReader questionReader;

    @Autowired
    private QuestionCreator questionCreator;

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
    void findQuestionsByTagsE2E() throws Exception {
        questionCreator.createQuestion(getCreateQuestionRequest(Set.of(TEST_TAG_1, TEST_TAG_2)));
        questionCreator.createQuestion(getCreateQuestionRequest(Set.of(TEST_TAG_1, TEST_TAG_2, TEST_TAG_3)));

        GetQuestionsByTagsRequest getQuestionsByTagsRequest = GetQuestionsByTagsRequest.builder().tagsToFilterBy(Set.of(TEST_TAG_1)).build();

        mockMvc.perform(requestFactory.GetQuestionsByTags(getQuestionsByTagsRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[:1].tags[*]").value(hasItem(TEST_TAG_1)));
    }

    @Test
    void findQuestionsByTagsE2EWithUniqueTag() throws Exception {
        questionCreator.createQuestion(getCreateQuestionRequest(Set.of(TEST_TAG_1, TEST_TAG_2)));
        questionCreator.createQuestion(getCreateQuestionRequest(Set.of(TEST_TAG_1, TEST_TAG_2, TEST_TAG_3)));

        GetQuestionsByTagsRequest getQuestionsByTagsRequest = GetQuestionsByTagsRequest.builder().tagsToFilterBy(Set.of(TEST_TAG_1, TEST_TAG_3)).build();

        mockMvc.perform(requestFactory.GetQuestionsByTags(getQuestionsByTagsRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[:1].tags[*]").value(hasItem(TEST_TAG_3)));
    }

    private CreateQuestionRequest getCreateQuestionRequest(Set<String> testSet) {
        return CreateQuestionRequest.builder()
                .content(TEST_CONTENT)
                .answer(TEST_ANSWER)
                .tags(testSet).build();
    }

}
