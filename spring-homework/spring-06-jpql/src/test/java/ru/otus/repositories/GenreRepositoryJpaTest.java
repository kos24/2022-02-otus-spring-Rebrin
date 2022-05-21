package ru.otus.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final long FIRST_COMMENT_ID = 1L;
    private static final String GENRE_NAME = "genre_name1";
    private static final int EXPECTED_NUMBER_OF_GENRES = 3;

    @Autowired
    private GenreRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldFingGenreByName() {
        var expectedGenre = em.find(Genre.class, FIRST_COMMENT_ID);
        var actualGenre = repositoryJpa.getByName(GENRE_NAME);
        assertThat(actualGenre).isNotNull().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getAll() {
        var expectedGenre = em.find(Genre.class, FIRST_COMMENT_ID);
        List<Genre> genres = repositoryJpa.getAll();

        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES).containsOnlyOnce(expectedGenre);

    }
}