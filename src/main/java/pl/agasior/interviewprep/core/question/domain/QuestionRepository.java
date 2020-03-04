package pl.agasior.interviewprep.core.question.domain;

interface QuestionRepository {
    Question save(Question question);
    Question update(Question question);
}
