package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.agasior.interviewprep.dto.UpdateQuestionCommand;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.util.Set;

public class QuestionUpdaterTest {
    private final QuestionRepository repository = new InMemoryQuestionRepository();
    private final QuestionUpdater questionUpdater = new QuestionUpdater(repository);

    @Nested
    class UpdateQuestion {

        @Test
        void validParamsAndQuestionExists() {
            final var question = repository.save(Question.builder()
                    .title("testTitle")
                    .content("testContent")
                    .answer("testAnswer")
                    .tags(Set.of("testTag"))
                    .build());
            final var command = UpdateQuestionCommand.builder()
                    .id(question.getId())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .answer("modifiedAnswer")
                    .tags(question.getTags())
                    .build();

            final var result = questionUpdater.updateQuestion(command);

        }
    }
}
