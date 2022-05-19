package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.exceptions.AuthorNotFoundException;
import ru.otus.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findByNames(List<String> fullNames) {
        checkIfNameExist(fullNames);
        TypedQuery<Author> query = em.createQuery(
                "select a " +
                        "from Author a " +
                        "where a.fullName in (:fullNames)", Author.class);
        query.setParameter("fullNames", fullNames);
        return query.getResultList();
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery(
                "select a " +
                        "from Author a", Author.class);
        return query.getResultList();
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
}
