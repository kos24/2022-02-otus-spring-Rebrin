package ru.otus.service;

public interface PrintModelService {
    void printAllBooksWithInfo();

    void printAllAuthors();

    void printAllGenres();

    void printAllCommentsByBookId(Long bookId);

    void printBookByTitle(String title);

    void printBookById(Long bookId);
}
