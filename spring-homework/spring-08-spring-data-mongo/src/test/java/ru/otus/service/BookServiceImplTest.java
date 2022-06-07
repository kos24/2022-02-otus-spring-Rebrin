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
import ru.otus.repositories.CommentRepository;
import ru.otus.repositories.GenreRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с книгами ")
@SpringBootTest
//@DataMongoTest
//@EnableAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})

class BookServiceImplTest {

    private static final String GENRE = "genre1";
    private static final String ID = "1";
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
    private CommentRepository commentRepository;

    @MockBean
    private BookRequestDtoConverter converter;

    private String title;
    private String expectedId;

//    private static final String CONNECTION_STRING = "mongodb://%s:%d";

//    private MongodExecutable mongodExecutable;
//    @Autowired
//    private MongoTemplate mongoTemplate;

//    @AfterEach
//    void clean() {
//        mongodExecutable.stop();
//    }


    @BeforeEach
    void setUp() throws IOException {
        title = TITLE;
        expectedId = ID;

//        String ip = "localhost";
//        int port = 27016;
//
//        ImmutableMongodConfig mongodConfig = MongodConfig
//                .builder()
//                .version(Version.Main.PRODUCTION)
//                .net(new Net(ip, port, Network.localhostIsIPv6()))
//                .build();
//
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//        mongodExecutable = starter.prepare(mongodConfig);
//        mongodExecutable.start();
//        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
    }

    @Test
    void shouldInsertBook() {

        List<Author> authors = List.of(new Author("1", FIRST_AUTHOR), new Author("2", SECOND_AUTHOR));
        var genre = new Genre(ID,GENRE);
        var expectedBook = new Book(ID, TITLE, genre, authors);
        var bookRequestDto = new BookRequestDto(null, title, GENRE, new String[] {FIRST_AUTHOR, SECOND_AUTHOR});

        Mockito.when(authorRepository.findAuthorByFullName(any())).thenReturn(authors.get(0)).thenReturn(authors.get(1));
        Mockito.when(genreRepository.findGenreByName(anyString())).thenReturn(genre);
        Mockito.when(converter.convert(bookRequestDto)).thenReturn(expectedBook);
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);

        var actualBook = bookService.insert(bookRequestDto);

        assertThat(actualBook).isNotNull().isEqualTo(expectedBook);
    }

    @Test
    void getBookByTitle() {
        bookService.getBookByTitle(title);
        verify(bookRepository,times(1)).findBookByTitle(title);
    }

    @Test
    void getBookById() {
        Mockito.when(bookRepository.findById(expectedId)).thenReturn(Optional.of(new Book()));
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