package ru.otus.exceptions;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String message) {
        super(message);
    }

    public GenreNotFoundException(Throwable cause) {
        super(cause);
    }
}
