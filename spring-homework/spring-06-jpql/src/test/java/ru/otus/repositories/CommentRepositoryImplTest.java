package ru.otus.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import ru.otus.models.Book;
import ru.otus.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями к книге")
@DataJpaTest
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    public static final long FIRST_COMMENT_ID = 1L;
    public static final String NEW_COMMENT = "new comment";
    public static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    @Autowired
    CommentRepository repositoryJpa;

    @Autowired
    TestEntityManager em;

    @DisplayName("должен добавлять новый комментарий")
    @Test
    void shouldInsertComment() {
        var book = em.find(Book.class, FIRST_COMMENT_ID);
        var expectedComment = new Comment(NEW_COMMENT, book);
        repositoryJpa.save(expectedComment);
        assertThat(expectedComment.getId()).isPositive();

        var actualComment = em.find(Comment.class, expectedComment.getId());
        assertThat(actualComment).isNotNull()
                .matches(c -> c.getId().equals(expectedComment.getId()))
                .matches(c -> c.getComment().equals(expectedComment.getComment()));
    }

    @DisplayName("должен обновлять комментарий по ID")
    @Test
    void shouldUpdateComment() {
        var commentToBeUpdated = em.find(Comment.class, FIRST_COMMENT_ID);
        String oldComment = commentToBeUpdated.getComment();
        commentToBeUpdated.setComment(NEW_COMMENT);
        repositoryJpa.update(commentToBeUpdated);
        em.flush();
        em.clear();

        var updatedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(updatedComment.getComment()).isNotEqualTo(oldComment).isEqualTo(NEW_COMMENT);
    }

    @DisplayName("должен находить комментарий по ID")
    @Test
    void shouldFindCommentById() {
        var expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        var actualComment = repositoryJpa.findById(expectedComment.getId());
        assertThat(actualComment).isNotNull().usingRecursiveComparison().isEqualTo(actualComment);
    }

    @DisplayName("должен находить все комментарии по ID книги")
    @Test
    void shouldFindAllCommentsByBookId() {
        var existingComment = em.find(Comment.class, FIRST_COMMENT_ID);
        var book = em.find(Book.class, FIRST_COMMENT_ID);
        List<Comment> comments = repositoryJpa.findAllByBookId(book.getId());

        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .containsOnlyOnce(existingComment);
    }

    @DisplayName("должен удалять комментарий по ID")
    @Test
    void deleteById() {
        var commentToBeDeleted = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(commentToBeDeleted).isNotNull();
        repositoryJpa.deleteById(FIRST_COMMENT_ID);
        em.clear();
        var deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }
}