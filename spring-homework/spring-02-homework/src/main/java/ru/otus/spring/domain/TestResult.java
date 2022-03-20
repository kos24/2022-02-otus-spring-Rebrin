package ru.otus.spring.domain;

public class TestResult {

    private final int maxPoints;
    private int correctAnswersCount;

    public TestResult(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void incrementCorrectAnswersCount() {
        this.correctAnswersCount++;
    }
}
