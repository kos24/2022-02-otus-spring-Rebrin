package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.exceptions.AuthorNotFoundException;
import ru.otus.exceptions.CommonProjectException;
import ru.otus.models.Author;

import javax.persistence.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author findByName(String fullName) {
        try {
            TypedQuery<Author> query = em.createQuery(
                    "select a " +
                            "from Author a " +
                            "where a.fullName = :fullName", Author.class);
            query.setParameter("fullName", fullName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new AuthorNotFoundException(String.format("Author with name - %s not found", fullName));
        } catch (Exception e) {
            throw new CommonProjectException("Something went wrong", e);
        }
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery(
                "select a " +
                        "from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author update(Author author) {
        Query query = em.createQuery(
                "update Author a " +
                        "set a.fullName =:fullName " +
                        "where a.id = :id");
        query.setParameter("fullName", author.getFullName());
        query.setParameter("id", author.getId());
        query.executeUpdate();
        return em.find(Author.class, author.getId());
    }
}
