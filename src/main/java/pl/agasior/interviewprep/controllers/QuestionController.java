package pl.agasior.interviewprep.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agasior.interviewprep.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.dto.CreateQuestionResult;
import pl.agasior.interviewprep.dto.QuestionDto;
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

    QuestionController(final QuestionCreator questionCreator, final QuestionUpdater questionUpdater, final QuestionReader questionReader) {
        this.questionCreator = questionCreator;
        this.questionUpdater = questionUpdater;
        this.questionReader = questionReader;
    }

    @PostMapping
    ResponseEntity<CreateQuestionResult> createQuestion(@RequestBody @Valid CreateQuestionCommand command) {
        return ResponseEntity.ok(questionCreator.createQuestion(command));
    }

    @GetMapping("/query")
    ResponseEntity<List<QuestionDto>> queryQuestions() {
        return ResponseEntity.ok(questionReader.getAllQuestions());
    }
}
