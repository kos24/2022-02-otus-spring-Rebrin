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
    public Long insert(String title, String genreName, String... authors) {
        List<Author> authorList = authorRepository.findByNames(Arrays.asList(authors));
        Genre genre = genreRepository.getByName(genreName);
        return bookRepository.insert(new Book(title, genre, authorList));
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
    public void update(Long id, String title, String genreName, String... authors) {
        List<Author> authorList = authorRepository.findByNames(Arrays.asList(authors));
        Genre genre = genreRepository.getByName(genreName);
        bookRepository.update(new Book(id, title, genre, authorList));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

}
