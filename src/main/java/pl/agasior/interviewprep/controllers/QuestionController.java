package pl.agasior.interviewprep.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agasior.interviewprep.dto.CreateQuestionRequest;
import pl.agasior.interviewprep.dto.GetQuestionsByTagsRequest;
import pl.agasior.interviewprep.dto.UpdateQuestionRequest;
import pl.agasior.interviewprep.dto.UpdateQuestionStatusRequest;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.services.question.QuestionCreator;
import pl.agasior.interviewprep.services.question.QuestionReader;
import pl.agasior.interviewprep.services.question.QuestionUpdater;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question")
class QuestionController {

    private final QuestionCreator questionCreator;
    private final QuestionUpdater questionUpdater;
    private final QuestionReader questionReader;

    QuestionController(final QuestionCreator questionCreator, final QuestionUpdater questionUpdater,
                       final QuestionReader questionReader) {
        this.questionCreator = questionCreator;
        this.questionUpdater = questionUpdater;
        this.questionReader = questionReader;
    }

    @PostMapping
    ResponseEntity<Question> createQuestion(@RequestBody @Valid CreateQuestionRequest request) {
        return ResponseEntity.ok(questionCreator.createQuestion(request));
    }

    @PatchMapping
    ResponseEntity<Question> updateQuestion(@RequestBody @Valid UpdateQuestionRequest request) {
        return ResponseEntity.ok(questionUpdater.updateQuestion(request));
    }

    @GetMapping
    ResponseEntity<List<Question>> queryQuestions() {
        return ResponseEntity.ok(questionReader.getAllQuestions());
    }

    @PatchMapping("/status")
    ResponseEntity<Question> updateQuestionStatus(@RequestBody @Valid UpdateQuestionStatusRequest request) {
        return ResponseEntity.ok(questionUpdater.updateQuestion(request));
    }

    @GetMapping("/getQuestionsByTags")
    ResponseEntity<List<Question>> getQuestionsByTags(@RequestBody @Valid GetQuestionsByTagsRequest request) {
        return ResponseEntity.ok(questionReader.getQuestionsByTags(request));
    }
}
