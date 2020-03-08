package pl.agasior.interviewprep.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agasior.interviewprep.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.dto.CreateQuestionResult;
import pl.agasior.interviewprep.dto.QuestionDto;
import pl.agasior.interviewprep.dto.TagDto;
import pl.agasior.interviewprep.entities.Tag;
import pl.agasior.interviewprep.services.question.QuestionCreator;
import pl.agasior.interviewprep.services.question.QuestionReader;
import pl.agasior.interviewprep.services.question.QuestionUpdater;
import pl.agasior.interviewprep.services.tag.TagCreator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question")
class QuestionController {

    private final QuestionCreator questionCreator;
    private final QuestionUpdater questionUpdater;
    private final QuestionReader questionReader;
    private final TagCreator tagCreator;

    QuestionController(final QuestionCreator questionCreator, final QuestionUpdater questionUpdater,
                       final QuestionReader questionReader, final TagCreator tagCreator) {
        this.questionCreator = questionCreator;
        this.questionUpdater = questionUpdater;
        this.questionReader = questionReader;
        this.tagCreator = tagCreator;
    }

    @PostMapping
    public ResponseEntity<CreateQuestionResult> createQuestion(@RequestBody @Valid CreateQuestionCommand command) {
        //Add unique tags from command
        command.getTags().stream().map(TagDto::new).forEach(tagDto -> tagCreator.createIfAbsent(Tag.fromDto(tagDto)));
        return ResponseEntity.ok(questionCreator.createQuestion(command));
    }

    @GetMapping("/query")
    public ResponseEntity<List<QuestionDto>> queryQuestions() {
        return ResponseEntity.ok(questionReader.getAllQuestions());
    }
}
