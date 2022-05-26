package ru.otus.repositories;

import ru.otus.models.Author;

import java.util.List;

public interface AuthorRepository {

    Author findByName(String fullName);

    List<Author> findAll();

    Author update(Author author);
}
