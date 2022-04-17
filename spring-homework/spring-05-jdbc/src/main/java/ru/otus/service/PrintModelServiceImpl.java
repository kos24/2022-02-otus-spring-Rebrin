package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
public class PrintModelServiceImpl implements PrintModelService {

    private final IOService ioService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public void printAllBooksWithInfo() {
        bookRepository.findAllWithAllInfo().stream()
                .map(Book::toString)
                .forEach(ioService::print);
    }

    @Override
    public void printAllAuthors() {
        authorRepository.findAll().stream()
                .map(Author::getFullName)
                .forEach(ioService::print);
    }

    @Override
    public void printAllGenres() {
        genreRepository.getAll().stream()
                .map(Genre::getName)
                .forEach(ioService::print);
    }

}
