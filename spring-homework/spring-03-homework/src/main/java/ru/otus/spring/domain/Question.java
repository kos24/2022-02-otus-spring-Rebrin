package ru.otus.spring.domain;

import java.util.List;

public class Question {
    private final String questionText;
    private final List<Answer> possibleAnswers;

    public Question(String questionText, List<Answer> possibleAnswers) {
        this.questionText = questionText;
        this.possibleAnswers = possibleAnswers;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public List<Answer> getPossibleAnswers() {
        return this.possibleAnswers;
    }

    public String getCorrectAnswer() {
        return possibleAnswers.stream()
                .filter(Answer::isCorrect)
                .map(Answer::getVariant)
                .findFirst()
                .orElse(null);
    }
}