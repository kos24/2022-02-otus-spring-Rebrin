package ru.otus.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.BookRequestDto;
import ru.otus.exceptions.AuthorNotFoundException;
import ru.otus.exceptions.GenreNotFoundException;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookRequestDtoConverter implements Converter<BookRequestDto, Book> {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public Book convert(BookRequestDto source) {
        return Book.builder()
                .id(source.getId())
                .title(source.getTitle())
                .genre(getGenre(source.getGenreName()))
                .authors(getAuthors(source.getAuthors()))
                .build();
    }

    private List<Author> getAuthors(String[] authorNames) {
        return Arrays.stream(authorNames)
                .map(author ->
                        Optional.ofNullable(authorRepository.findAuthorByFullName(author))
                                .orElseThrow(
                                        () -> new AuthorNotFoundException(String.format("Author: %s not found, consider adding a new one with this name ", author)
                                        ))
                ).toList();
    }

    private Genre getGenre(String genreName) {
        return Optional.ofNullable(genreRepository.findGenreByName(genreName)).orElseThrow(
                () -> new GenreNotFoundException(
                        String.format("Genre: %s not found, consider adding a new one with this name ", genreName))
        );
    }
}
