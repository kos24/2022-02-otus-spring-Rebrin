package ru.otus.repositories.ext;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookAuthorRelation {
    private final Long bookId;
    private final Long authorId;
}
