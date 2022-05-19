package ru.otus.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.BookRequestDto;
import ru.otus.models.Book;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.GenreRepository;

import java.util.Arrays;

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
                .genre(source.getGenreName().isBlank()? null : genreRepository.getByName(source.getGenreName()))
                .authors(source.getAuthors()[0].isBlank() ? null : authorRepository.findByNames(Arrays.asList(source.getAuthors())))
                .build();
    }
}
