package ru.otus.repositories;

import ru.otus.models.Book;

import java.util.List;

public interface BookRepository {
    Long insert(Book book);

    Book findByTitle(String title);

    Book findById(Long id);

    void deleteById(Long id);

    void update(Book book);

    List<Book> findAllWithAllInfo();
}
