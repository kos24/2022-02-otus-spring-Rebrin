package ru.otus.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@JdbcTest
@Import({BookRepositoryJdbc.class, AuthorRepositoryJdbc.class})
class BookRepositoryJdbcTest {

    private static final Long EXPECTED_BOOK_ID = 5L;

    @Autowired
    private BookRepository repositoryJdbc;

    @DisplayName("должен добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        List<Author> authors = Arrays.asList(new Author(5L, "author_name_05"),
                new Author(6L, "author_name_06"));
        Book expectedBook = new Book("title", new Genre(4L, "genre_name4"), authors);
        Long expectedId = repositoryJdbc.insert(expectedBook);
        var actualBook = repositoryJdbc.findById(expectedId);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isEqualTo(expectedId);
        assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
    }

    @DisplayName("должен находить книгу в БД по id")
    @Test
    void shouldFindBookById() {
        var actualBook = repositoryJdbc.findById(EXPECTED_BOOK_ID);
        assertThat(actualBook.getTitle()).isNotNull()
                .isEqualTo("book_05");
    }

    @DisplayName("должен удалить книгу в БД по id")
    @Test
    void shouldDeleteBookById() {
        assertThatCode(() -> repositoryJdbc.findById(EXPECTED_BOOK_ID)).doesNotThrowAnyException();

        repositoryJdbc.deleteById(EXPECTED_BOOK_ID);

        assertThatThrownBy(() -> repositoryJdbc.findById(EXPECTED_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {

        var books = repositoryJdbc.findAllWithAllInfo();
        Assertions.assertThat(books).isNotNull()
                .isNotEmpty()
                .allMatch(book -> !book.getTitle().equals(""))
                .allMatch(book -> book.getAuthors() != null && book.getAuthors().size() > 0)
                .allMatch(book -> book.getGenre() != null);
    }
}