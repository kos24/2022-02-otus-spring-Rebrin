package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.BookRequestDtoConverter;
import ru.otus.dto.BookRequestDto;
import ru.otus.models.Book;
import ru.otus.repositories.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRequestDtoConverter converterToBook;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book insert(BookRequestDto bookRequestDto) {
        return bookRepository.saveOrUpdate(converterToBook.convert(bookRequestDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public Book update(BookRequestDto bookRequestDto) {
        return bookRepository.saveOrUpdate(converterToBook.convert(bookRequestDto));
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

}
