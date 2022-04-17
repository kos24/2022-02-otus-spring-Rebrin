package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с книгами ")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @MockBean
    GenreRepository genreRepository;

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    BookRepository bookRepository;

    String title;
    Long expectedId;

    @BeforeEach
    void setUp() {
        title = "test1";
        expectedId = 1L;
    }

    @Test
    void shouldInsertBook() {
        String genreStr = "genre1";

        List<Author> authors = List.of(new Author(1L, "author1"), new Author(2L, "author2"));
        Genre genre = new Genre(1L, genreStr);

        Mockito.when(authorRepository.findByNames(any())).thenReturn(authors);
        Mockito.when(genreRepository.getByName(anyString())).thenReturn(genre);
        Mockito.when(bookRepository.insert(new Book(title, genre, authors))).thenReturn(expectedId);

        Long actualBookId = bookService.insert(title, genreStr, "author1", "author2");

        assertThat(actualBookId).isNotNull().isEqualTo(expectedId);
    }

    @Test
    void getBookByTitle() {
        bookService.getBookByTitle(title);
        verify(bookRepository,times(1)).findByTitle(title);
    }

    @Test
    void getBookById() {
        bookService.getBookById(expectedId);
        verify(bookRepository,times(1)).findById(expectedId);
    }

    @Test
    void deleteBookById() {
        bookService.deleteBookById(expectedId);
        verify(bookRepository,times(1)).deleteById(expectedId);
    }

    @Test
    void findAllWithAllInfo() {
        bookService.findAllWithAllInfo();
        verify(bookRepository,times(1)).findAllWithAllInfo();
    }
}