package pl.agasior.interviewprep.services.question;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

//TODO
public class QuestionUpdaterTest {

    @Nested
    class UpdateQuestion {

        @Test
        void modifyAnswer() {

        }

        @Test
        void modifyContent() {

        }

        @Test
        void modifyTags() {

        }
    }

    @Nested
    class ThrowValidationError {

        @Test
        void nullOrEmptyId() {

        }

        @Test
        void idNotFound() {

        }

        @Test
        void nullTags() {

        }

        @Test
        void emptyTags() {

        }

        @Test
        void tooManyTags() {

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