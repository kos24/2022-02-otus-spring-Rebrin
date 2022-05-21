package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.converter.BookRequestDtoConverter;
import ru.otus.dto.BookRequestDto;
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

    private static final String GENRE = "genre1";
    private static final long ID = 1L;
    private static final String TITLE = "test1";
    private static final String FIRST_AUTHOR = "author1";
    private static final String SECOND_AUTHOR = "author2";

    @Autowired
    private BookService bookService;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookRequestDtoConverter converter;

    private String title;
    private Long expectedId;

    @BeforeEach
    void setUp() {
        title = TITLE;
        expectedId = ID;
    }

    @Test
    void shouldInsertBook() {

        List<Author> authors = List.of(new Author(1L, FIRST_AUTHOR), new Author(2L, SECOND_AUTHOR));
        var genre = new Genre(ID,GENRE);
        var expectedBook = new Book(ID, TITLE, genre, authors);
        var bookRequestDto = new BookRequestDto(null, title, GENRE, new String[] {FIRST_AUTHOR, SECOND_AUTHOR});

        Mockito.when(authorRepository.findByName(any())).thenReturn(authors.get(0)).thenReturn(authors.get(1));
        Mockito.when(genreRepository.getByName(anyString())).thenReturn(genre);
        Mockito.when(converter.convert(bookRequestDto)).thenReturn(expectedBook);
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);

        var actualBook = bookService.insert(bookRequestDto);

        assertThat(actualBook).isNotNull().isEqualTo(expectedBook);
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
        bookService.findAll();
        verify(bookRepository,times(1)).findAll();
    }
}