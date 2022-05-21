package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.models.Author;
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

    private final AuthorRepository authorRepository;

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
        Query query = em.createQuery(
                "update Book b " +
                        "set b.title = :title, " +
                        "b.genre = :genre " +
                        "where b.id = :id");
        query.setParameter("title", book.getTitle());
        query.setParameter("genre", book.getGenre());
        query.setParameter("id", book.getId());
        query.executeUpdate();
        Book updatedBook = em.find(Book.class, book.getId());
        List<Author> updatedAuthors = book.getAuthors().stream().map(authorRepository::update).toList();
        updatedBook.setAuthors(updatedAuthors);
        return updatedBook;
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
