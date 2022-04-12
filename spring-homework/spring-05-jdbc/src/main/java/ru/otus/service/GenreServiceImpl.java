package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.models.Genre;
import ru.otus.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public void printAll() {
        genreRepository.getAll().stream().map(Genre::getName).forEach(System.out::println);
    }
}
