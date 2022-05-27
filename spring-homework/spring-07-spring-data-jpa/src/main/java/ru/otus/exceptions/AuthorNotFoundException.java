package ru.otus.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
        super();
    }
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
