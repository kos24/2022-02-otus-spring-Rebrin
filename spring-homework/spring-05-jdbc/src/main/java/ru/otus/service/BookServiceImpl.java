package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Book> findAllWithAllInfo() {
        return bookRepository.findAllWithAllInfo();
    }

    @Override
    public void insert(String title, String genreName, String... authors) {
        Long id = Math.round(Math.random() * 1000 + 1);
        List<Author> authorList = Arrays.stream(authors)
                .map(authorRepository::findByName)
                .collect(Collectors.toList());
        Genre genre = genreRepository.getByName(genreName);
        bookRepository.insert(new Book(id, title, genre, authorList));
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void printAllWithInfo() {
        findAllWithAllInfo().forEach(System.out::println);
    }
}
