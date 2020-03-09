package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.util.Set;

public class QuestionCreatorTest {
    private final QuestionRepository questionRepository = new InMemoryQuestionRepository();
    private final QuestionCreator questionCreator = new QuestionCreator(questionRepository);

    @Test
    void createQuestionWithValidFields() {
        final var command = CreateQuestionRequest.builder()
                .content("testContent")
                .answer("testAnswer")
                .tags(Set.of("testTag1", "testTag2")).build();

        final var result = questionCreator.createQuestion(command);

        questionRepository.findById(result.getId())
                .ifPresentOrElse(question -> {
                    Assertions.assertEquals(command.getContent(), question.getContent());
                    Assertions.assertEquals(command.getAnswer(), question.getAnswer());
                    Assertions.assertEquals(command.getTags(), question.getTags());
                }, Assertions::fail);
    }
}
