package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.exceptions.GenreNotFoundException;
import ru.otus.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Genre getByName(String name) {
        try {
            return jdbc.queryForObject("Select id, name from genre where name = :name",
                    Map.of("name", name),
                    new GenreRowMapper());
        } catch (Exception e) {
            throw new GenreNotFoundException(String.format("Can't find genre: %s", name));
        }
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genre", new GenreRowMapper());
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
