package ru.otus.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.exceptions.AuthorNotFoundException;
import ru.otus.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе Jpa для работы с авторами")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final String AUTHOR = "author_name_01";
    private static final String NOT_EXISTING_AUTHOR = "not_existing_author";

    @Autowired
    private AuthorRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен найти авторa по ФИО")
    @Test
    void shouldFindAuthorByNames() {
         var expectedAuthor= em.find(Author.class, 1L);
        Author actualAuthor = repositoryJpa.findByName(AUTHOR);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("должен кидать исключение, если ФИО авторов не найдено")
    @Test
    void shouldThrowExceptionWhenNotExistingAuthorNames() {
        assertThrows(AuthorNotFoundException.class, () -> repositoryJpa.findByName(NOT_EXISTING_AUTHOR));
    }

    @DisplayName("должен найти всех авторов")
    @Test
    void findAll() {
        List<Author> authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(3)
                .allMatch(a -> !a.getFullName().isBlank());
    }
}