package ru.otus.service;

import ru.otus.dto.CommentRequestDto;
import ru.otus.models.Comment;

import java.util.List;

public interface CommentService {

    Comment addCommentForBook(CommentRequestDto commentRequestDto);

    Comment update(CommentRequestDto commentRequestDto);

    List<Comment> findAllByBookId(Long bookId);

    void deleteById(Long id);


}
