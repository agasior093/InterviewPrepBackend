package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.agasior.interviewprep.dto.UpdateQuestionCommand;
import pl.agasior.interviewprep.dto.exceptions.*;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.time.LocalDateTime;
import java.util.Set;

public class QuestionUpdaterTest {
    private final QuestionRepository repository = new InMemoryQuestionRepository();
    private final QuestionUpdater questionUpdater = new QuestionUpdater(repository, new QuestionValidator());

    @Nested
    class UpdateQuestion {

        @Test
        void modifyContent() {
            final var question = saveTestQuestion();
            final var updateCommand = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content("modifiedContent")
                    .answer(question.getAnswer())
                    .tags(question.getTags())
                    .build();

            final var result = questionUpdater.updateQuestion(updateCommand);

            repository.findById(result.getId()).ifPresentOrElse(updatedQuestion -> {
                Assertions.assertEquals(updateCommand.getContent(), updatedQuestion.getContent());
                Assertions.assertEquals(question.getAnswer(), updatedQuestion.getAnswer());
                Assertions.assertEquals(question.getTags(), updatedQuestion.getTags());
                Assertions.assertEquals(question.getUserId(), updatedQuestion.getUserId());
                Assertions.assertEquals(question.getCreationDate(), updatedQuestion.getCreationDate());
            }, Assertions::fail);
        }

        @Test
        void modifyAnswer() {
            final var question = saveTestQuestion();
            final var updateCommand = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content(question.getContent())
                    .answer("modifiedAnswer")
                    .tags(question.getTags())
                    .build();

            final var result = questionUpdater.updateQuestion(updateCommand);

            repository.findById(result.getId()).ifPresentOrElse(updatedQuestion -> {
                Assertions.assertEquals(updateCommand.getAnswer(), updatedQuestion.getAnswer());
                Assertions.assertEquals(question.getContent(), updatedQuestion.getContent());
                Assertions.assertEquals(question.getTags(), updatedQuestion.getTags());
                Assertions.assertEquals(question.getUserId(), updatedQuestion.getUserId());
                Assertions.assertEquals(question.getCreationDate(), updatedQuestion.getCreationDate());
            }, Assertions::fail);
        }

        @Test
        void modifyTags() {
            final var question = saveTestQuestion();
            final var updateCommand = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content(question.getContent())
                    .answer(question.getAnswer())
                    .tags(Set.of("modifiedTag"))
                    .build();

            final var result = questionUpdater.updateQuestion(updateCommand);

            repository.findById(result.getId()).ifPresentOrElse(updatedQuestion -> {
                Assertions.assertEquals(updateCommand.getTags(), updatedQuestion.getTags());
                Assertions.assertEquals(question.getContent(), updatedQuestion.getContent());
                Assertions.assertEquals(question.getAnswer(), updatedQuestion.getAnswer());
                Assertions.assertEquals(question.getUserId(), updatedQuestion.getUserId());
                Assertions.assertEquals(question.getCreationDate(), updatedQuestion.getCreationDate());
            }, Assertions::fail);
        }
    }

    @Nested
    class ThrowValidationException {

        @Test
        void updateNotExistingQuestion() {
            final var updateCommand = UpdateQuestionCommand.builder()
                    .id("notExistingTag")
                    .content("modifiedContent")
                    .answer("modifiedAnswer")
                    .tags(Set.of("modifiedTag"))
                    .build();

            Assertions.assertThrows(QuestionNotFoundException.class, () -> questionUpdater.updateQuestion(updateCommand));
        }

        @Test
        void nullId() {
            final var updateCommand = UpdateQuestionCommand.builder()
                    .content("modifiedContent")
                    .answer("modifiedAnswer")
                    .tags(Set.of("modifiedTag"))
                    .build();

            Assertions.assertThrows(MissingIdOnUpdateException.class, () -> questionUpdater.updateQuestion(updateCommand));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void emptyContent(String content) {
            final var question = saveTestQuestion();
            final var command = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content(content)
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2")).build();

            Assertions.assertThrows(EmptyContentException.class, () -> questionUpdater.updateQuestion(command));
        }


        @ParameterizedTest
        @ValueSource(strings = {"  ", "\t", "\n"})
        void emptyAnswer(String answer) {
            final var question = saveTestQuestion();
            final var command = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content("testContent")
                    .answer(answer)
                    .tags(Set.of("testTag1", "testTag2")).build();

            Assertions.assertThrows(EmptyAnswerException.class, () -> questionUpdater.updateQuestion(command));
        }


        @Test
        void emptyTags() {
            final var question = saveTestQuestion();
            final var command = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of()).build();

            Assertions.assertThrows(InvalidTagsException.class, () -> questionUpdater.updateQuestion(command));
        }

        @Test
        void nullTags() {
            final var question = saveTestQuestion();
            final var command = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(null).build();

            Assertions.assertThrows(InvalidTagsException.class, () -> questionUpdater.updateQuestion(command));
        }

        @Test
        void tooManyTags() {
            final var question = saveTestQuestion();
            final var command = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2", "testTag3", "testTag4", "testTag5", "testTag6")).build();

            Assertions.assertThrows(InvalidTagsException.class, () -> questionUpdater.updateQuestion(command));
        }
    }

    private Question saveTestQuestion() {
        return repository.save(Question.builder()
                .content("testContent")
                .answer("testAnswer")
                .tags(Set.of("testTag"))
                .userId("testUser")
                .creationDate(LocalDateTime.now())
                .build());
    }
}
