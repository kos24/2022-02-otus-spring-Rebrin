package ru.otus.repositories;

import ru.otus.models.Book;

import java.util.List;

public interface BookRepository {
    void insert(Book book);

    Book findByTitle(String title);

    Book findById(Long id);

    void deleteById(Long id);

    List<Book> findAllWithAllInfo();
}
