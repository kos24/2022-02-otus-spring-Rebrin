package ru.otus.exceptions;

public class CommonProjectException extends RuntimeException {

    public CommonProjectException(String message) {
        super(message);
    }

    public CommonProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
