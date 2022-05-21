package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.CommentRequestDtoConverter;
import ru.otus.dto.CommentRequestDto;
import ru.otus.models.Comment;
import ru.otus.repositories.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentRequestDtoConverter converter;

    @Override
    @Transactional
    public Comment addCommentForBook(CommentRequestDto commentRequestDto) {
        return commentRepository.save(converter.convert(commentRequestDto));
    }

    @Override
    @Transactional
    public Comment update(CommentRequestDto commentRequestDto) {
        return commentRepository.update(converter.convert(commentRequestDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllByBookId(Long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
