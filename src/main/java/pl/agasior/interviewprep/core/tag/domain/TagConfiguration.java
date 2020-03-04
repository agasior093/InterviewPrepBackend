package pl.agasior.interviewprep.core.tag.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TagConfiguration {
    @Bean
    TagFacade tagFacade(TagRepository repository) {
        return new TagFacade(repository);
    }
}
