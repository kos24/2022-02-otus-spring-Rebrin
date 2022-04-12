package ru.otus.repositories;

import ru.otus.models.Genre;

import java.util.List;

public interface GenreRepository {
    Genre getByName(String name);

    List<Genre> getAll();
}
