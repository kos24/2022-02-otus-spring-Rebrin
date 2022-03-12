package ru.otus.spring.domain;

public class Question {
    private final String questionAsked;
    private final String possibleAnswers;
    private final String answer;

    public Question(String questionAsked, String possibleAnswers, String answer) {
        this.questionAsked = questionAsked;
        this.possibleAnswers = possibleAnswers;
        this.answer = answer;
    }

    public String getQuestionAsked() {
        return this.questionAsked;
    }

    public String getPossibleAnswers() {
        return this.possibleAnswers;
    }

    public String getAnswer() {
        return this.answer;
    }
}