package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.FakeUserReader;
import pl.agasior.interviewprep.testutils.RequestFactory;
import pl.agasior.interviewprep.testutils.ResponseParser;

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
        void findQuestionByTag() throws Exception {
            final var user = UserDto.builder().username("admin").roles(Set.of(Role.Admin, Role.User)).build();
            fakeUserReader.provideUser(user);

            Question question = Question.builder().content("testContent").answer("testAnswer").tags(Set.of("testTag1", "testTag2")).build();

            final var command = CreateQuestionRequest.builder()
                    .content(question.getContent())
                    .answer(question.getAnswer())
                    .tags(question.getTags()).build();

            final var result = mockMvc.perform(requestFactory.createQuestion(command))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            ResponseParser.getStringValue(result, "id")
                    .ifPresentOrElse(id -> questionRepository.findByTags(new GetQuestionsByTagsRequest(Collections.singletonList("testTag1")))
                                    .forEach(quest -> Assertions.assertEquals(quest, question)), () -> Assertions.fail("Question not found by tag"));
        }
    }

    @Nested
    class ThrowValidationError {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void nullOrEmptyContent(String content) throws Exception {
            final var command = CreateQuestionRequest.builder()
                    .content(content)
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2")).build();

            mockMvc.perform(requestFactory.createQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void nullOrEmptyAnswer(String answer) throws Exception {
            final var command = CreateQuestionRequest.builder()
                    .content("testContent")
                    .answer(answer)
                    .tags(Set.of("testTag1", "testTag2")).build();

            mockMvc.perform(requestFactory.createQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        void emptyTags() throws Exception {
            final var command = CreateQuestionRequest.builder()
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of()).build();

            mockMvc.perform(requestFactory.createQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        void nullTags() throws Exception {
            final var command = CreateQuestionRequest.builder()
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(null).build();

            mockMvc.perform(requestFactory.createQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        void tooManyTags() throws Exception {
            final var command = CreateQuestionRequest.builder()
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2", "testTag3", "testTag4", "testTag5", "testTag6")).build();

            mockMvc.perform(requestFactory.createQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

}
