package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
public class PrintModelServiceImpl implements PrintModelService {

    private final IOService ioService;
    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentService commentService;

    @Override
    @Transactional
    public void printAllBooksWithInfo() {
        bookService.findAll().stream()
                .map(Book::toString)
                .forEach(ioService::print);
    }

    @Override
    @Transactional
    public void printAllAuthors() {
        authorRepository.findAll().stream()
                .map(Author::getFullName)
                .forEach(ioService::print);
    }

    @Override
    @Transactional
    public void printAllGenres() {
        genreRepository.getAll().stream()
                .map(Genre::getName)
                .forEach(ioService::print);
    }

    @Override
    public void printAllCommentsByBookId(Long bookId) {
        commentService.findAllByBookId(bookId).stream()
                .map(Comment::toString)
                .forEach(ioService::print);
    }

    @Override
    @Transactional
    public void printBookByTitle(String title) {
        bookService.getBookByTitle(title).stream()
                .map(Book::toString)
                .forEach(ioService::print);
    }

    @Override
    @Transactional
    public void printBookById(Long bookId) {
        ioService.print(bookService.getBookById(bookId).toString());
    }
}
