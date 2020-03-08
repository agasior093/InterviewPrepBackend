package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.QuestionDto;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.repositories.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionReader {

    private final QuestionRepository questionRepository;
    private final QuestionConverter converter;

    QuestionReader(final QuestionRepository questionRepository, final QuestionConverter converter) {
        this.questionRepository = questionRepository;
        this.converter = converter;
    }

    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream().map(converter::toDto).collect(Collectors.toList());
    }
}
