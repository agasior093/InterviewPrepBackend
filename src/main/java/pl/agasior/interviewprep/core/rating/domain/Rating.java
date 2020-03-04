package pl.agasior.interviewprep.core.rating.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
class Rating {
    private String id;
    private String questionId;
    private Double score;
    private Integer likes;
    private Integer dislikes;

    String getId() {
        return id;
    }

    void setId(final String id) {
        this.id = id;
    }

    String getQuestionId() {
        return questionId;
    }

    void setQuestionId(final String questionId) {
        this.questionId = questionId;
    }

    Double getScore() {
        return score;
    }

    void setScore(final Double score) {
        this.score = score;
    }

    Integer getLikes() {
        return likes;
    }

    void setLikes(final Integer likes) {
        this.likes = likes;
    }

    Integer getDislikes() {
        return dislikes;
    }

    void setDislikes(final Integer dislikes) {
        this.dislikes = dislikes;
    }
}
