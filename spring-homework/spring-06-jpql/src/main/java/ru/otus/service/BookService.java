package ru.otus.service;

import ru.otus.dto.BookRequestDto;
import ru.otus.models.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book insert(BookRequestDto bookRequestDto);

    List<Book> getBookByTitle(String title);

    Book getBookById(Long id);

    Book update(BookRequestDto bookRequestDto);

    void deleteBookById(Long id);
}
