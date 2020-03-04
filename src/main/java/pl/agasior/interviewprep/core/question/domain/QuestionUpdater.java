package pl.agasior.interviewprep.core.question.domain;

class QuestionUpdater {
    private final QuestionRepository questionRepository;

    public QuestionUpdater(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
