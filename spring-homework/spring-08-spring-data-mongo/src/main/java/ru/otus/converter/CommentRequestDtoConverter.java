package ru.otus.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.CommentRequestDto;
import ru.otus.exceptions.BookNotFoundException;
import ru.otus.exceptions.CommentNotFoundException;
import ru.otus.models.Comment;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.CommentRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentRequestDtoConverter implements Converter<CommentRequestDto, Comment> {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment convert(CommentRequestDto source) {
        return Comment.builder()
                .id(getId(source.getId()))
                .comment(source.getComment())
                .book(bookRepository.findById(source.getBookId())
                        .orElseThrow(() -> new BookNotFoundException(String.format("Book with id: %s not found", source.getBookId()))))
                .build();
    }

    private String getId(String commentId) {
        if (Objects.nonNull(commentId)) {
            if (commentRepository.existsById(commentId)) {
                return commentId;
            } else {
                throw new CommentNotFoundException(String.format("Comment with id: %s not found", commentId));
            }
        }
        return null;
    }
}
