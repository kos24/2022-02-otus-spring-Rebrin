package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.GenreRepository;

import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrintModelServiceImpl implements PrintModelService {

    private final IOService ioService;
    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentService commentService;

    @Override
    @Transactional(readOnly = true)
    public void printAllBooksWithInfo() {

        bookService.findAll().stream().map(this::convertBookForPrint).forEach(ioService::print);
    }

    @Override
    @Transactional(readOnly = true)
    public void printAllAuthors() {
        authorRepository.findAll().stream()
                .map(Author::getFullName)
                .forEach(ioService::print);
    }

    @Override
    @Transactional(readOnly = true)
    public void printAllGenres() {
        genreRepository.findAll().stream()
                .map(Genre::getName)
                .forEach(ioService::print);
    }

    @Override
    @Transactional(readOnly = true)
    public void printAllCommentsByBookId(String bookId) {
        commentService.findAllByBookId(bookId).stream()
                .map(c -> {
                    String prefix = "Comment: ";
                    StringJoiner sj = new StringJoiner(",", prefix, "");
                    sj.add("id=" + c.getId().toString());
                    sj.add("comment=" + c.getComment());
                    sj.add("bookId=" + c.getBook().getId() + ")");
                    return sj.toString();})
                .forEach(ioService::print);
    }

    @Override
    @Transactional(readOnly = true)
    public void printBookByTitle(String title) {
        bookService.getBookByTitle(title).stream()
                .map(this::convertBookForPrint)
                .forEach(ioService::print);
    }

    @Override
    @Transactional(readOnly = true)
    public void printBookById(String bookId) {
        ioService.print(convertBookForPrint(bookService.getBookById(bookId)));
    }

    private String convertBookForPrint(Book book) {
        String prefix = "Book: ";
        StringJoiner sj = new StringJoiner(",", prefix, "");
        sj.add("id=" + book.getId().toString());
        sj.add("title=" + book.getTitle());
        sj.add("genre=" + book.getGenre().getName());
        sj.add("authors=(" + book.getAuthors().stream()
                .map(Author::getFullName)
                .collect(Collectors.joining(",")) + ")");
        return sj.toString();
    }
}
