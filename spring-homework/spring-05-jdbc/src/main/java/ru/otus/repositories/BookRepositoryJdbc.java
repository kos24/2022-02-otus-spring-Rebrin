package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.ext.BookAuthorRelation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final AuthorRepository authorRepository;
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Long insert(Book book) {
        String title = book.getTitle();
        Long genreId = book.getGenre().getId();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.getJdbcOperations().update(
                con -> {
                    PreparedStatement ps = con.prepareStatement("Insert into book(title, genre_id) values (?,?)",
                            new String[]{"id"});
                    ps.setString(1, title);
                    ps.setLong(2, genreId);
                    return ps;
                },
                keyHolder);

        book.getAuthors().forEach(author ->
                insertRelations((Long) keyHolder.getKey(), author.getId())
        );
        return (Long) keyHolder.getKey();
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
        List<Book> books =
                jdbc.query("select b.id, b.title, g.id genre_id, g.name " +
                                "from book b left join genre g on " +
                                "b.genre_id = g.id",
                        new BookRowMapper());
        return mergeBooksAuthors(books, authors, relations);
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

    private List<Book> mergeBooksAuthors(List<Book> books, List<Author> authors,
                                         List<BookAuthorRelation> relations) {
        Map<Long, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getId, Function.identity()));
        Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        relations.forEach(r -> {
            if (bookMap.containsKey(r.getBookId()) && authorMap.containsKey(r.getAuthorId())) {
                bookMap.get(r.getBookId()).getAuthors().add(authorMap.get(r.getAuthorId()));
            }

        });
        return new ArrayList<>(Objects.requireNonNull(bookMap).values());
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
