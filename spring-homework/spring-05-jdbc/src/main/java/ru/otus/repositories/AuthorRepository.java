package ru.otus.repositories;

import ru.otus.models.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> findByNames(List<String> fullNames);

    List<Author> findAll();

    List<Author> findAllUsed();

    List<Author> findByBookId(Long bookId);
}
