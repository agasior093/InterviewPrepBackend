package pl.agasior.interviewprep.services.question;

import org.springframework.stereotype.Component;
import pl.agasior.interviewprep.dto.QuestionDto;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.entities.Tag;

import java.util.stream.Collectors;

@Component
class QuestionConverter {

    public QuestionDto toDto(Question entity) {
        return QuestionDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .answer(entity.getAnswer())
                .userId(entity.getUserId())
                .tags(entity.getTags())
                .build();
    }
}
