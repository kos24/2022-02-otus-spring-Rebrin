package ru.otus.repositories;

import ru.otus.models.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);

    Comment update(Comment comment);

    Comment findById(Long id);

    List<Comment> findAllByBookId(Long bookId);

    void deleteById(Long id);
}
