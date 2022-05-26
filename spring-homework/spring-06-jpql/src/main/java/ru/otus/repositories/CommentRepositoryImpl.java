package ru.otus.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Comment update(Comment comment) {
        Comment commentToBeUpdated = em.find(Comment.class, comment.getId());
        commentToBeUpdated.setComment(comment.getComment());
        return em.merge(commentToBeUpdated);
    }

    @Override
    public Comment findById(Long id) {
        TypedQuery<Comment> query = em.createQuery(
                "select c " +
                        "from Comment c " +
                        "where c.id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c " +
                        "from Comment c " +
                        "join c.book b " +
                        "where b.id = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery(
                "delete " +
                        "from Comment c " +
                        "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
