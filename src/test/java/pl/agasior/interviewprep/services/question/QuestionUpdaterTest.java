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
import pl.agasior.interviewprep.dto.UpdateQuestionRequest;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.RequestFactory;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionUpdaterTest {

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private DatabasePreparer databasePreparer;

    @Autowired
    private QuestionRepository questionRepository;

    private final MockMvc mockMvc;

    QuestionUpdaterTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    @AfterAll
    void clearDatabase() {
        databasePreparer.clear();
    }

    private Question sampleQuestion() {
        return Question.builder()
                .content("testContent")
                .answer("testAnswer")
                .tags(Set.of("testTagA", "testTagB"))
                .userId("testUser")
                .creationDate(LocalDateTime.now())
                .build();
    }

    @Nested
    class UpdateQuestion {

        @Test
        void modifyAnswer() throws Exception {
            final var id = questionRepository.save(sampleQuestion()).getId();
            final var command = UpdateQuestionRequest.builder()
                    .id(id)
                    .answer("modifiedAnswer")
                    .build();

            mockMvc.perform(requestFactory.updateQuestion(command))
                    .andDo(print())
                    .andExpect(status().isOk());

            questionRepository.findById(id).ifPresentOrElse(question -> {
                Assertions.assertEquals(question.getAnswer(), command.getAnswer());
                Assertions.assertEquals(question.getContent(), sampleQuestion().getContent());
                Assertions.assertEquals(question.getTags(), sampleQuestion().getTags());
                Assertions.assertEquals(question.getUserId(), sampleQuestion().getUserId());
            }, () -> Assertions.fail("Question not found"));
        }

        @Test
        void modifyContent() throws Exception {
            final var id = questionRepository.save(sampleQuestion()).getId();
            final var command = UpdateQuestionRequest.builder()
                    .id(id)
                    .content("modifiedContent")
                    .build();

            mockMvc.perform(requestFactory.updateQuestion(command))
                    .andDo(print())
                    .andExpect(status().isOk());

            questionRepository.findById(id).ifPresentOrElse(question -> {
                Assertions.assertEquals(question.getAnswer(), sampleQuestion().getAnswer());
                Assertions.assertEquals(question.getContent(), command.getContent());
                Assertions.assertEquals(question.getTags(), sampleQuestion().getTags());
                Assertions.assertEquals(question.getUserId(), sampleQuestion().getUserId());
//                Assertions.assertEquals(question.getCreationDate(), sampleQuestion().getCreationDate()); TODO
            }, () -> Assertions.fail("Question not found"));
        }

        @Test
        void modifyTags() throws Exception {
            final var id = questionRepository.save(sampleQuestion()).getId();
            final var command = UpdateQuestionRequest.builder()
                    .id(id)
                    .tags(Set.of("modifiedTagA", "modifiedTagB", "modifiedTagC"))
                    .build();

            mockMvc.perform(requestFactory.updateQuestion(command))
                    .andDo(print())
                    .andExpect(status().isOk());

            questionRepository.findById(id).ifPresentOrElse(question -> {
                Assertions.assertEquals(question.getAnswer(), sampleQuestion().getAnswer());
                Assertions.assertEquals(question.getContent(), sampleQuestion().getContent());
                Assertions.assertEquals(question.getTags(), command.getTags());
                Assertions.assertEquals(question.getUserId(), sampleQuestion().getUserId());
//                Assertions.assertEquals(question.getCreationDate(), sampleQuestion().getCreationDate()); TODO
            }, () -> Assertions.fail("Question not found"));
        }
    }

    @Nested
    class ThrowValidationError {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void nullOrEmptyId(String id) throws Exception {
            final var command = UpdateQuestionRequest.builder()
                    .id(id)
                    .build();

            mockMvc.perform(requestFactory.updateQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        void idNotFound() throws Exception {
            final var id = "randomId";
            final var command = UpdateQuestionRequest.builder()
                    .id(id)
                    .build();

            final var mvcResult = mockMvc.perform(requestFactory.updateQuestion(command))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andReturn();
            Assertions.assertEquals("Question with id " + id + " does not exists", mvcResult.getResponse().getContentAsString());
        }

        @Test
        void emptyTags() throws Exception {
            final var command = UpdateQuestionRequest.builder()
                    .id("id")
                    .tags(Set.of())
                    .build();

            mockMvc.perform(requestFactory.updateQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        void tooManyTags() throws Exception {
            final var command = UpdateQuestionRequest.builder()
                    .id("id")
                    .tags(Set.of("1", "2", "3", "4", "5", "6"))
                    .build();

            mockMvc.perform(requestFactory.updateQuestion(command))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

}


//    @Test
//    void modifyContent() {
//        final var question = saveTestQuestion();
//        final var updateCommand = UpdateQuestionRequest.builder()
//                .id(question.getId())
//                .content("modifiedContent")
//                .build();
//
//        final var result = questionUpdater.updateQuestion(updateCommand);
//
//        repository.findById(result.getId()).ifPresentOrElse(updatedQuestion -> {
//            Assertions.assertEquals(updateCommand.getContent(), updatedQuestion.getContent());
//            Assertions.assertEquals(question.getAnswer(), updatedQuestion.getAnswer());
//            Assertions.assertEquals(question.getTags(), updatedQuestion.getTags());
//            Assertions.assertEquals(question.getUserId(), updatedQuestion.getUserId());
//            Assertions.assertEquals(question.getCreationDate(), updatedQuestion.getCreationDate());
//        }, Assertions::fail);
//    }
//
//    @Test
//    void modifyAnswer() {
//        final var question = saveTestQuestion();
//        final var updateCommand = UpdateQuestionRequest.builder()
//                .id(question.getId())
//                .answer("modifiedAnswer")
//                .build();
//
//        final var result = questionUpdater.updateQuestion(updateCommand);
//
//        repository.findById(result.getId()).ifPresentOrElse(updatedQuestion -> {
//            Assertions.assertEquals(updateCommand.getAnswer(), updatedQuestion.getAnswer());
//            Assertions.assertEquals(question.getContent(), updatedQuestion.getContent());
//            Assertions.assertEquals(question.getTags(), updatedQuestion.getTags());
//            Assertions.assertEquals(question.getUserId(), updatedQuestion.getUserId());
//            Assertions.assertEquals(question.getCreationDate(), updatedQuestion.getCreationDate());
//        }, Assertions::fail);
//    }
//
//    @Test
//    void modifyTags() {
//        final var question = saveTestQuestion();
//        final var updateCommand = UpdateQuestionRequest.builder()
//                .id(question.getId())
//                .tags(Set.of("modifiedTag"))
//                .build();
//
//        final var result = questionUpdater.updateQuestion(updateCommand);
//
//        repository.findById(result.getId()).ifPresentOrElse(updatedQuestion -> {
//            Assertions.assertEquals(updateCommand.getTags(), updatedQuestion.getTags());
//            Assertions.assertEquals(question.getContent(), updatedQuestion.getContent());
//            Assertions.assertEquals(question.getAnswer(), updatedQuestion.getAnswer());
//            Assertions.assertEquals(question.getUserId(), updatedQuestion.getUserId());
//            Assertions.assertEquals(question.getCreationDate(), updatedQuestion.getCreationDate());
//        }, Assertions::fail);
//    }
//
//
//    @Test
//    void throwOnUpdatingNotExistingQuestion() {
//        final var updateCommand = UpdateQuestionRequest.builder()
//                .id("notExistingTag")
//                .content("modifiedContent")
//                .answer("modifiedAnswer")
//                .tags(Set.of("modifiedTag"))
//                .build();
//
//        Assertions.assertThrows(QuestionNotFoundException.class, () -> questionUpdater.updateQuestion(updateCommand));
//    }
//
//
//    private Question saveTestQuestion() {
//        return repository.save(Question.builder()
//                .content("testContent")
//                .answer("testAnswer")
//                .tags(Set.of("testTag"))
//                .userId("testUser")
//                .creationDate(LocalDateTime.now())
//                .build());
//    }