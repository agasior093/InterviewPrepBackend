package pl.agasior.interviewprep.core.question.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QuestionConfiguration {

    @Bean
    QuestionFacade questionFacade(QuestionRepository repository) {
        return new QuestionFacade(repository);
    }
}
