package ru.otus.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.CommentRequestDto;
import ru.otus.models.Comment;
import ru.otus.repositories.BookRepository;

@Service
@RequiredArgsConstructor
public class CommentRequestDtoConverter implements Converter<CommentRequestDto, Comment> {

    private final BookRepository bookRepository;

    @Override
    public Comment convert(CommentRequestDto source) {
        return Comment.builder()
                .id(source.getId())
                .comment(source.getComment())
                .book(bookRepository.findById(source.getBookId()))
                .build();
    }
}
