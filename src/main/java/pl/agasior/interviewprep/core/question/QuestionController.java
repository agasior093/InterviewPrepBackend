package pl.agasior.interviewprep.core.question;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agasior.interviewprep.core.question.domain.QuestionFacade;
import pl.agasior.interviewprep.core.question.dto.CreateQuestionCommand;
import pl.agasior.interviewprep.core.question.dto.CreateQuestionResult;
import pl.agasior.interviewprep.core.question.query.QuestionDto;
import pl.agasior.interviewprep.core.question.query.QuestionQueryRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question")
class QuestionController {
    private final QuestionFacade questionFacade;
    private final QuestionQueryRepository queryRepository;

    QuestionController(final QuestionFacade questionFacade, final QuestionQueryRepository queryRepository) {
        this.questionFacade = questionFacade;
        this.queryRepository = queryRepository;
    }

    @PostMapping
    ResponseEntity<CreateQuestionResult> createQuestion(@RequestBody @Valid CreateQuestionCommand command) {
        return ResponseEntity.ok(questionFacade.createQuestion(command));
    }

    @GetMapping("/query")
    ResponseEntity<List<QuestionDto>> queryQuestions() {
        return ResponseEntity.ok(queryRepository.findAll());
    }
}
