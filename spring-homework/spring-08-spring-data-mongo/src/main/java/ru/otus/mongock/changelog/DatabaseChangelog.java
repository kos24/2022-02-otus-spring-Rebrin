package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.CommentRepository;
import ru.otus.repositories.GenreRepository;

import java.util.Arrays;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "insertGenre1", author = "korebrin")
    public void insertGenre1(GenreRepository genreRepository) {
        genreRepository.save(new Genre("genre1"));
    }

    @ChangeSet(order = "002", id = "insertGenre2", author = "korebrin")
    public void insertGenre2(GenreRepository genreRepository) {
        genreRepository.save(new Genre("genre2"));
    }

    @ChangeSet(order = "003", id = "insertAuthor1", author = "korebrin")
    public void insertAuthor1(AuthorRepository authorRepository) {
        authorRepository.save(new Author("author1"));
    }

    @ChangeSet(order = "004", id = "insertAuthor2", author = "korebrin")
    public void insertAuthor2(AuthorRepository authorRepository) {
        authorRepository.save(new Author("author2"));
    }

    @ChangeSet(order = "005", id = "insertAuthor3", author = "korebrin")
    public void insertAuthor3(AuthorRepository authorRepository) {
        authorRepository.save(new Author("author3"));
    }

    @ChangeSet(order = "006", id = "insertBook1", author = "korebrin")
    public void insertBook1(BookRepository repository, GenreRepository genreRepository, AuthorRepository authorRepository) {
        Genre genre = genreRepository.findGenreByName("genre1");
        Author author1 = authorRepository.findAuthorByFullName("author1");
        Author author2 = authorRepository.findAuthorByFullName("author2");
        repository.save(new Book("title1", genre,
                Arrays.asList(author1, author2)));
    }

    @ChangeSet(order = "007", id = "insertBook2", author = "korebrin")
    public void insertBook2(BookRepository repository, GenreRepository genreRepository, AuthorRepository authorRepository) {
        Genre genre = genreRepository.findGenreByName("genre2");
        Author author2 = authorRepository.findAuthorByFullName("author2");
        Author author3 = authorRepository.findAuthorByFullName("author3");
        repository.save(new Book("title2", genre,
                Arrays.asList(author2, author3)));
    }

    @ChangeSet(order = "008", id = "insertComment1", author = "korebrin")
    public void insertComment1(BookRepository repository, CommentRepository commentRepository) {
        Book book = repository.findBookByTitle("title1").get(0);
        commentRepository.save(new Comment("comment1", book));
    }

    @ChangeSet(order = "009", id = "insertComment2", author = "korebrin")
    public void insertComment2(BookRepository repository, CommentRepository commentRepository) {
        Book book = repository.findBookByTitle("title1").get(0);
        commentRepository.save(new Comment("comment2", book));
    }

    @ChangeSet(order = "010", id = "insertComment3", author = "korebrin")
    public void insertComment3(BookRepository repository, CommentRepository commentRepository) {
        Book book = repository.findBookByTitle("title2").get(0);
        commentRepository.save(new Comment("comment3", book));
    }
}
