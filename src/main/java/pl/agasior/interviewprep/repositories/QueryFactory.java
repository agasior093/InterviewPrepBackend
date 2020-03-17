package pl.agasior.interviewprep.repositories;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

class QueryFactory {
    QueryFactory() {}

    static Query idQuery(String id) {
        return new Query(Criteria.where("_id").is(id));
    }

    static Query usernameQuery(String username) {
        return new Query(Criteria.where("username").is(username));
    }

    static Query emailQuery(String email) {
        return new Query(Criteria.where("email").is(email));
    }

    static Query tagsToFilterBy(List<String> tags) {
        return new Query(Criteria.where("tags").in(tags));
    }
}
