package ru.otus.repositories;

import ru.otus.models.Book;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    List<Book> findByTitle(String title);

    Book findById(Long id);

    void deleteById(Long id);

    Book update(Book book);

    List<Book> findAll();
}
