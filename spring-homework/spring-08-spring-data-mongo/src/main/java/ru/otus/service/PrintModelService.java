package ru.otus.service;

public interface PrintModelService {
    void printAllBooksWithInfo();

    void printAllAuthors();

    void printAllGenres();

    void printAllCommentsByBookId(String bookId);

    void printBookByTitle(String title);

    void printBookById(String bookId);
}
