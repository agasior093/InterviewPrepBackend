package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.agasior.interviewprep.entities.Question;
import pl.agasior.interviewprep.entities.QuestionList.QuestionList;

import java.util.List;

@Repository
public interface QuestionListRepository extends MongoRepository<QuestionList, String> {


}
