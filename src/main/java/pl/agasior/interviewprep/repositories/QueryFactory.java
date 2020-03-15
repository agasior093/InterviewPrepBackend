package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

class QueryFactory {
    static Query idQuery(String id) {
        return new Query(Criteria.where("_id").is(id));
    }
}
