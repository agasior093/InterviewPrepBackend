package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.agasior.interviewprep.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.dto.exceptions.EmptyAnswerException;
import pl.agasior.interviewprep.dto.exceptions.EmptyContentException;
import pl.agasior.interviewprep.dto.exceptions.EmptyTitleException;
import pl.agasior.interviewprep.dto.exceptions.InvalidTagsException;
import pl.agasior.interviewprep.repositories.QuestionRepository;
import pl.agasior.interviewprep.repositories.TagRepository;
import pl.agasior.interviewprep.services.tag.InMemoryTagRepository;
import pl.agasior.interviewprep.services.tag.TagCreator;

import java.util.Set;

public class QuestionCreatorTest {
    private final QuestionRepository questionRepository = new InMemoryQuestionRepository();
    private final TagRepository tagRepository = new InMemoryTagRepository();
    private final TagCreator tagCreator = new TagCreator(tagRepository);
    private final QuestionCreator questionCreator = new QuestionCreator(questionRepository, tagCreator);

    @Nested
    class CreateQuestion {
        @Test
        void validFields() {
            final var command = CreateQuestionCommand.builder()
                    .title("testTitle")
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2")).build();

            final var result = questionCreator.createQuestion(command);

            questionRepository.findById(result.getQuestionId())
                    .ifPresentOrElse(question -> {
                        Assertions.assertEquals(command.getTitle(), question.getTitle());
                        Assertions.assertEquals(command.getContent(), question.getContent());
                        Assertions.assertEquals(command.getAnswer(), question.getAnswer());
                        Assertions.assertEquals(command.getTags(), question.getTags());
                    }, Assertions::fail);
        }
    }

    @Nested
    class ValidationException {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void emptyTitle(String title) {
            final var command = CreateQuestionCommand.builder()
                    .title(title)
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2")).build();

            Assertions.assertThrows(EmptyTitleException.class, () -> questionCreator.createQuestion(command));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void emptyContent(String content) {
            final var command = CreateQuestionCommand.builder()
                    .title("testTitle")
                    .content(content)
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2")).build();

            Assertions.assertThrows(EmptyContentException.class, () -> questionCreator.createQuestion(command));
        }


        @ParameterizedTest
        @ValueSource(strings = {"  ", "\t", "\n"})
        void emptyAnswer(String answer) {
            final var command = CreateQuestionCommand.builder()
                    .title("testTitle")
                    .content("testContent")
                    .answer(answer)
                    .tags(Set.of("testTag1", "testTag2")).build();

            Assertions.assertThrows(EmptyAnswerException.class, () -> questionCreator.createQuestion(command));
        }


        @Test
        void emptyTags() {
            final var command = CreateQuestionCommand.builder()
                    .title("testTitle")
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of()).build();

            Assertions.assertThrows(InvalidTagsException.class, () -> questionCreator.createQuestion(command));
        }

        @Test
        void nullTags() {
            final var command = CreateQuestionCommand.builder()
                    .title("testTitle")
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(null).build();

            Assertions.assertThrows(InvalidTagsException.class, () -> questionCreator.createQuestion(command));
        }

        @Test
        void tooManyTags() {
            final var command = CreateQuestionCommand.builder()
                    .title("testTitle")
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag1", "testTag2", "testTag3", "testTag4", "testTag5", "testTag6")).build();

            Assertions.assertThrows(InvalidTagsException.class, () -> questionCreator.createQuestion(command));
        }
    }

}
