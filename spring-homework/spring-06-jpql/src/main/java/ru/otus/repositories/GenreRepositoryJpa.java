package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.exceptions.CommonProjectException;
import ru.otus.exceptions.GenreNotFoundException;
import ru.otus.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre getByName(String name) {
        try {
            TypedQuery<Genre> query = em.createQuery(
                    "select g " +
                            "from Genre g " +
                            "where g.name = :name", Genre.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new GenreNotFoundException(String.format("Can't find genre: %s", name));
        } catch (Exception e) {
            throw new CommonProjectException("Something went wrong", e);
        }
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery(
                "select g " +
                        "from Genre g", Genre.class);
        return query.getResultList();
    }
}
