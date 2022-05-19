package ru.otus.service;

import org.springframework.transaction.annotation.Transactional;

public interface PrintModelService {
    void printAllBooksWithInfo();

    void printAllAuthors();

    void printAllGenres();

    void printAllCommentsByBookId(Long bookId);

    @Transactional
    void printBookByTitle(String title);

    @Transactional
    void printBookById(Long bookId);
}
