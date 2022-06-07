package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.BookRequestDtoConverter;
import ru.otus.dto.BookRequestDto;
import ru.otus.exceptions.BookNotFoundException;
import ru.otus.models.Book;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRequestDtoConverter converterToBook;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book insert(BookRequestDto bookRequestDto) {
        return bookRepository.save(converterToBook.convert(bookRequestDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBookByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with id: %s not found", id)));
    }

    @Override
    @Transactional
    public Book update(BookRequestDto bookRequestDto) {
        return bookRepository.save(converterToBook.convert(bookRequestDto));
    }

    @Override
    @Transactional
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
        commentRepository.deleteAll(commentRepository.findAllByBookId(id));
    }

}
