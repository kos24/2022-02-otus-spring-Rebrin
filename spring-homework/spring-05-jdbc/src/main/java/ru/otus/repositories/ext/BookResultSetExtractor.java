package ru.otus.repositories.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.models.Genre;
import ru.otus.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookResultSetExtractor implements
        ResultSetExtractor<Map<Long, Book>> {
    @Override
    public Map<Long, Book> extractData(ResultSet rs) throws SQLException,
            DataAccessException {

        Map<Long, Book> books = new HashMap<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            Book book = new Book(id, rs.getString("title"),
                    new Genre(rs.getLong("genre_id"), rs.getString("name")),
                    new ArrayList<>());
            books.put(book.getId(), book);
        }
        return books;
    }
}
