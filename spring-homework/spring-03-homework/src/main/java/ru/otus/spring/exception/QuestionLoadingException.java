package ru.otus.spring.exception;

public class QuestionLoadingException extends RuntimeException {
    public QuestionLoadingException() {
    }

    public QuestionLoadingException(String message) {
        super(message);
    }

    public QuestionLoadingException(Throwable cause) {
        super(cause);
    }
}
