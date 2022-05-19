package ru.otus.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.exceptions.AuthorNotFoundException;
import ru.otus.models.Author;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе Jpa для работы с авторами")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    public static final List<String> AUTHORS = Collections.singletonList("author_name_01");
    public static final List<String> NOT_EXISTING_AUTHORS = Collections.singletonList("not_existing_author");

    @Autowired
    private AuthorRepository repositoryJpa;

    @Autowired
    TestEntityManager em;

    @DisplayName("должен найти авторов по ФИО")
    @Test
    void shouldFindAuthorsByNames() {
         var expectedAuthor= em.find(Author.class, 1L);
        List<Author> authors = repositoryJpa.findByNames(AUTHORS);
        assertThat(authors).containsOnlyOnce(expectedAuthor);
    }

    @DisplayName("должен кидать исключение, если ФИО авторов не найдено")
    @Test
    void shouldThrowExceptionWhenNotExistingAuthorNames() {
        assertThrows(AuthorNotFoundException.class, () -> repositoryJpa.findByNames(NOT_EXISTING_AUTHORS));
    }

    @DisplayName("должен найти всех авторов")
    @Test
    void findAll() {
        List<Author> authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(3)
                .allMatch(a -> !a.getFullName().isBlank());
    }
}