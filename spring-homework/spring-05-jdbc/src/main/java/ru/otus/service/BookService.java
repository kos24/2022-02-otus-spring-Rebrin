package ru.otus.service;

import ru.otus.models.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllWithAllInfo();

    Long insert(String title, String genreName, String... authors);

    Book getBookByTitle(String title);

    Book getBookById(Long id);

    void update(Long id, String title, String genreName, String... authors);

    void deleteBookById(Long id);
}
