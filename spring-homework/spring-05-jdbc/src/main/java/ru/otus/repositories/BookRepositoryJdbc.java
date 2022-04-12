package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.ext.BookResultSetExtractor;
import ru.otus.repositories.ext.BookAuthorRelation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final AuthorRepository authorRepository;
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Book book) {
        Long id = book.getId();
        Long genreId = book.getGenre().getId();
        String title = book.getTitle();
        jdbc.update("Insert into book(id, title, genre_id) values (:id, :title, :genreId)",
                Map.of("id", id, "title", title, "genreId", genreId));
        book.getAuthors().forEach(author ->
                insertRelations(book.getId(), author.getId())
        );
    }

    @Override
    public Book findByTitle(String title) {
        Book book = jdbc.queryForObject("select b.id, b.title, g.id genre_id, g.name " +
                        "from book b left join genre g on b.genre_id = g.id " +
                        "where b.title = :title", Map.of("title", title),
                new BookRowMapper());
        if (book != null) {
            List<Author> authors = authorRepository.findByBookId(book.getId());
            book.setAuthors(authors);
        }
        return book;
    }

    @Override
    public Book findById(Long id) {
        Book book = jdbc.queryForObject("select b.id, b.title, g.id genre_id, g.name " +
                        "from book b left join genre g on b.genre_id = g.id " +
                        "where b.id = :id", Map.of("id", id),
                new BookRowMapper());
        if (book != null) {
            List<Author> authors = authorRepository.findByBookId(book.getId());
            book.setAuthors(authors);
        }
        return book;
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("Delete from book where id = :id", Map.of("id", id));
        deleteRelations(id);
    }


    @Override
    public List<Book> findAllWithAllInfo() {
        List<Author> authors = authorRepository.findAllUsed();
        List<BookAuthorRelation> relations = getRelations();
        Map<Long, Book> books =
                jdbc.query("select b.id, b.title, g.id genre_id, g.name " +
                                "from book b left join genre g on " +
                                "b.genre_id = g.id",
                        new BookResultSetExtractor());
        mergeBooksAuthors(books, authors, relations);
        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    private List<BookAuthorRelation> getRelations() {
        return jdbc.query("select book_id, author_id from book_author ba ",
                (rs, i) -> new BookAuthorRelation(rs.getLong(1), rs.getLong(2)));
    }

    private void insertRelations(Long bookId, Long authorId) {
        jdbc.update("insert into book_author(book_id, author_id) values (:bookId, :authorId)",
                Map.of("bookId", bookId, "authorId", authorId));
    }

    private void deleteRelations(Long bookId) {
        jdbc.update("delete from book_author where book_id = :bookId", Map.of("bookId", bookId));
    }

    private void mergeBooksAuthors(Map<Long, Book> books, List<Author> authors,
                                   List<BookAuthorRelation> relations) {
        Map<Long, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getId, Function.identity()));
        relations.forEach(r -> {
            if (books.containsKey(r.getBookId()) && authorMap.containsKey(r.getAuthorId())) {
                books.get(r.getBookId()).getAuthors().add(authorMap.get(r.getAuthorId()));
            }
        });
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id"), rs.getString("title"),
                    new Genre(rs.getLong("genre_id"), rs.getString("name")),
                    new ArrayList<>());
        }
    }


}
