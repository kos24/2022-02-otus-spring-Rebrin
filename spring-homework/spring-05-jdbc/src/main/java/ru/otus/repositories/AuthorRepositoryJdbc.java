package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.exceptions.AuthorNotFoundException;
import ru.otus.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> findByNames(List<String> fullNames) {
        checkIfNameExist(fullNames);
        return jdbc.query("Select a.id, a.full_name from author a where a.full_name in (:fullNames)",
                Map.of("fullNames",
                        fullNames), new AuthorRowMapper());
    }

    private void checkIfNameExist(List<String> fullNames) {
        List<String> authorNames = findAll().stream().map(Author::getFullName).collect(Collectors.toList());
        List<String> authorsNotInDB = fullNames.stream()
                .filter(name -> !authorNames.contains(name))
                .collect(Collectors.toList());
        if (!authorsNotInDB.isEmpty()) {
            throw new AuthorNotFoundException(String.format("Can't find authors: %s",
                    String.join(", ", authorsNotInDB)));
        }
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select a.id, a.full_name " +
                "from author a ", new AuthorRowMapper());
    }

    @Override
    public List<Author> findAllUsed() {
        return jdbc.query("select a.id, a.full_name " +
                "from author a inner join book_author ba on a.id = ba.author_id " +
                "group by a.id, a.full_name ", new AuthorRowMapper());
    }

    @Override
    public List<Author> findByBookId(Long bookId) {
        return jdbc.query("Select a.id, a.full_name " +
                "from author a inner join book_author ba on a.id = ba.author_id " +
                "where ba.book_id = :bookId", Map.of("bookId", bookId), new AuthorRowMapper());
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("full_name"));
        }
    }

}
