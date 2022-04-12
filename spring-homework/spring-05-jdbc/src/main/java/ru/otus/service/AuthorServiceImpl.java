package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.models.Author;
import ru.otus.repositories.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public void printAll() {
        authorRepository.findAll().stream()
                .map(Author::getFullName)
                .forEach(System.out::println);
    }
}
