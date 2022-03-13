package ru.otus.spring.domain;

import java.util.ArrayList;
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
        return possibleAnswers.stream().filter(Answer::isCorrect).map(Answer::getVariant).findFirst().orElse(null);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String questionText;
        private List<Answer> possibleAnswers;

        public Builder questionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public Builder possibleAnswers(List<Answer> possibleAnswers) {
            if (this.possibleAnswers == null) {
                this.possibleAnswers = new ArrayList<>();
            }
            this.possibleAnswers.addAll(possibleAnswers);
            return this;
        }

        public Builder possibleAnswers(Answer possibleAnswer) {
            if (this.possibleAnswers == null) {
                this.possibleAnswers = new ArrayList<>();
            }
            this.possibleAnswers.add(possibleAnswer);
            return this;
        }

        public Question build() {
            return new Question(questionText, possibleAnswers);
        }


    }


}