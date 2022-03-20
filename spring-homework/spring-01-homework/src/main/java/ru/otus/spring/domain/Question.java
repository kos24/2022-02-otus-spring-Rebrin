package ru.otus.spring.domain;

public class Question {

    private final String questionAsked;
    private final String possibleAnswers;

    public Question(String questionAsked, String possibleAnswers) {
        this.questionAsked = questionAsked;
        this.possibleAnswers = possibleAnswers;
    }

    public String getQuestionAsked() {
        return questionAsked;
    }

    public String getPossibleAnswers() {
        return possibleAnswers;
    }
}
