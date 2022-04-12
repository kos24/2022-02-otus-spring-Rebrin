package ru.otus.service;

import ru.otus.models.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllWithAllInfo();

    void insert(String title, String genreName, String... authors);

    Book getBookByTitle(String title);

    Book getBookById(Long id);

    void deleteBookById(Long id);

    void printAllWithInfo();
}
