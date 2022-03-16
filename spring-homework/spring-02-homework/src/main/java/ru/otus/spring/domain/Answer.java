package ru.otus.spring.domain;

public class Answer {
    private final String variant;
    private final boolean isCorrect;

    public Answer(String variant, boolean isCorrect) {
        this.variant = variant;
        this.isCorrect = isCorrect;
    }

    public String getVariant() {
        return variant;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}