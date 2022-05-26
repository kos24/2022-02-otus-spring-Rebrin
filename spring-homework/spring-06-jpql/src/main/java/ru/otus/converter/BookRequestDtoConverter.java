package ru.otus.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.BookRequestDto;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.GenreRepository;

import java.util.Arrays;
import java.util.List;

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
                .genre(genreRepository.getByName(source.getGenreName()))
                .authors(getAuthors(source.getAuthors()))
                .build();
    }

    private List<Author> getAuthors(String[] authorNames) {
        return Arrays.stream(authorNames).map(authorRepository::findByName).toList();
    }
}
