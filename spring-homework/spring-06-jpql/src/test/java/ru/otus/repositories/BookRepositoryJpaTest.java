package ru.otus.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final Long FIRST_BOOK_ID = 1L;
    public static final String NEW_TITLE = "new title";
    public static final String FIRST_BOOK_TITLE = "book_01";
    public static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    public static final List<Author> AUTHORS = Arrays.asList(new Author("author_name_04"),
            new Author("author_name_05"));
    public static final String NEW_GENRE = "genre_name4";

    @Autowired
    private BookRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен добавлять книгу в БД")
    @Test
    void shouldInsertBook() {

        var expectedBook = repositoryJpa.save(new Book(NEW_TITLE, new Genre(NEW_GENRE), AUTHORS, null));
        assertThat(expectedBook.getId()).isPositive();
        var actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull().matches(book -> book.getTitle().equals(expectedBook.getTitle()))
                .matches(book -> book.getGenre().equals(expectedBook.getGenre()))
                .matches(book -> book.getAuthors() != null && book.getAuthors().size() > 0);
    }

    @DisplayName("должен обновлять книгу в БД по ID")
    @Test
    void shouldUpdateBookById() {
        var initialBook = em.find(Book.class, FIRST_BOOK_ID);
        String oldTitle = initialBook.getTitle();
        initialBook.setTitle(NEW_TITLE);
        repositoryJpa.update(initialBook);
        em.flush();
        em.clear();

        Book updatedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(NEW_TITLE);
    }

    @DisplayName("должен находить книгу по ее названию")
    @Test
    void shouldFindExpectedBookByTitle() {
        var expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        List<Book> books = repositoryJpa.findByTitle(FIRST_BOOK_TITLE);

        assertThat(books).containsOnlyOnce(expectedBook);
    }

    @DisplayName("должен находить книгу по ID")
    @Test
    void shouldFindExpectedBookById() {
        var expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        var actualBook = repositoryJpa.findById(FIRST_BOOK_ID);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен удалять книгу по ID")
    @Test
    void shouldDeleteExpectedBookById() {
        var bookToBeDeleted = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(bookToBeDeleted).isNotNull();

        repositoryJpa.deleteById(FIRST_BOOK_ID);
        em.clear();

        var deletedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(deletedBook).isNull();
    }

    @DisplayName("должен находить все книги вместе с жанром и авторами")
    @Test
    void shouldFindAllBooksWithInfo() {
        List<Book> books = repositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getTitle().isBlank())
                .allMatch(b -> b.getGenre() != null && !b.getGenre().getName().isBlank())
                .allMatch(b -> b.getAuthors() != null && b.getAuthors().size() > 0);
    }
}