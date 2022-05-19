package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery(
                "select b " +
                        "from Book b " +
                        "where b.title = :title", Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public Book findById(Long id) {
        TypedQuery<Book> query = em.createQuery(
                "select b " +
                        "from Book b " +
                        "where b.id = :id", Book.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery(
                "delete " +
                        "from Book b " +
                        "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book update(Book book) {
        Book oldBook = em.find(Book.class, book.getId());
        if (!book.getTitle().isBlank()) {
            oldBook.setTitle(book.getTitle());
        }
        if (book.getGenre() != null){
            oldBook.setGenre(book.getGenre());
        }
        if (book.getAuthors() != null){
            oldBook.setAuthors(book.getAuthors());
        }
        return em.merge(oldBook);
    }

    @Override
    public List<Book> findAll() {
            TypedQuery<Book> query = em.createQuery(
                    "select b " +
                            "from Book b " +
//                            "join fetch b.genre", Book.class);
                            "", Book.class);
        return query.getResultList();
    }

}
